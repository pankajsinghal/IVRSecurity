package com.bng.core.service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bng.core.bean.ContentPlaylistBean;
import com.bng.core.bean.UserBean;
import com.bng.core.da.FilesDao;
import com.bng.core.da.UserDao;
import com.bng.core.entity.Content;
import com.bng.core.entity.ContentPlaylist;
import com.bng.core.entity.Country;
import com.bng.core.entity.Mxgraph;
import com.bng.core.entity.MxgraphKeyMapping;
import com.bng.core.entity.MxgraphVersion;
import com.bng.core.entity.Operator;
import com.bng.core.entity.UserRoles;
import com.bng.core.entity.Users;
import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.bng.core.xmlparser.marshal.ConverterCoreEngine;

public class UserServiceImp implements UserService{
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
		ConverterCoreEngine.setUserService(this);
	}

	public Users addUser(UserBean user){
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		Users roleuser = new Users(user.getUserName(),hashedPassword,1);
  	  	UserRoles roles = new UserRoles(user.getRole());
  	  	roleuser.getUserRoleses().add(roles);
		roles.setUsers(roleuser);
  	  	Users users=userDao.addUser(roleuser);
  	  	return users;
	}
//	public List<Mxgraph> viewservice(Integer userid){
//		List<Mxgraph> mxgraphlist = userDao.viewservice(userid);
//		return mxgraphlist;
//	}
	
	public List<Users> listUser(){
		return userDao.listUser();
	}
	
	public void removeUser(Integer id){
		userDao.removeUser(id);
	}
	
	public UserBean showUser(Integer id){
		
		Users user = userDao.showUser(id);
		UserBean users = new UserBean();
		users.setId(id);
		users.setUserName(user.getUsername());
		users.setPassword(user.getPassword());
                Iterator iter = user.getUserRoleses().iterator();
                while(iter.hasNext())
                {
                    Logger.sysLog(LogValues.info, this.getClass().getName(),"role :::"+((UserRoles)iter.next()).getAuthority());
                    users.setRole(((UserRoles)iter.next()).getAuthority());
                    //users.setRole(user.getuRoles().get(0).getRole());
                }			
		return users;
	}
	
	public void updateUser(UserBean user){
	
		Users xuser = userDao.showUser(user.getId());
		xuser.setUsername(user.getUserName());
  	  	Iterator iter = xuser.getUserRoleses().iterator();
                while(iter.hasNext())
                {
                   ((UserRoles)iter.next()).setAuthority(user.getRole());
                }
                //xuser.getuRoles().get(0).setRole(user.getRole());
  	  	userDao.updateUser(xuser);
	}
	
	/*public void saveXml(XmlDetail xmlFileEntity) {
		userDao.saveXml(xmlFileEntity);
		
	}
	
	public void saveMxGraph(MxgraphFile mxgraph){
		userDao.saveMxGraph(mxgraph);
		
	}
	
	public void saveMxGraphTemplate(MxgraphTemplate mxgraph){
		userDao.saveMxGraphTemplate(mxgraph);
		
	}
	
	public List<MxgraphFile> getMxGraph(){
		
		List<MxgraphFile> mxGraph = userDao.getMxGraph();
		return mxGraph;
		
	}*/
	
	public void forProduction(String id){
		userDao.forProduction(id);
	}

	public void addmapper(Country country, Operator operator, Users users) {
		userDao.addmapper(country, operator, users);
		
	}

	public Country getCountry(String countryname) {
		return userDao.getCountry(countryname);
	}

	public Operator getOperator(String operatorname) {
		return userDao.getOpeartor(operatorname);
	}

//	public void saveMxGraph(Mxgraph mxgraph) {
//		userDao.saveMxGraph(mxgraph);
//		
//	}
//
//	public void saveData(Mxdata mxdata) {
//		userDao.saveData(mxdata);
//		
//	}
//
//	public void saveMxGraphVersion(MxgraphVersion mxgraphversion) {
//		userDao.saveMxGraphVersion(mxgraphversion);
//		
//	}
	public List<Content> getContentList()
	{
		List<Content> contentlist=userDao.getContentList();
		return contentlist;
	}
	public List<ContentPlaylist> getContentPlaylist(int i)
	{
		List<ContentPlaylist> contentPlaylist=userDao.getContentPlaylist(i);
		return contentPlaylist;
	}
	public String convertToXml(FilesDao filesDao,Class... type) 
	 {
	  String result;
	        StringWriter sw = new StringWriter();
	        try 
	        {    
	            JAXBContext carContext = JAXBContext.newInstance(type);
	            Marshaller carMarshaller = carContext.createMarshaller();
	            carMarshaller.marshal(filesDao,sw);
	            result = sw.toString();
	        } 
	        catch (JAXBException e) 
	        {
	            throw new RuntimeException(e);
	        }
	        return result;
	    }
	public Users getUser(String username) 
	{
		Users user=userDao.getUser(username);
		return user;
	}

	@Override
	public List getTemplate(char type) {
		return userDao.getTemplate(type);
	}

	@Override
	public List getTemplateXml(String serviceName, String shortCode) {
		return userDao.getTemplateXml(serviceName, shortCode);
	}

	@Override
	public List<MxgraphVersion> createdate(Integer userid) {
		return userDao.createdate(userid);
	}

	@Override
	public void updateService(String serviceName, String shortCode, String xml,
			String callType, String type) {
		userDao.updateService(serviceName, shortCode, xml,callType, type);
	}

	@Override
	public void removefromProduction(Mxgraph mxgraph) {
		userDao.removefromProduction(mxgraph);
		
	}

	@Override
	public List<Mxgraph> getReleasedservice(String shortcode) {
		return userDao.getReleasedservice(shortcode);
	}

	@Override
	public void releasetoProduction(Mxgraph mxgraph) {
		userDao.releasetoProduction(mxgraph);
		
	}

	@Override
	public List<MxgraphKeyMapping> getkeys(Mxgraph mxgraph) {
		return userDao.getkeys(mxgraph);
	}

	@Override
	public List<MxgraphKeyMapping> getexistingkeys(String longcode) {
		return userDao.getexistingkeys(longcode);
	}

	@Override
	public void releasetoProduction(Mxgraph mxgraph, String longcode) {
		userDao.releasetoProduction(mxgraph,longcode);
		
	}

	@Override
	public void removeKey(Mxgraph mxgraph, String string) {
		userDao.removeKey(mxgraph,string);
		
	}

	@Override
	public void saveXML(byte[] xmlbyte, String serviceName, Date date,
			String type, String shortCode, String callType, Integer id,
			Date createdDate) {
		userDao.saveXML(xmlbyte, serviceName, date, type, shortCode, callType, id, createdDate);
	}

	@Override
	public void saveBlacklist(String number, Integer isSeries) {
		userDao.saveBlacklist(number, isSeries);
		
	}

	@Override
	public void saveWhitelist(String line, Integer isSeries) {
		userDao.saveWhitelist(line, isSeries);
		
	}

	@Override
	public void saveRedcarpetlist(String line, Integer isSeries) {
		userDao.saveRedcarpetlist(line,isSeries);
		
	}

	@Override
	public List<ContentPlaylistBean> getContentForPlaylistIds(
			ArrayList<Integer> ids) {
		return userDao.getContentForPlaylistIds(ids);
	}
	
	@Override
	public String changeContentId(String path)
	{
		return userDao.changeContentId(path);
	}
	
	@Override
	public int changeContentPlaylistId(String value)
	{
		return userDao.changeContentPlaylistId(value);
	}
	@Override
	public String changeContentPath(String path)
	{
		return userDao.changeContentPath(path);
	}
}
