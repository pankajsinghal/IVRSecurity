package com.bng.core.controller;

/**
 * author : Krishnakumar
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.bng.core.bean.PlaylistBean;
import com.bng.core.entity.Content;
import com.bng.core.entity.ContentPlaylist;
import com.bng.core.entity.ContentPlaylistMapper;
import com.bng.core.entity.Playlist;
import com.bng.core.service.FileUploadImp;
import com.bng.core.service.SchedulerBo;
import com.bng.core.service.UserService;
import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.bng.core.util.coreException;
import com.bng.core.xmlparser.marshal.ConverterCoreEngine;

@Controller
public class PlaylistController {

	@Autowired
	private SchedulerBo schedulerBo; 

	@Autowired
	private UserService userService;
	/**
	 * 
	 * @param principal
	 * @param model
	 * @return
	 * 
	 * Sends the playlists present in the database to front end
	 */
	@RequestMapping(value="/playlistviewer.htm", method = RequestMethod.GET)
	public String getPlaylist(Principal principal, ModelMap model)
	{
		PlaylistBean playlistBean = new PlaylistBean();
		model.addAttribute("playlistForm",playlistBean);		
		List<Playlist> playlist = schedulerBo.getplaylist();
		model.addAttribute("playList", playlist);
		return "playlist";
	}

	/**
	 * 
	 * @param principal
	 * @param response
	 */
	@RequestMapping(value="/getPlaylist_onImport", method = RequestMethod.GET)
	public void getPlaylist_onImport(Principal principal, HttpServletResponse response)
	{
		try
		{
			List<Playlist> playlist = schedulerBo.getplaylist();
			PrintWriter pw=response.getWriter();
			Iterator playlistIterator = playlist.iterator();
			StringBuilder sb=new StringBuilder("");
			while(playlistIterator.hasNext())
			{
				Playlist getPlaylist=(Playlist) playlistIterator.next();
				sb.append("<option value=\""+getPlaylist.getPlaylist_id()+"\">"+getPlaylist.getPlaylist_name()+"</option>");
			}
			pw.print(sb.toString());
		}
		catch(Exception e)
		{
			Logger.sysLog(LogValues.error, this.getClass().getName(),e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param playlistId
	 * @param response
	 * @param model
	 * 
	 * Sends the Playlist Contents for a particular playlist 
	 */
	@RequestMapping(value="/getPlayLstContent/{playlistId}")
	public void getPlayListContent(@PathVariable("playlistId")int playlistId,HttpServletResponse response,ModelMap model)
	{
		try 
		{
			String fileStoreRootPath=FileUploadImp.filestoreFolder;
			//response.getWriter().println(playlistId); 
			List<ContentPlaylistMapper> contentPlaylistMapperList=schedulerBo.getContent(playlistId);
			Logger.sysLog(LogValues.debug, this.getClass().getName(),"" + contentPlaylistMapperList);	
			//model.addAttribute("playlistcontent", contentPlaylistMapper);
			PrintWriter pw=response.getWriter();
			Iterator<ContentPlaylistMapper> contentPlaylistMapperIterator=contentPlaylistMapperList.iterator();
			StringBuilder sb=new StringBuilder("");
			while(contentPlaylistMapperIterator.hasNext())
			{
				ContentPlaylistMapper contentPlaylistMapper=contentPlaylistMapperIterator.next();
				Content content=contentPlaylistMapper.getContent();
				sb.append("<option value=\""+content.getId()+"-"+contentPlaylistMapper.getId()+"\">"+content.getPath().replace(fileStoreRootPath, "")+"</option>");
			}
			pw.print(sb.toString());
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	/**
	 * 
	 * @param playlistName
	 * @param response
	 * @param model
	 * 
	 *  Adds a new playlist into the database 
	 */
	@RequestMapping(value="/addPlayList/{playlistName}")
	public void addPlayList(@PathVariable("playlistName")String playlistName,HttpServletResponse response,ModelMap model)
	{
		Logger.sysLog(LogValues.info, this.getClass().getName(),"" + playlistName);
		int result = schedulerBo.addNewPlayList(playlistName);
		Logger.sysLog(LogValues.info, this.getClass().getName(),"" + result);
		try {
			response.getWriter().print(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*	@RequestMapping(value="/savePlayListContents/{playlistid}/{deletecontent}/{playcontent}")
	public void savePlayListContent_old(@PathVariable("playlistid")int playlistId, @PathVariable("deletecontent") int[] deleteContent, @PathVariable("playcontent") int[] playContent, HttpServletResponse response)
	{
		try 
		{
			Logger.sysLog(LogValues.info, this.getClass().getName(),"Save PlayList Id : " + playlistId);
			for(int i = 0; i < deleteContent.length; i++)
				Logger.sysLog(LogValues.info, this.getClass().getName(),"To Delete Ids "+ i + ": " + deleteContent[i]);
			for(int i = 0; i < playContent.length; i++)
				Logger.sysLog(LogValues.info, this.getClass().getName(),"To Insert Ids "+ i + ": " + playContent[i]);
			int result = schedulerBo.savePlaylistContents(playlistId,deleteContent ,playContent);
			try {
				response.getWriter().print(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}						
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	*/

	/**
	 * 
	 * @param playlistId
	 * @param insertContent
	 * @param response
	 * 
	 * Saves the playlist into the database 
	 */
	@RequestMapping(value="/savePlayListContents/{playlistid}/{insertcontent}")
	public void savePlayListContent(@PathVariable("playlistid")int playlistId, @PathVariable("insertcontent") int[] insertContent, HttpServletResponse response)
	{
		try 
		{
			Logger.sysLog(LogValues.info, this.getClass().getName(),"Save PlayList Id : " + playlistId);
			for(int i = 0; i < insertContent.length; i++)
				Logger.sysLog(LogValues.info, this.getClass().getName(),"To Delete Ids "+ i + ": " + insertContent[i]);

			int result = schedulerBo.savePlaylistContents(playlistId,insertContent);
			try {
				response.getWriter().print(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}						
		}

		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	@RequestMapping(value="/exportPlayList/{playlists}")
	public void getExportPlayList(HttpServletRequest request,HttpServletResponse response,Principal principal,@PathVariable("playlists") int[] playlists)throws IOException
	{
		try
		{
			String fileStoreRootPath=FileUploadImp.filestoreFolder;
			Logger.sysLog(LogValues.info, this.getClass().getName(),"getExportList started");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Map<String,Element> playlistMap=new HashMap<String,Element>();
			List<ContentPlaylistMapper> contentPlaylistMappers=schedulerBo.getPlaylists(playlists);
			Element root = doc.createElement("root");
			doc.appendChild(root);

			for(ContentPlaylistMapper ContentPlaylistMapper: contentPlaylistMappers)
			{				
				Element content=doc.createElement("content");
				content.appendChild(doc.createTextNode(ContentPlaylistMapper.getContent().getPath().replace(fileStoreRootPath,"")));
				Logger.sysLog(LogValues.info, this.getClass().getName(),"Content Path:"+ContentPlaylistMapper.getContent().getPath().replace(fileStoreRootPath,""));
				if(playlistMap.containsKey(ContentPlaylistMapper.getContentPlaylist().getPlaylistName()))
				{
					playlistMap.get(ContentPlaylistMapper.getContentPlaylist().getPlaylistName()).appendChild(content);					
				}
				else
				{
					Element playlist=doc.createElement("playlist");
					playlist.setAttribute("name",ContentPlaylistMapper.getContentPlaylist().getPlaylistName() );
					Logger.sysLog(LogValues.info, this.getClass().getName(),"Content Playlist:"+ContentPlaylistMapper.getContentPlaylist().getPlaylistName());
					root.appendChild(playlist);					
					playlistMap.put(ContentPlaylistMapper.getContentPlaylist().getPlaylistName(), playlist);
					playlist.appendChild(content);					
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


	@RequestMapping(value="/importPlaylist",  method = RequestMethod.POST)
	public void importPlayList(HttpServletRequest request,HttpServletResponse response,Principal principal)throws IOException
	{
		PrintWriter out=response.getWriter();
		try
		{
			String xml=request.getReader().readLine();
			String xmls=URLDecoder.decode(xml,"UTF-8").trim();
			if(xmls==null)
			{
				out.println("Upload Playlist Properly");
				return;
			}
			else if(xmls.equalsIgnoreCase("null"))
			{
				out.println("Upload Playlist Properly");
				return;
			}
			else if(xml=="")
			{
				out.println("Open Chome from Command Prompt [ chrome.exe --allow-file-access-from-files ] \nOR\nPlease use Firefox or Opera ");
				return;
			}						
			Logger.sysLog(LogValues.info, this.getClass().getName(), "UTF XML:"+xmls);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xmls));
			Document doc=builder.parse(is);
			NodeList playlists=doc.getElementsByTagName("playlist");
			List<ContentPlaylist> contentPlaylists=schedulerBo.getPlaylists();
			HashMap<String,ContentPlaylist> contentPlaylistMap=new HashMap<String,ContentPlaylist>();
			for(ContentPlaylist contentPlaylist :contentPlaylists)
			{
				contentPlaylistMap.put(contentPlaylist.getPlaylistName(), contentPlaylist);
			}
			for(int i=0;i<playlists.getLength();i++)
			{
				Node playlist=playlists.item(i);
				NamedNodeMap nodeMap=playlist.getAttributes();
				Node nameAttribute= nodeMap.getNamedItem("name");
				if(nameAttribute!=null)
				{
					if(contentPlaylistMap.containsKey(nameAttribute.getNodeValue()))
					{
						out.println("Playlist [ "+nameAttribute.getNodeValue()+" ] Already Exists");
					}
					else
					{
						HashMap<String,Content> contentHashMap=new HashMap<String,Content>();
						List<Content> contentList=userService.getContentList();
						Iterator<Content> contentIterator=contentList.iterator();
						while(contentIterator.hasNext())
						{
							try
							{
								Content content=(Content)contentIterator.next();
								String keyString=content.getPath().replace(FileUploadImp.filestoreFolder,"");
								contentHashMap.put(keyString,content);								
							}
							catch(Exception e)
							{
								Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(), coreException.GetStack(e));
							}
						}
						NodeList contentNodeList=playlist.getChildNodes();
						ArrayList<Content> contentArrayList=new ArrayList<Content>();
						for(int j=0;j<contentNodeList.getLength();j++)
						{
							try
							{
								Node contentNode=contentNodeList.item(j);
								if(contentHashMap.containsKey(contentNode.getTextContent()))
								{
									Content content=contentHashMap.get(contentNode.getTextContent());
									contentArrayList.add(content);
								}
								else
								{
									if(!contentNode.getTextContent().trim().equals(""))
									{
										Logger.sysLog(LogValues.info, this.getClass().getName(), contentNode.getTextContent().toString()+" is not exists");
										out.println(contentNode.getTextContent().toString()+" is not exists");
									}
								}
							}
							catch(Exception e)
							{
								Logger.sysLog(LogValues.error, this.getClass().getName(),coreException.GetStack(e));
							}
						}
						ContentPlaylist contentPlaylist=new ContentPlaylist(nameAttribute.getNodeValue());
						schedulerBo.savePlaylistContents(contentPlaylist,contentArrayList);
					}
					Logger.sysLog(LogValues.info, this.getClass().getName(), "AttributeName:"+nameAttribute.getNodeValue());
				}				
			}
			Logger.sysLog(LogValues.info, this.getClass().getName(), "No Problem");
			out.println("Playlist Imported Successfully");			
		}
		catch(Exception e)
		{
			Logger.sysLog(LogValues.error, this.getClass().getName(),coreException.GetStack(e));
		}
	}	
}