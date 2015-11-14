package com.bng.core.da;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bng.core.bean.ContentPlaylistBean;
import com.bng.core.entity.Content;
import com.bng.core.entity.ContentPlaylist;
import com.bng.core.entity.Country;
import com.bng.core.entity.Mxgraph;
import com.bng.core.entity.MxgraphKeyMapping;
import com.bng.core.entity.MxgraphVersion;
import com.bng.core.entity.Operator;
import com.bng.core.entity.Users;


public interface UserDao {
	
		public Users addUser(Users user);
	    public List<Users> listUser();
	    public void removeUser(Integer id);
	    public Users showUser(Integer id);
	    public void updateUser(Users user);
		/*public void saveXml(XmlDetail xmlFileEntity);
		public void saveMxGraphTemplate(MxgraphTemplate mxgraph);
		public void saveMxGraph(MxgraphFile mxgraph);*/
//	    public List<Mxgraph> viewservice(Integer userid);
		public List<Mxgraph> getMxGraph();
		public void forProduction(String id);
		public void addmapper(Country country,Operator operator,Users users);
		public Country getCountry(String countryname);
		public Operator getOpeartor(String operatorname);
//		public void saveMxGraph(Mxgraph mxgraph);
//		public void saveData(Mxdata mxdata);
//		public void saveMxGraphVersion(MxgraphVersion mxgraphversion);	
		public List<Content> getContentList();
		public List<ContentPlaylist> getContentPlaylist(int i);
		public Users getUser(String username);
		public List getTemplate(char type);
		public List getTemplateXml(String serviceName, String shortCode);
		public List<MxgraphVersion> createdate(Integer userid);
		public void updateService(String serviceName, String shortCode,
				String xml, String callType, String type);
		public void removefromProduction(Mxgraph mxgraph);
		public List<Mxgraph> getReleasedservice(String shortcode);
		public void releasetoProduction(Mxgraph mxgraph);
		public List<MxgraphKeyMapping> getexistingkeys(String longcode);
		public void releasetoProduction(Mxgraph mxgraph, String longcode);
		public void removeKey(Mxgraph mxgraph, String string);
		public List<MxgraphKeyMapping> getkeys(Mxgraph mxgraph);
		public void saveXML(byte[] xmlbyte, String serviceName, Date date, String type, String shortCode, String callType, Integer id, Date createdDate);
		public void saveBlacklist(String number, Integer isSeries);
		public void saveWhitelist(String line, Integer isSeries);
		public void saveRedcarpetlist(String line, Integer isSeries);
		public List<ContentPlaylistBean> getContentForPlaylistIds(
				ArrayList<Integer> ids);
		public String changeContentId(String path);
		public int changeContentPlaylistId(String value);
		public String changeContentPath(String path);
	//	public void saveMxGraphTemplate(MxGraphTemplate mxgraph);		
}
