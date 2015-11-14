package cn.bluejoe.elfinder.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bluejoe.elfinder.controller.executor.CommandExecutionContext;
import cn.bluejoe.elfinder.controller.executor.CommandExecutor;
import cn.bluejoe.elfinder.controller.executor.CommandExecutorFactory;
import cn.bluejoe.elfinder.service.FsServiceFactory;

import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.bng.core.util.coreException;


@Controller
@RequestMapping("connector")
public class ConnectorController
{
	@Resource(name = "commandExecutorFactory")
	private CommandExecutorFactory _commandExecutorFactory;

	@Resource(name = "fsServiceFactory")
	private FsServiceFactory _fsServiceFactory;

	@RequestMapping
	public void connector(HttpServletRequest request, final HttpServletResponse response) throws IOException
	{
		/*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      String name = auth.getName(); //get logged in username
		*/
		String name = "";
	    try{  
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	      name = user.getUsername();//get logged in username
	    }catch(Exception e){
	    	Logger.sysLog(LogValues.error, this.getClass().getName(), "logout URL called "+request.getRemoteHost()+"/"+request.getContextPath()+"/"+"welcome.htm");
	    	return;
	    }
	      
	      Logger.sysLog(LogValues.info, this.getClass().getName(), "connector start "+name );
		try
		{
			request = parseMultipartContent(request);
		}
		catch (Exception e)
		{
			Logger.sysLog(LogValues.error, this.getClass().getName(), "Exception "+coreException.GetStack(e));
			throw new IOException(e.getMessage());
		}
		
		Logger.sysLog(LogValues.info, this.getClass().getName(), " request expiredate "+request.getParameter("expireDate"));

		String cmd = request.getParameter("cmd");
		Logger.sysLog(LogValues.info, this.getClass().getName(), " cmd "+cmd);
		CommandExecutor ce = _commandExecutorFactory.get(cmd);

		if (ce == null)
		{
			Logger.sysLog(LogValues.error, this.getClass().getName(), "Exception cmd null");
			throw new FsException(String.format("unknown command: %s", cmd));
		}

		try
		{
			final HttpServletRequest finalRequest = request;
			ce.execute(new CommandExecutionContext()
			{

				public FsServiceFactory getFsServiceFactory()
				{
					return _fsServiceFactory;
				}

				public HttpServletRequest getRequest()
				{
					return finalRequest;
				}

				public HttpServletResponse getResponse()
				{
					return response;
				}

				public ServletContext getServletContext()
				{
					return finalRequest.getSession().getServletContext();
				}
			});
		}
		catch (Exception e)
		{
			Logger.sysLog(LogValues.error, this.getClass().getName(), "Exception "+coreException.GetStack(e));
			throw new FsException("unknown error", e);
		}
	}

	private HttpServletRequest parseMultipartContent(final HttpServletRequest request) throws Exception
	{
		Logger.sysLog(LogValues.info, this.getClass().getName(), "parseMultipartContent start ");
		if (!ServletFileUpload.isMultipartContent(request))
			return request;

		final Map<String, String> requestParams = new HashMap<String, String>();
		List<FileItemStream> listFiles = new ArrayList<FileItemStream>();
		Logger.sysLog(LogValues.info, this.getClass().getName(), "parseMultipartContent start 222");
		// Parse the request
		ServletFileUpload sfu = new ServletFileUpload();
		//ServletFileUpload.isMultipartContent(request);
		String characterEncoding = request.getCharacterEncoding();
		Logger.sysLog(LogValues.info, this.getClass().getName(), "parseMultipartContent start 444 "+characterEncoding);
		sfu.setHeaderEncoding(characterEncoding);
		FileItemIterator iter = sfu.getItemIterator(request);
		Logger.sysLog(LogValues.info, this.getClass().getName(), "parseMultipartContent start 555 "+iter.hasNext());
		while (iter.hasNext())
		{
			final FileItemStream item = iter.next();
			String name = item.getFieldName();
			Logger.sysLog(LogValues.info, this.getClass().getName(), "parseMultipartContent start 666 "+name);
			InputStream stream = item.openStream();
			if (item.isFormField())
			{
				Logger.sysLog(LogValues.info, this.getClass().getName(), "parseMultipartContent start 777 "+name);
				requestParams.put(name, Streams.asString(stream));
			}
			else
			{
				String fileName = item.getName();
				Logger.sysLog(LogValues.info, this.getClass().getName(), "parseMultipartContent start 333" + fileName);
				if (fileName != null && !"".equals(fileName.trim()))
				{
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					IOUtils.copy(stream, os);
					final byte[] bs = os.toByteArray();
					stream.close();

					listFiles.add((FileItemStream) Proxy.newProxyInstance(this.getClass().getClassLoader(),
						new Class[] { FileItemStream.class }, new InvocationHandler()
						{
							public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
							{
								if ("openStream".equals(method.getName()))
								{
									return new ByteArrayInputStream(bs);
								}

								return method.invoke(item, args);
							}
						}));
				}
			}
		}

		request.setAttribute(FileItemStream.class.getName(), listFiles);
		return (HttpServletRequest) Proxy.newProxyInstance(this.getClass().getClassLoader(),
			new Class[] { HttpServletRequest.class }, new InvocationHandler()
			{
				public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable
				{
					if ("getParameter".equals(arg1.getName()))
					{
						return requestParams.get(arg2[0]);
					}

					return arg1.invoke(request, arg2);
				}
			});
	}
}