
package com.bng.core.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.security.Principal;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.engine.jdbc.NonContextualLobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bng.core.bean.Upload;
import com.bng.core.entity.Content;
import com.bng.core.entity.ContentMeta;
import com.bng.core.entity.ContentPlaylist;
import com.bng.core.entity.Mxgraph;
import com.bng.core.entity.Users;
import com.bng.core.scheduler.FileExtractorAndUpload;
import com.bng.core.service.FileUploadImp;
import com.bng.core.service.UserService;
import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.bng.core.util.Utility;
import com.bng.core.util.XmlPropertiesReader;
import com.bng.core.util.coreException;
import com.bng.core.xmlparser.marshal.BnGModel;
import com.bng.core.xmlparser.marshal.ConverterCoreEngine;
import com.bng.core.xmlparser.unmarshal.ConverterSCP;
import com.bng.core.xmlparser.unmarshal.MxGraphModel;

@Controller
public class FileController {

	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private UserService userService;
	@Autowired
	private XmlPropertiesReader xmlProperties;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private XmlPropertiesReader htmlProperties;

	private String serviceModifyId = "xmlsent";

	private String fileStoreRootPath=FileUploadImp.filestoreFolder;
	
	@RequestMapping(value="/getContentPlayList")
	public void getContentPlayList(HttpServletRequest request,HttpServletResponse response,Principal principal)throws IOException
	{
		try
		{
			Logger.sysLog(LogValues.info, this.getClass().getName(),"getContentPlayList started");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element fileStore = doc.createElement("filestore");
			doc.appendChild(fileStore);

			Element root = doc.createElement("root");
			fileStore.appendChild(root);
			
			Element playlist=doc.createElement("Playlist");
			root.appendChild(playlist);
			
			Element __wavs__=doc.createElement("__wavs__");
			playlist.appendChild(__wavs__);
			
			List<ContentPlaylist> contentPlaylists=userService.getContentPlaylist(-1);
			Iterator<ContentPlaylist> contentPlaylistIterator=contentPlaylists.iterator();
			while(contentPlaylistIterator.hasNext())
			{
				ContentPlaylist contentPlaylist=contentPlaylistIterator.next();
				Element id=doc.createElement("id");
				id.appendChild(doc.createTextNode(contentPlaylist.getId().toString()));
				
				Element file=doc.createElement("file");					
				file.appendChild(doc.createTextNode(contentPlaylist.getPlaylistName()));
				
				Element path=doc.createElement("path");
				path.appendChild(doc.createTextNode(contentPlaylist.getPlaylistName()));
				
				Element checked=doc.createElement("checked");
				checked.appendChild(doc.createTextNode("false"));
				
				Element wav= doc.createElement("wav");
				wav.appendChild(id);
				wav.appendChild(file);
				wav.appendChild(path);
				wav.appendChild(checked);
				
				__wavs__.appendChild(wav);
			}
			NodeList wavslist=doc.getElementsByTagName("__wavs__");
			for(int i=0;i<wavslist.getLength();i++)
			{
				
				if (wavslist.item(i).getNodeType() == Node.ELEMENT_NODE) 
				{
					Element parent=(Element)wavslist.item(i);
					NodeList children=parent.getChildNodes();
					if(parent.getChildNodes().getLength()==1)
					{
						Element tempwav=doc.createElement("wav");
						Element tempid=doc.createElement("id");
						tempid.appendChild(doc.createTextNode("-1"));
						tempwav.appendChild(tempid);
					
						Element tempfile=doc.createElement("file");
						tempfile.appendChild(doc.createTextNode("default"));
						tempwav.appendChild(tempfile);
						
						Element temppath=doc.createElement("path");
						temppath.appendChild(doc.createTextNode("default"));
						tempwav.appendChild(temppath);
					
						Element tempchecked=doc.createElement("checked");
						tempchecked.appendChild(doc.createTextNode("false"));
						tempwav.appendChild(tempchecked);
						parent.appendChild(tempwav);
					
					}					
				}				
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();			

			StringWriter stringWriter = new StringWriter();
			StreamResult streamResult = new StreamResult(stringWriter);			
			DOMSource domsource = new DOMSource(doc);

			transformer.transform(domsource, streamResult);
			String xmlString = stringWriter.toString();			
			response.getWriter().println(xmlString);
			
		}
		catch(Exception e)
		{
			Logger.sysLog(LogValues.error, this.getClass().getName(),coreException.GetStack(e));
		}
		Logger.sysLog(LogValues.info, this.getClass().getName(),"getContentPlayList finished");
	}

	
	
	//Changed code for windows and linux support of xml... if any problem revert the code to last update.. having old code in system-harish
	@RequestMapping(value="/getContentList")
	public void getContentList(HttpServletRequest request,HttpServletResponse response,Principal principal)throws IOException
	{
		try
		{
			Logger.sysLog(LogValues.debug, this.getClass().getName(),"getContentList started"+fileStoreRootPath);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Map<String,Element> folderMap=new HashMap<String,Element>();
			List<Content> contentlist=userService.getContentList();
			Element fileStore = doc.createElement("filestore");
			doc.appendChild(fileStore);

			Element root = doc.createElement("root");
			fileStore.appendChild(root);
			
			for(Content content : contentlist)
			{   
				Iterator<ContentMeta> contentMetaIterator=content.getContentMetas().iterator();
				while(contentMetaIterator.hasNext())
				{
					Logger.sysLog(LogValues.debug, this.getClass().getName(),"Content Path:"+content.getPath());
					ContentMeta contentmeta =(ContentMeta)contentMetaIterator.next();
					//TODO CHECK
					String relativePath=content.getPath().substring(content.getPath().indexOf(java.io.File.separator)+1,content.getPath().lastIndexOf(java.io.File.separator)+1);
					//String relativePath=content.getPath().substring(content.getPath().indexOf(java.io.File.separator)+1,content.getPath().lastIndexOf(java.io.File.separator)+1).replace(fileStoreRootPath,"");
					//TODO CHECK
					int index=relativePath.indexOf(java.io.File.separator);
					int lastIndex=0;
					while(index>0)
					{
						String folderMapping=relativePath.substring(0,index);
						String parentFolder=relativePath.substring(0,lastIndex);
						if(!folderMap.containsKey(folderMapping))
						{
							String foldername=null;
							if(lastIndex==0)
							{
								foldername=relativePath.substring(lastIndex,index).replaceAll("[^a-zA-Z0-9]","_");
								foldername= Character.isDigit(foldername.charAt(0))? "_"+foldername :foldername;								
							}
							else
							{
								foldername=relativePath.substring(lastIndex+1,index).replaceAll("[^a-zA-Z0-9]","_");
								foldername= Character.isDigit(foldername.charAt(0))? "_"+foldername :foldername;
							}
							Element temp=doc.createElement(foldername);
							if(folderMap.containsKey(parentFolder))
								folderMap.get(parentFolder).appendChild(temp);
							else
								root.appendChild(temp);
							folderMap.put(folderMapping,temp);														
						}			
						lastIndex=index;
						//TODO CHECK
						index=relativePath.indexOf(java.io.File.separator,index+1);
					}
					Logger.sysLog(LogValues.debug, this.getClass().getName(),"wav file add started");
					Element wav= doc.createElement("wav");
					Element id=doc.createElement("id");
					id.appendChild(doc.createTextNode(content.getId().toString()));
					wav.appendChild(id);
					Element file=doc.createElement("file");					
					file.appendChild(doc.createTextNode((new java.io.File(content.getPath())).getName()));
					wav.appendChild(file);
					
					Element path=doc.createElement("path");
					Logger.sysLog(LogValues.debug, this.getClass().getName(), "Content.getPath:"+content.getPath().replace(fileStoreRootPath, ""));
					//TODO
					//path.appendChild(doc.createTextNode(content.getPath().replaceAll(":","*").replace("\\","\\\\").replace("*", ":")));
					path.appendChild(doc.createTextNode(content.getPath().replace(fileStoreRootPath,"")));
					wav.appendChild(path);
					
					Element checked=doc.createElement("checked");
					checked.appendChild(doc.createTextNode("false"));
					wav.appendChild(checked);
					//TODO CHECK
					Element parent=folderMap.get(relativePath.substring(0,relativePath.lastIndexOf(java.io.File.separator)));
					NodeList wavstemplist=parent.getElementsByTagName("__wavs__");
					if(wavstemplist.getLength()!=0)
					{
						Element current=(Element)wavstemplist.item(0);
						current.appendChild(wav);
					}
					else
					{
						Element wavs=doc.createElement("__wavs__");
						parent.appendChild(wavs);
						wavs.appendChild(wav);
					}
				}    
			}
			Logger.sysLog(LogValues.debug, this.getClass().getName(),"Normalise __wavs__ started");
			NodeList wavslist=doc.getElementsByTagName("__wavs__");
			for(int i=0;i<wavslist.getLength();i++)
			{
				
				if (wavslist.item(i).getNodeType() == Node.ELEMENT_NODE) 
				{
					Element parent=(Element)wavslist.item(i);
					NodeList children=parent.getChildNodes();
					if(parent.getChildNodes().getLength()==1)
					{
						Element tempwav=doc.createElement("wav");
						Element tempid=doc.createElement("id");
						tempid.appendChild(doc.createTextNode("-1"));
						tempwav.appendChild(tempid);
					
						Element tempfile=doc.createElement("file");
						tempfile.appendChild(doc.createTextNode("default"));
						tempwav.appendChild(tempfile);
						
						Element temppath=doc.createElement("path");
						temppath.appendChild(doc.createTextNode("default"));
						tempwav.appendChild(temppath);
					
						Element tempchecked=doc.createElement("checked");
						tempchecked.appendChild(doc.createTextNode("false"));
						tempwav.appendChild(tempchecked);
						parent.appendChild(tempwav);
					
					}
					
				}
				
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();			

			StringWriter stringWriter = new StringWriter();
			StreamResult streamResult = new StreamResult(stringWriter);			
			DOMSource domsource = new DOMSource(doc);

			transformer.transform(domsource, streamResult);
			String xmlString = stringWriter.toString();			
			response.getWriter().println(xmlString);
			
		}
		catch(Exception e)
		{
			Logger.sysLog(LogValues.error, this.getClass().getName(),coreException.GetStack(e));
		}
		Logger.sysLog(LogValues.info, this.getClass().getName(),"getContentLst finished");
	}

	
		
	@RequestMapping("/duplicateServiceCheck/{serviceName}/{shortCode}")
	public @ResponseBody boolean duplicateServiceCheck(@PathVariable("serviceName") String serviceName,@PathVariable("shortCode") String shortCode)
	{
		if(userService.getTemplateXml(serviceName, shortCode).size()>0)
			return true;
		else
			return false;
	}


	@RequestMapping(value="/save.htm")
	public String save(HttpServletRequest request,HttpServletResponse response,Principal principal,HttpSession session, ModelMap model ) throws IOException
	{
		try {
			String username = principal.getName();
			Users currentUser=userService.getUser(username);
			Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			String xmls = request.getParameter("xml");
			Logger.sysLog(LogValues.debug, this.getClass().getName(),"XML:\n"+xmls);
			String type = request.getParameter("savetype");
			//System.out.println(xmls);
			HashMap<String, String> xml= convert(URLDecoder.decode(xmls, "UTF-8").replace(fileStoreRootPath, ""));
			Iterator<SimpleGrantedAuthority> role = authorities.iterator();
			String roles = null;
			while(role.hasNext())
			{  
				SimpleGrantedAuthority authority = (SimpleGrantedAuthority) role.next();
				roles=  authority.getAuthority();
			}
			Logger.sysLog(LogValues.debug, this.getClass().getName(),"request role:"+ roles+"request type:"+type);
			Logger.sysLog(LogValues.debug, this.getClass().getName(),"request xml:"+xml);
			Date createdDate = new java.sql.Date(new java.util.Date().getTime());
			Date date = null;
			try {
				date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2001-01-01 00:00:00");
			} catch (ParseException e) {
				Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(), coreException.GetStack(e));
			}

			String id=null;
			for(Cookie cookie : request.getCookies()){
				if(cookie.getName().equalsIgnoreCase(serviceModifyId))
				{
					id = cookie.getValue();
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
			if(id!=null && id.split("[$]")[0].equalsIgnoreCase(xml.get("servicename")) && id.split("[$]")[1].equalsIgnoreCase(xml.get("shortcode")) && type.equalsIgnoreCase("s"))
			{
//				request.getSession().removeAttribute(serviceModifyId);
				userService.updateService(xml.get("servicename"),xml.get("shortcode"),xml.get("xml"),xml.get("calltype"),id.split("[$]")[2]);	//id.split("[$]")[2] because if type = "s" then save the previous type
				model.addAttribute("status", "'Service Saved Successfully'");
				return "mxgraph";
			}
			if(type.equalsIgnoreCase("a")) type = "s";	//"a" indicates "saveas" & "s" indicates "service" and "save"
			//Blob blobxml = createBlob(xml.get("xml").getBytes());
			byte xmlbyte[]=xml.get("xml").getBytes();

			userService.saveXML(xmlbyte,xml.get("servicename"),date,type,xml.get("shortcode"),xml.get("calltype"),currentUser.getId(),createdDate);
			
//			Mxdata mxdata=new Mxdata(xmlbyte);
//			userService.saveData(mxdata);
//			Mxgraph mxgraph=new Mxgraph(mxdata,xml.get("servicename"),false,date,type,xml.get("shortcode"),xml.get("calltype"));
//			userService.saveMxGraph(mxgraph);
//			MxgraphVersion mxgraphversion=new MxgraphVersion(mxdata,mxgraph,currentUser.getId(),createdDate);
//			userService.saveMxGraphVersion(mxgraphversion);
			if(xml.get("isfilemissing").equals("true"))
				model.addAttribute("status", "'Mapped File Missing at FileStore'");
			else
				model.addAttribute("status", "'Service Saved Successfully'");
		} catch (Exception e) {
			Logger.sysLog(LogValues.error, this.getClass().getName(),coreException.GetStack(e));
			model.addAttribute("status", "'Error Saving Service'");
		}

		return "mxgraph";	
	}


	private HashMap<String, String> convert(String xml) {
		Logger.sysLog(LogValues.debug, this.getClass().getName(), "xml recevied "+xml);
		String newXml="";
		JAXBContext jaxbContext;
		ConverterSCP converterScp = new ConverterSCP();
		Boolean isFileMissing=false;
		try {
			jaxbContext = JAXBContext.newInstance(MxGraphModel.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			InputStream is = new ByteArrayInputStream(xml.getBytes());
			MxGraphModel mxGraphModel = (MxGraphModel) jaxbUnmarshaller
					.unmarshal(is);

			mxGraphModel = converterScp.convert(mxGraphModel);


			Marshaller m = jaxbContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			m.marshal(mxGraphModel,baos);
			newXml = baos.toString();
			
			BnGModel bnGModel = ConverterCoreEngine.convert(mxGraphModel,isFileMissing);
			baos.close();
			jaxbContext = JAXBContext.newInstance(BnGModel.class);
			m = jaxbContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			baos = new ByteArrayOutputStream();
			m.marshal(bnGModel,baos);
			//jmsTemplate.send(getMessageCreator(baos.toString()));
			baos.close();
		} catch (Exception e) {
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(), coreException.GetStack(e));
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("xml", newXml);
		map.put("calltype",converterScp.callType);
		map.put("shortcode", converterScp.shortCode);
		map.put("servicename", converterScp.serviceName);
		map.put("isfilemissing", isFileMissing.toString());
		return map;
	}

	private MessageCreator getMessageCreator(final String message) {
		return new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		};
	}

	private Blob createBlob(byte[] bytes) {
		return NonContextualLobCreator.INSTANCE.wrap(NonContextualLobCreator.INSTANCE.createBlob(bytes));
	}

	@RequestMapping("/open.htm")
	public String open()
	{
		return "open";
	}

	@RequestMapping("/openTemplateGetService.htm/{type}")
	public @ResponseBody String openTemplateGetService(@PathVariable("type") char type)
	{
		List list = userService.getTemplate(type);
		//		Map<String,byte[]> map = new HashMap<String, byte[]>();
		Iterator iter = list.iterator();
		String services="";
		while(iter.hasNext()){
			Mxgraph mxgraph = (Mxgraph) iter.next();
			services+=mxgraph.getServiceName()+"("+mxgraph.getShortcode()+"),";
		}
		Logger.sysLog(LogValues.info, this.getClass().getName(),services);
		//		return map;
		//		response.getWriter().w
		return services;
	}

	@RequestMapping("/openTemplateGetXml.htm/{value}")
	public @ResponseBody String openTemplateGetXml(Principal principal, HttpServletResponse response,@PathVariable ("value") String value)
	{

		String[] values = value.split("[()]");
		String serviceName = values[0];
		String shortCode = values[1];

		Logger.sysLog(LogValues.info, this.getClass().getName(),value);
//		String id = request.getSession().getId(); 

		List list = userService.getTemplateXml(serviceName,shortCode);
		Mxgraph mxgraph = (Mxgraph)list.get(0);
		String str = new String(mxgraph.getMxdata().getData());

		Cookie cookie = new Cookie(serviceModifyId, mxgraph.getServiceName()+"$"+mxgraph.getShortcode()+"$"+mxgraph.getType());
		cookie.setPath("/");
		response.addCookie(cookie);
//		request.getSession().setAttribute(serviceModifyId, mxgraph.getServiceName()+"$"+mxgraph.getShortcode()+"$"+mxgraph.getType());

		//		Iterator iter = list.iterator();
		//		String services="";
		//		while(iter.hasNext()){
		//			Mxgraph mxgraph = (Mxgraph) iter.next();
		//			services+=mxgraph.getServiceName()+"("+mxgraph.getShortcode()+"),";
		//		}
		Logger.sysLog(LogValues.info, this.getClass().getName(),str);
		return str;
	}


	@RequestMapping(value="/mxgraph.htm")
	public String newmxgraph(ModelMap model)
	{		
		return "mxgraph";
	}

	@RequestMapping(value="/scppopup/{id}/{type}")
	public @ResponseBody void test(@PathVariable("id") String sid, @PathVariable("type") String type, HttpServletResponse response)throws IOException
	{
		Logger.sysLog(LogValues.info, this.getClass().getName(), "");
		//Logger.sysLog(LogValues.info, this.getClass().getName(),"mxgraph type" +type);
		String fileName = (String) xmlProperties.propertiesXml().get(type);
		String htmlfileName = (String) htmlProperties.propertiesXml().get(type);
		//Logger.sysLog(LogValues.info, this.getClass().getName(),"mxgraph file name" +fileName);
		InputStream in = servletContext.getResourceAsStream("/WEB-INF/defaultxml/"+fileName);
		String line = "";
		StringBuffer sb = new StringBuffer();

		try {
			BufferedReader reader=new BufferedReader(new InputStreamReader(in));
			while((line=reader.readLine())!=null){
				sb.append(line);
			}
		} 
		catch (Exception e) {
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(), coreException.GetStack(e));
		}
		//String xmls = "<mxParams><recs><rec><id> bargein </id><name> Barge In </name><type>checkbox</type><values><value> true </value></values></rec><rec><id> repeat </id><name> Repeat </name><type>textbox</type><values><value> 400 </value></values></rec><rec><id> record </id><name> Record </name><type>checkbox</type><values><value>true</value></values></rec><rec><id> timeout </id><name> Time Out </name><type>textbox</type><values><value> 300 </value></values></rec><rec><id> playlist </id><name> Playlist </name><type>select</type><values><value> SELECT </value><value> 1.mp3 </value><value> 2.mp3 </value><value> 3.mp3 </value></values></rec></recs></mxParams>";
		//Logger.sysLog(LogValues.info, this.getClass().getName(),"mxgraph line" +sb.toString());
		response.setContentType("text/html");  
		response.getWriter().write(sb.toString() + '|' + htmlfileName); 
	}

	/*@RequestMapping("/viewservice.htm")
	public String viewService(ModelMap map)
	{        
		List<MxgraphFile> mxGraph = userService.getMxGraph();
		map.addAttribute("servicelist", mxGraph);
		return "viewservice";
	}*/
	/*@RequestMapping("/getmxgraphlist/{type}")
	public @ResponseBody void getMxGraphList(@RequestParam("type") String type,HttpServletResponse response,Principal principal)throws IOException
	{
		String username=principal.getName();
		String responseString="";
		List<MxGraph> mxGraph=userService.getMxGraphList();
		for(MxGraph mxgt : mxGraph)
		{
			responseString=mxgt.getServiceName()+"|";
		}
		response.setContentType("text/html");
		response.getWriter().write(responseString);
	}	
	 */



	/*@RequestMapping("/viewservice.htm")
	public String viewService(ModelMap map)
	{        
		List<MxgraphFile> mxGraph = userService.getMxGraph();
		map.addAttribute("servicelist", mxGraph);
		return "viewservice";
	}*/
	/*@RequestMapping("/getmxgraphlist/{type}")
	public @ResponseBody void getMxGraphList(@RequestParam("type") String type,HttpServletResponse response,Principal principal)throws IOException
	{
		String username=principal.getName();
		String responseString="";
		List<MxGraph> mxGraph=userService.getMxGraphList();
		for(MxGraph mxgt : mxGraph)
		{
			responseString=mxgt.getServiceName()+"|";
		}
		response.setContentType("text/html");
		response.getWriter().write(responseString);
	}	
	 */

	@RequestMapping("/productionMxGraph.htm")
	public @ResponseBody void production(@RequestParam("id") String id, HttpServletResponse response)throws IOException
	{
		String[] splitId = id.split(",");
		for(int i=0;i<splitId.length;i++){
			userService.forProduction(splitId[i]);	
		}

	}
	@RequestMapping(value="/fileviewer.htm")
	public String fileViewer()
	{
		return "fileviewer";
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}



	@RequestMapping(value="/exports.htm")
	public String exports(HttpServletRequest request,HttpServletResponse response,Principal principal,HttpSession session) throws IOException
	{
		try {
			response.setContentType("text/xml");
			String filename = request.getParameter("filename");
			String xmls = request.getParameter("xml");
			//System.out.println(xmls);
			String xml= URLDecoder.decode(xmls, "UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename);
			
			OutputStream os = response.getOutputStream();
			byte tmp[]=new byte[xml.length()];
			tmp=xml.getBytes();
			os.write(tmp);
			os.close();
		}catch (Exception e) {
		
			Logger.sysLog(LogValues.error, this.getClass().getName(),coreException.GetStack(e));
		}
	
		return "mxgraph";	
	}
	/**
	 * 
	 * @param model: to add upload bean
	 * @return It returns the page for uploading blacklist numbers
	 */
	@RequestMapping(value="/blacklist.htm", method = RequestMethod.GET)
	public String uploadb(ModelMap model)
	{
		Upload up = new Upload();
		model.addAttribute("upload", up);
		return "blacklist";
	}
	/**
	 * It gets the uploaded file from jsp page. Corrects the numbers and inserts into database
	 * @param upl
	 * @param result
	 * @param status
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/blacklist.htm", method = RequestMethod.POST)
	public String downloadb(@ModelAttribute("upload") Upload upl, BindingResult result, SessionStatus status,HttpServletRequest request)
	{
		try{
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory); 
		List items = upload.parseRequest(request);
        Iterator iterator = items.iterator();
        while (iterator.hasNext()) 
        {
            FileItem fi= (FileItem) iterator.next();
            Logger.sysLog(LogValues.debug, this.getClass().getName(),fi.getFieldName()+" :"+fi.getString());
            switch(fi.getFieldName()){
            case "blacklist": upl.setBlacklist(new CommonsMultipartFile(fi));
            break;
            case "isSeries" : upl.setIsSeries(Integer.parseInt(fi.getString()));
            }
            
		}
		MultipartFile dataFile = upl.getBlacklist();
		Logger.sysLog(LogValues.debug, this.getClass().getName(),"Uploaded file Size " + dataFile.getSize());
		Logger.sysLog(LogValues.debug, this.getClass().getName(),"ISeries: " +upl.getIsSeries() );
		InputStream is = null;
		BufferedReader br = null;
		//FileOutputStream out = null;
		try {
			is = dataFile.getInputStream();

			br = new BufferedReader(new InputStreamReader(is));
			//out = new FileOutputStream(tempFile);
			String line="";
			String msisdn="";
			while ((line = br.readLine()) != null) {
				if(upl.getIsSeries()!=1){
				msisdn= Utility.numbercorrected(line);
				Logger.sysLog(LogValues.info, this.getClass().getName(),"number corrected"+msisdn);
				}
				else
					msisdn=line;
				userService.saveBlacklist(msisdn,upl.getIsSeries());
			}
		
		} catch (IOException e) {
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),
					coreException.GetStack(e));
		}
		finally {
	        try {
	        	//out.close();
				br.close();
				is.close();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
	    }
		}
		catch(Exception e)
		{
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),
					coreException.GetStack(e));
		}
		
		return "uploaded";
	}
	/**
	 * 
	 * @param model: to add upload bean
	 * @return It returns the page for uploading whitelist numbers
	 */
	@RequestMapping(value="/whitelist.htm", method = RequestMethod.GET)
	public String uploadw(ModelMap model)
	{
		Upload up = new Upload();
		model.addAttribute("upload", up);
		return "whitelist";
	}
	@RequestMapping(value="/whitelist.htm", method = RequestMethod.POST)
	public String downloadw(@ModelAttribute("upload") Upload upl, BindingResult result, SessionStatus status,HttpServletRequest request)
	{
		try{
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory); 
		List items = upload.parseRequest(request);
        Iterator iterator = items.iterator();
        while (iterator.hasNext()) 
        {
            FileItem fi= (FileItem) iterator.next();
            Logger.sysLog(LogValues.info, this.getClass().getName(),fi.getFieldName()+" :"+fi.getString());
            switch(fi.getFieldName()){
            case "whitelist": upl.setWhitelist(new CommonsMultipartFile(fi));
            break;
            case "isSeries" : upl.setIsSeries(Integer.parseInt(fi.getString()));
            }
            
		}
		MultipartFile dataFile = upl.getWhitelist();
		Logger.sysLog(LogValues.debug, this.getClass().getName(),"Uploaded file Size " + dataFile.getSize());
		Logger.sysLog(LogValues.info, this.getClass().getName(),"ISeries: " +upl.getIsSeries() );
		InputStream is = null;
		BufferedReader br = null;
		//FileOutputStream out = null;
		try {
			is = dataFile.getInputStream();

			br = new BufferedReader(new InputStreamReader(is));
			//out = new FileOutputStream(tempFile);
			String line="";
			String msisdn="";
			while ((line = br.readLine()) != null) {
				if(upl.getIsSeries()!=1){
					msisdn= Utility.numbercorrected(line);
					Logger.sysLog(LogValues.info, this.getClass().getName(),"number corrected"+msisdn);
					}
					else
						msisdn=line;
				userService.saveWhitelist(line,upl.getIsSeries());
			}
		
		} catch (IOException e) {
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),
					coreException.GetStack(e));
		}
		finally {
	        try {
	        	//out.close();
				br.close();
				is.close();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
	    }
		}
		catch(Exception e)
		{
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),
					coreException.GetStack(e));
		}
		
		return "uploaded";
	}
	@RequestMapping(value="/redcarpet.htm", method = RequestMethod.GET)
	public String uploadr(ModelMap model)
	{
		Upload up = new Upload();
		model.addAttribute("upload", up);
		return "redcarpet";
	}
	@RequestMapping(value="/redcarpet.htm", method = RequestMethod.POST)
	public String downloadr(@ModelAttribute("upload") Upload upl, BindingResult result, SessionStatus status,HttpServletRequest request)
	{
		try{
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory); 
		List items = upload.parseRequest(request);
        Iterator iterator = items.iterator();
        while (iterator.hasNext()) 
        {
            FileItem fi= (FileItem) iterator.next();
            Logger.sysLog(LogValues.info, this.getClass().getName(),fi.getFieldName()+" :"+fi.getString());
            switch(fi.getFieldName()){
            case "redcarpet": upl.setRedcarpet(new CommonsMultipartFile(fi));
            break;
            case "isSeries" : upl.setIsSeries(Integer.parseInt(fi.getString()));
            }
            
		}
		MultipartFile dataFile = upl.getRedcarpet();
		Logger.sysLog(LogValues.info, this.getClass().getName(),"Uploaded file Size " + dataFile.getSize());
		Logger.sysLog(LogValues.info, this.getClass().getName(),"ISeries: " +upl.getIsSeries() );
		InputStream is = null;
		BufferedReader br = null;
		//FileOutputStream out = null;
		try {
			is = dataFile.getInputStream();

			br = new BufferedReader(new InputStreamReader(is));
			//out = new FileOutputStream(tempFile);
			String line="";
			String msisdn="";
			while ((line = br.readLine()) != null) {
				if(upl.getIsSeries()!=1){
					msisdn= Utility.numbercorrected(line);
					Logger.sysLog(LogValues.info, this.getClass().getName(),"number corrected"+msisdn);
					}
					else
						msisdn=line;
				userService.saveRedcarpetlist(line,upl.getIsSeries());
			}
		
		} catch (IOException e) {
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),
					coreException.GetStack(e));
		}
		finally {
	        try {
	        	//out.close();
				br.close();
				is.close();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
	    }
		}
		catch(Exception e)
		{
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),
					coreException.GetStack(e));
		}
		
		return "uploaded";
	}
}

