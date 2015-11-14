package com.bng.core.da;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.bng.core.bean.ContentPlaylistBean;
import com.bng.core.entity.Content;
import com.bng.core.entity.ContentPlaylist;
import com.bng.core.entity.ContentPlaylistMapper;
import com.bng.core.entity.Country;
import com.bng.core.entity.IvrBlacklist;
import com.bng.core.entity.IvrRedcarpetlist;
import com.bng.core.entity.IvrWhitelist;
import com.bng.core.entity.Mxdata;
import com.bng.core.entity.Mxgraph;
import com.bng.core.entity.MxgraphKeyMapping;
import com.bng.core.entity.MxgraphVersion;
import com.bng.core.entity.Operator;
import com.bng.core.entity.UserMapping;
import com.bng.core.entity.Users;
import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.bng.core.util.coreException;
import com.bng.core.xmlparser.marshal.ConverterCoreEngine;
public class UserDaoImp implements UserDao {

	private SessionFactory sessionFactorySCP;
	private SessionFactory sessionFactoryGlobal; 

	public SessionFactory getSessionFactorySCP() {
		return sessionFactorySCP;
	}

	public void setSessionFactorySCP(SessionFactory sessionFactorySCP) {
		this.sessionFactorySCP = sessionFactorySCP;
	}

	public SessionFactory getSessionFactoryGlobal() {
		return sessionFactoryGlobal;
	}

	public void setSessionFactoryGlobal(SessionFactory sessionFactoryGlobal) {
		this.sessionFactoryGlobal = sessionFactoryGlobal;
	}

	@Transactional("global")
	public Users addUser(Users user) 
	{
		Session session = sessionFactoryGlobal.getCurrentSession();
		session.save(user);
		return user;
	}

	@Transactional("global")
	public List<Users> listUser() {

		Session session = sessionFactoryGlobal.getCurrentSession();
		List<Users> list = session.createQuery("from Users").list();
		return list;
	}

	@Transactional("global")
	public void removeUser(Integer id) {
		Session session = sessionFactoryGlobal.getCurrentSession();
//		Transaction tx = session.beginTransaction();
		Users contact = (Users) session.get(Users.class, id);
		if (null != contact) {
			Logger.sysLog(LogValues.info, this.getClass().getName(),"user ::"+contact.getId());
			session.delete(contact);
//			tx.commit();
		}
	}

	@Transactional("global")
	public Users showUser(Integer id) {
		Session session = sessionFactoryGlobal.getCurrentSession();
		Users suser = (Users)session.get(Users.class, id);
		return suser;
	}

	@Transactional("global")
	public void updateUser(Users user) {    	
		Session session = sessionFactoryGlobal.getCurrentSession();
//		Transaction tx = session.beginTransaction();
		session.update(user);
//		tx.commit();
	}

	/*public void saveXml(XmlDetail xmlFileEntity) {
    	Session session =sessionFactorySCP.getCurrentSession();
		session.setFlushMode(FlushMode.ALWAYS);
    	session.save(xmlFileEntity);
	}*

    public void saveMxGraph(MxgraphFile mxgraph){
    	Session session =sessionFactorySCP.getCurrentSession();
		session.setFlushMode(FlushMode.ALWAYS);
    	Transaction tx = session.beginTransaction();
    	session.saveOrUpdate(mxgraph);
    	tx.commit();
    }

    public void saveMxGraphTemplate(MxgraphTemplate mxgraph){
    	Session session =sessionFactorySCP.getCurrentSession();
		session.setFlushMode(FlushMode.ALWAYS);
    	Transaction tx = session.beginTransaction();
    	session.saveOrUpdate(mxgraph);
    	tx.commit();
    }
	 */
	@Transactional
	public List<Mxgraph> getMxGraph()
	{
		Session session = sessionFactorySCP.getCurrentSession();
		List<Mxgraph> list = session.createQuery("from Mxgraph").list();
		return list;

	}
	

//	public List<Mxgraph> viewservice(Integer userid){
//		
//		Mxgraph mxGraph = null;
//		List<Mxgraph> mxgraphlist = new ArrayList();
//		List<Mxgraph> mxgraphList = new ArrayList();
//		List<String> serviceNameList = new ArrayList();
//		List <MxgraphVersion> mxGraphVersionsList = createdate(userid);
//		Logger.sysLog(LogValues.info, this.getClass().getName(),"mxgraphversionList"+ mxGraphVersionsList.size());
//		for(MxgraphVersion mxGraphVersion : mxGraphVersionsList)
//		{
//			//Logger.sysLog(LogValues.info, this.getClass().getName(),mxGraphVersion);
//			mxGraph =  mxGraphVersion.getMxgraph();
//			//Logger.sysLog(LogValues.info, this.getClass().getName(),mxGraph);
//			mxgraphlist.add(mxGraph);
//		}	
//		Session session =sessionFactorySCP.getCurrentSession();
//		session.setFlushMode(FlushMode.ALWAYS);		
//		for(int i=0; i<mxgraphlist.size();i++){
//		Criteria crit2 = session.createCriteria(Mxgraph.class);
//		mxgraphList = crit2.add(Restrictions.eq("id", mxgraphlist.get(i).getId())).list();
//		}
//				
//		/*for(MxGraph mxGraph1:mxgraphList)
//		{
//			serviceName = mxGraph1.getServiceName();
//			Logger.sysLog(LogValues.info, this.getClass().getName(),serviceName);
//			serviceNameList.add(serviceName);
//		}
//	}
//		Logger.sysLog(LogValues.info, this.getClass().getName(),"service list"+serviceNameList);
//		return mxgraphList;
//		
//		
//	}
	@Transactional
	public List<MxgraphVersion> createdate(Integer userid){
		
		
		
		Session session = sessionFactorySCP.getCurrentSession();
		Criteria crit1 = session.createCriteria(MxgraphVersion.class);
		List<MxgraphVersion> mxGraphVersionsList = crit1.add(Restrictions.eq("userMappingId", userid)).list();
		Iterator iterator = mxGraphVersionsList.iterator();
		while(iterator.hasNext()){
			MxgraphVersion mxgraphVersion = (MxgraphVersion)iterator.next();
			mxgraphVersion.getMxdata().getData();
			mxgraphVersion.getMxgraph().getServiceName();
			mxgraphVersion.getMxgraph().getShortcode();
			mxgraphVersion.getCreatedDate();
		}
		return mxGraphVersionsList;
	}
	
	@Transactional
	public void forProduction(String id)
	{

		Session session = sessionFactorySCP.getCurrentSession();
//		Transaction tx = session.beginTransaction();
		int ids = Integer.parseInt(id);
		Query query = session.createQuery("update MxgraphFile set production_flag = :production_flag" +
				" where id = :id");
		query.setParameter("production_flag", "1");
		query.setParameter("id", ids);
		int result = query.executeUpdate();
		Logger.sysLog(LogValues.info, this.getClass().getName(),"result"+result);

//		tx.commit();

	}

	@Transactional("global")
	public void addmapper(Country country, Operator operator, Users users) {
		Session session = sessionFactoryGlobal.getCurrentSession();
		UserMapping map = new UserMapping();
		map.setCountry(country);
		map.setOperator(operator);
		map.setUsers(users);
		session.save(map);

	}

	@Transactional("global")
	public Country getCountry(String countryname) {
		Session session= sessionFactoryGlobal.getCurrentSession();
		Country country= null;
		//Country country = (Country)session.get(Country.class, id);
		Criteria crit = session.createCriteria(Country.class);
		List countryList = crit.add(Restrictions.eq("countryName", countryname)).list();
		Iterator iter = countryList.iterator();
		while(iter.hasNext())
		{
			country = (Country)iter.next();
		}		
		return country;
	}

	@Transactional("global")
	public Operator getOpeartor(String operatorname) {
		Session session= sessionFactoryGlobal.getCurrentSession();
		//Operator operator = (Operator)session.get(Operator.class, operatorname);
		Operator operator= null;
		//Country country = (Country)session.get(Country.class, id);
		Criteria crit = session.createCriteria(Operator.class);
		List operatorlist = crit.add(Restrictions.eq("operatorName", operatorname)).list();
		Iterator iter = operatorlist.iterator();
		while(iter.hasNext())
		{
			operator = (Operator)iter.next();
		}	
		return operator;
	}

	@Transactional
	public MxgraphKeyMapping getKey(Mxgraph mxgraph, String longcode)
	{
		Session session = sessionFactorySCP.getCurrentSession();
		MxgraphKeyMapping mxkey = null;
		Criteria crit = session.createCriteria(MxgraphKeyMapping.class);
		List keys = crit.add(Restrictions.eq("mxgraph", mxgraph)).add(Restrictions.eq("shortcode", longcode)).list();
		Iterator iter = keys.iterator();
		while(iter.hasNext())
		{
			mxkey = (MxgraphKeyMapping)iter.next();
		}	
		return mxkey;
	}
	
//	@Transactional
//	public void saveMxGraph(Mxgraph mxgraph) 
//	{
//		Session session = sessionFactorySCP.getCurrentSession();
////		Transaction tx = session.beginTransaction();
//		session.saveOrUpdate(mxgraph);
////		tx.commit();
//
//	}
//	@Transactional
//	public void saveData(Mxdata mxdata) 
//	{
//		Session session = sessionFactorySCP.getCurrentSession();
////		Transaction tx = session.beginTransaction();
//		session.saveOrUpdate(mxdata);
////		tx.commit();
//
//	}
//	@Transactional
//	public void saveMxGraphVersion(MxgraphVersion mxgraphversion) 
//	{
//		Session session = sessionFactorySCP.getCurrentSession();
////		Transaction tx = session.beginTransaction();
//		session.saveOrUpdate(mxgraphversion);
////		tx.commit();
//
//	}
	
	@Transactional
	public List<Content> getContentList()
	{
		Session session=sessionFactorySCP.getCurrentSession();
		session.setFlushMode(FlushMode.ALWAYS);
		List<Content> contentlist=session.createCriteria(Content.class).list();
		//Iterator iterator = contentlist.iterator();
		/*while(iterator.hasNext()){
			Content content = (Content)iterator.next();
			Iterator iterator2 = content.getContentMappers().iterator();
			while(iterator2.hasNext()){
				ContentMapper contentMapper = (ContentMapper)iterator2.next();
				contentMapper.getId();
			}
			
			iterator2 = content.getContentMetas().iterator();
			while(iterator2.hasNext()){
				ContentMeta contentMeta = (ContentMeta)iterator2.next();
				contentMeta.getId();
			}
			
			iterator2 = content.getContentPlaylistMappers().iterator();
			while(iterator2.hasNext()){
				ContentPlaylistMapper contentPlaylistMapper = (ContentPlaylistMapper)iterator2.next();
				contentPlaylistMapper.getId();
			
			}
		}
*/		return contentlist;    
	}
	
	@Transactional
	public List<ContentPlaylist> getContentPlaylist(int i)
	{
		Session session=sessionFactorySCP.getCurrentSession();
		session.setFlushMode(FlushMode.ALWAYS);
		if(i==-1){
		List<ContentPlaylist> contentPlaylist=session.createCriteria(ContentPlaylist.class).list();
		return contentPlaylist;
		}
		else
		{
			List<ContentPlaylist> contentPlaylist=session.createCriteria(ContentPlaylist.class).add(Restrictions.eq("id", i)).list();
			return contentPlaylist;
		}
	}
	


/*	@Transactional
	public List<Content> getContentForPlaylistIds(ArrayList<Integer> ids) 
	{
		Session session=sessionFactorySCP.getCurrentSession();
		session.setFlushMode(FlushMode.ALWAYS);
		ArrayList<Content> contents = new ArrayList<Content>();
		if(ids.size()<1) 
			return contents;
		Disjunction disjunction = Restrictions.disjunction();
		for(int id : ids)
			disjunction.add(Restrictions.eq("id", id));
		Criteria criteria = session.createCriteria(ContentPlaylist.class).add(disjunction);
		List<ContentPlaylist> list = criteria.list();
		for(ContentPlaylist contentPlaylist : list)
		{
			Iterator iterator = contentPlaylist.getContentPlaylistMappers().iterator();
			while(iterator.hasNext())
			{
				ContentPlaylistMapper contentPlaylistMapper = (ContentPlaylistMapper)iterator.next();
				contentPlaylistMapper.getContent().getPath();
				contentPlaylistMapper.getContent().getContentPlaylistMappers().iterator().next().getContentPlaylist().getPlaylistName();
				contents.add(contentPlaylistMapper.getContent());
			}
		}
		return contents;
	}*/
	@Transactional
	public List<ContentPlaylistBean> getContentForPlaylistIds(ArrayList<Integer> ids) 
	{
		List<ContentPlaylistBean> contentPlaylistBean = new ArrayList();
		if(ids.size()<1) 
			return contentPlaylistBean;
		int i=1;
		for(int id: ids)
		{
			Logger.sysLog(LogValues.debug, ConverterCoreEngine.class.getName(), "Integer:"+id);
			try
			{
				Session session=sessionFactorySCP.getCurrentSession();
				session.setFlushMode(FlushMode.ALWAYS);
				Criteria criteria1=session.createCriteria(ContentPlaylist.class);
				List<ContentPlaylist> contentPlaylist=criteria1.add(Restrictions.eq("id",id)).list();
				for(ContentPlaylist playlist: contentPlaylist)
				{
					Criteria criteria=session.createCriteria(ContentPlaylistMapper.class);
					criteria.add(Restrictions.eq("contentPlaylist",playlist)).addOrder(Order.asc("id"));
					List<ContentPlaylistMapper> list = criteria.list();
					for(ContentPlaylistMapper contentPlaylistMapper : list)
					{
						Logger.sysLog(LogValues.debug, ConverterCoreEngine.class.getName(), "Content:"+contentPlaylistMapper.getContent().getPath());
						contentPlaylistMapper.getContent().getPath();
						contentPlaylistBean.add(new ContentPlaylistBean(contentPlaylistMapper.getContent(),i));									
					}					
				}
				i++;
			}			
			catch(Exception e)
			{
				Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),"error while parsing path and changing id"+ coreException.GetStack(e));
			}			
		}		
		return contentPlaylistBean;
	}
	
	
	@Transactional("global")
	public Users getUser(String username) 
	{
		Session session = sessionFactoryGlobal.getCurrentSession();
		session.setFlushMode(FlushMode.ALWAYS);
		Criteria criteria = session.createCriteria(Users.class).add(Restrictions.eq("username",username));
		List<Users>currentUserList=criteria.list();
		return (Users)currentUserList.get(0);

	}

	@Override
	@Transactional
	public List getTemplate(char type) {
		Session session = sessionFactorySCP.getCurrentSession();
		Criteria criteria = session.createCriteria(Mxgraph.class);
		List list = criteria.add(Restrictions.eq("type", type+"")).list();
		Iterator listIterator = list.iterator();
		Logger.sysLog(LogValues.info, this.getClass().getName(),"extracted list size: " + list.size());
		while (listIterator.hasNext()) {
			Mxgraph mxgraph = (Mxgraph) listIterator.next();
			mxgraph.getMxdata().getData();
		}
		return list;
	}

	@Override
	@Transactional
	public List getTemplateXml(String serviceName, String shortCode) {
		Session session = sessionFactorySCP.getCurrentSession();
		Criteria criteria = session.createCriteria(Mxgraph.class);
		List list = criteria.add(Restrictions.eq("serviceName", serviceName))
				.add(Restrictions.eq("shortcode", shortCode))
				.list();
		Iterator listIterator = list.iterator();
		Logger.sysLog(LogValues.info, this.getClass().getName(),"extracted list size: " + list.size());
		while (listIterator.hasNext()) {
			Mxgraph mxgraph = (Mxgraph) listIterator.next();
			mxgraph.getMxdata().getData();
		}
		return list;
	}

	@Transactional
	@Override
	public void updateService(String serviceName, String shortCode, String xml,
			String callType, String type) {
		Session session = sessionFactorySCP.getCurrentSession();
//		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Mxgraph.class);
		List list = criteria.add(Restrictions.eq("serviceName", serviceName))
				.add(Restrictions.eq("shortcode", shortCode))
				.list();
		Mxgraph mxgraph = (Mxgraph)list.get(0);
		//System.out.println("before updating already existant mxgraph: "+mxgraph.toString());
		mxgraph.setCallType(callType);
		mxgraph.setType(type);
		//System.out.println("after updating already existant mxgraph: "+mxgraph.toString());
		session.update(mxgraph);
		int mxDataId = mxgraph.getMxdata().getId();
		criteria = session.createCriteria(Mxdata.class);
		list = criteria.add(Restrictions.eq("id", mxDataId))
				.list();
		Mxdata mxdata = (Mxdata)list.get(0);
		
		//System.out.println("before updating already existant mxdata: "+mxdata.toString());
		mxdata.setData(xml.getBytes());
		//System.out.println("after updating already existant mxdata: "+mxdata.toString());
		session.update(mxdata);
		criteria = session.createCriteria(MxgraphVersion.class);
		list = criteria.add(Restrictions.eq("mxdata", mxdata))
				.add(Restrictions.eq("mxgraph", mxgraph))
				.list();
		MxgraphVersion mxgraphVersion = (MxgraphVersion)list.get(0);
		//System.out.println("before updating already existant mxgraphVersion: "+mxgraphVersion.toString());
		mxgraphVersion.setVersion(mxgraphVersion.getVersion()+1);
		//System.out.println("after updating already existant mxgraphVersion: "+mxgraphVersion.toString());
		session.update(mxgraphVersion);
//		tx.commit();
	}

	@Transactional
	@Override
	public void removefromProduction(Mxgraph mxgraph) {
		mxgraph.setproductionFlag(false);
		Session session = sessionFactorySCP.getCurrentSession();
		session.update(mxgraph);
	}

	@Transactional
	@Override
	public List<Mxgraph> getReleasedservice(String shortcode) {
		
		Session session = sessionFactorySCP.getCurrentSession();
		Mxgraph list= null;
		Criteria crit = session.createCriteria(Mxgraph.class);
		List mxlist = crit.add(Restrictions.eq("shortcode", shortcode)).list();
		Iterator iter = mxlist.iterator();
		while(iter.hasNext())
		{
			list = (Mxgraph)iter.next();
			list.isproductionFlag();
			list.getCallType();
			list.getMxdata().getData();
		}	
		Logger.sysLog(LogValues.info, this.getClass().getName(),"--inside releasedservice--");
		return mxlist;
		
	}

	@Transactional
	@Override
	public void releasetoProduction(Mxgraph mxgraph) {
		mxgraph.setproductionFlag(true);
		Session session = sessionFactorySCP.getCurrentSession();
		session.update(mxgraph);
	}

	@Transactional
	@Override
	public List<MxgraphKeyMapping> getkeys(Mxgraph mxgraph) {
		Session session = sessionFactorySCP.getCurrentSession();
		MxgraphKeyMapping list = null;
		Criteria crit = session.createCriteria(MxgraphKeyMapping.class);
		List existingKeys = crit.add(Restrictions.eq("mxgraph", mxgraph)).list();
		Iterator iter = existingKeys.iterator();
		while(iter.hasNext())
		{
			list = (MxgraphKeyMapping)iter.next();
			list.getShortcode();
			list.getMxgraph();
		}
		Logger.sysLog(LogValues.info, this.getClass().getName(),"--inside existing keys check--");
		return existingKeys;
	}

	
	@Transactional
	@Override
	public List<MxgraphKeyMapping> getexistingkeys(String longcode) {
		Session session = sessionFactorySCP.getCurrentSession();
		MxgraphKeyMapping list = null;
		Criteria crit = session.createCriteria(MxgraphKeyMapping.class);
		List existingKeys = crit.add(Restrictions.eq("shortcode", longcode)).list();
		Iterator iter = existingKeys.iterator();
		while(iter.hasNext())
		{
			list = (MxgraphKeyMapping)iter.next();
			list.getMxgraph();
			list.getMxgraph().getCallType();
			list.getMxgraph().isproductionFlag();
		}
		return existingKeys;
	}

	@Transactional
	@Override
	public void releasetoProduction(Mxgraph mxgraph, String longcode) {
		Session session = sessionFactorySCP.getCurrentSession();
		MxgraphKeyMapping mxgraphkey = new MxgraphKeyMapping();
		mxgraphkey.setMxgraph(mxgraph);
		mxgraphkey.setShortcode(longcode);
		session.saveOrUpdate(mxgraphkey);
	}

	
	
	@Transactional
	@Override
	public void removeKey(Mxgraph mxgraph, String longcode) {
		Session session = sessionFactorySCP.getCurrentSession();
		MxgraphKeyMapping key = getKey(mxgraph,longcode);
		session.delete(key);
		
	}

	@Transactional
	@Override
	public void saveXML(byte[] xmlbyte, String serviceName, Date date,
			String type, String shortCode, String callType, Integer id,
			Date createdDate) {
		try {
			Session session = sessionFactorySCP.getCurrentSession();
			
			Mxdata mxdata=new Mxdata(xmlbyte);
			session.saveOrUpdate(mxdata);
			Mxgraph mxgraph=new Mxgraph(mxdata,serviceName,false,date,type,shortCode,callType);
			session.saveOrUpdate(mxgraph);
			MxgraphVersion mxgraphversion=new MxgraphVersion(mxdata,mxgraph,id,createdDate);
			session.saveOrUpdate(mxgraphversion);
		} catch (Exception e) {
			
		}
		
	}
	@Transactional
	@Override
	public String changeContentId(String path)
	{
		String newPath="";
		try
		{
			Session session=sessionFactorySCP.getCurrentSession();
			session.setFlushMode(FlushMode.ALWAYS);
			Query query = session.createQuery("from Content where path like :path");
			String idPath[]=path.split("-");
			List contentList = query.setParameter("path", "%"+idPath[1].replace("\\", "_")).list();		
			Iterator iter = contentList.iterator();
			if(iter.hasNext())
			{
				Content content=(Content)iter.next();
				newPath=content.getId()+"-"+content.getPath();
			}
		}
		catch(Exception e)
		{
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),"error while parsing path and changing id"+ coreException.GetStack(e));
		}
		return newPath;
	}
	@Transactional
	@Override
	public String changeContentPath(String path)
	{
		String newPath="";
		try
		{
			Session session=sessionFactorySCP.getCurrentSession();
			session.setFlushMode(FlushMode.ALWAYS);
			Query query = session.createQuery("from Content where path like :path");
			List contentList = query.setParameter("path", "%"+path.replace("\\", "_")).list();		
			Iterator iter = contentList.iterator();
			if(iter.hasNext())
			{
				Content content=(Content)iter.next();
				newPath=content.getPath();
			}
		}
		catch(Exception e)
		{
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),"error while parsing path and changing id"+ coreException.GetStack(e));
		}
		return newPath;
	}
	
	@Transactional
	@Override
	public int changeContentPlaylistId(String value)
	{
		int newId=0;
		Logger.sysLog(LogValues.info, ConverterCoreEngine.class.getName(),"value:"+value);
		try
		{
			Session session=sessionFactorySCP.getCurrentSession();
			session.setFlushMode(FlushMode.ALWAYS);
			Criteria crit1 = session.createCriteria(ContentPlaylist.class);
			List<ContentPlaylist> ContentPlaylists = crit1.add(Restrictions.eq("playlistName", value)).list();
			Iterator iterator = ContentPlaylists.iterator();
			if(iterator.hasNext())
			{
				ContentPlaylist contentPlaylist=(ContentPlaylist)iterator.next();
				newId=contentPlaylist.getId();
			}
		}
		catch(Exception e)
		{
			Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(),"error while parsing path and changing id"+ coreException.GetStack(e));
		}
		return newId;
	}

	@Transactional
	@Override
	public void saveBlacklist(String number, Integer isSeries) {
		Session session = sessionFactorySCP.getCurrentSession();
		IvrBlacklist blacklist = new IvrBlacklist();
		blacklist.setDate(new Date());
		blacklist.setMsisdn(number);
		blacklist.setIsSeries(isSeries);
		session.saveOrUpdate(blacklist);
	}

	@Transactional
	@Override
	public void saveWhitelist(String line, Integer isSeries) {
		Session session = sessionFactorySCP.getCurrentSession();
		IvrWhitelist whitelist = new IvrWhitelist();
		whitelist.setDate(new Date());
		whitelist.setMsisdn(line);
		whitelist.setIsSeries(isSeries);
		session.saveOrUpdate(whitelist);
	}

	@Transactional
	@Override
	public void saveRedcarpetlist(String line, Integer isSeries) {
		
		Session session = sessionFactorySCP.getCurrentSession();
		IvrRedcarpetlist redcarpet = new IvrRedcarpetlist();
		redcarpet.setDate(new Date());
		redcarpet.setMsisdn(line);
		redcarpet.setIsSeries(isSeries);
		session.saveOrUpdate(redcarpet);
	}

	
}