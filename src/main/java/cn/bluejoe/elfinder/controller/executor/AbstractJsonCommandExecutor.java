package cn.bluejoe.elfinder.controller.executor;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cn.bluejoe.elfinder.service.FsService;

import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.bng.core.util.coreException;
import com.bng.core.xmlparser.marshal.ConverterCoreEngine;

public abstract class AbstractJsonCommandExecutor extends AbstractCommandExecutor
{
	@Override
	final public void execute(FsService fsService, HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		JSONObject json = new JSONObject();
		try
		{
			execute(fsService, request, servletContext, json);
			String singleupload=request.getParameter("singleupload");
			if(singleupload!=null)
			{
				response.sendRedirect("fileviewer.htm");
			}
			else
			{
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter writer = response.getWriter();
				json.write(writer);
				writer.flush();
				writer.close();
			}
		}
		catch (Exception e)
		{
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(), coreException.GetStack(e));
			json.put("error", e.getMessage());
		}
	}

	protected abstract void execute(FsService fsService, HttpServletRequest request, ServletContext servletContext,
			JSONObject json) throws Exception;

}