/**
 * 
 */
package com.bng.core.da;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.bng.core.entity.Content;
import com.bng.core.entity.ContentCategory;
import com.bng.core.entity.ContentLabel;
import com.bng.core.entity.ContentMapper;
import com.bng.core.entity.ContentMeta;
import com.bng.core.entity.ContentMetaExtra;
import com.bng.core.entity.ContentPlaylist;
import com.bng.core.entity.ContentPlaylistMapper;
import com.bng.core.entity.ContentProvider;
import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;

/**
 * @author Harish Shan
 *
 */
public class FileUploadDaoImp implements FileUploadDao 
{

	private SessionFactory sessionFactorySCP;
	private SessionFactory sessionFactoryGlobal;	
		
	public void setSessionFactorySCP(SessionFactory sessionFactorySCP) 
	{
		Logger.sysLog(LogValues.debug, this.getClass().getName(), " sessing session factory "+sessionFactorySCP);
		this.sessionFactorySCP = sessionFactorySCP;
	}
	public SessionFactory getSessionFactorySCP() 
	{
		return sessionFactorySCP;
	}
	
	public void setSessionFactoryGlobal(SessionFactory sessionFactoryGlobal) 
	{
		this.sessionFactoryGlobal = sessionFactoryGlobal;
	}
	public SessionFactory getSessionFactoryGlobal() 
	{
		return sessionFactoryGlobal;
	}
	
	@Transactional
	public void saveContent(Content content) 
	{
		Session session = sessionFactorySCP.getCurrentSession();
        session.saveOrUpdate(content);
	}
	
	@Transactional
	public ContentPlaylist saveContentPlaylist(ContentPlaylist contentPlaylist) 
	{
		Session session = sessionFactorySCP.getCurrentSession();
		Criteria criteria = session.createCriteria(ContentPlaylist.class).add(Restrictions.eq("playlistName",contentPlaylist.getPlaylistName()));
		List<ContentPlaylist>contentPlayList=criteria.list();
		if(contentPlayList.size()>0)
		{
			contentPlaylist=contentPlayList.get(0);
		}
		else
		{
			session.saveOrUpdate(contentPlaylist);	        
		}       
        return contentPlaylist;
	}

	
	@Transactional
	public ContentProvider saveContentProvider(ContentProvider contentProvider) 
	{
		Session session = sessionFactorySCP.getCurrentSession();
		Criteria criteria = session.createCriteria(ContentProvider.class).add(Restrictions.eq("providerName",contentProvider.getProviderName()));
		List<ContentProvider>contentProviderList=criteria.list();
		if(contentProviderList.size()>0)
		{
			contentProvider=contentProviderList.get(0);
		}
		else
		{
			session.saveOrUpdate(contentProvider);	        
		}       
        return contentProvider;
	}
	
	@Transactional
	public ContentLabel saveContentLabel(ContentLabel contentLabel) 
	{
		Session session = sessionFactorySCP.getCurrentSession();
		Criteria criteria = session.createCriteria(ContentLabel.class).add(Restrictions.eq("labelName",contentLabel.getLabelName()));
		List<ContentLabel>contentLabelList=criteria.list();
		if(contentLabelList.size()>0)
		{
			contentLabel=contentLabelList.get(0);
			Logger.sysLog(LogValues.debug, this.getClass().getName(),"select"+contentLabelList.get(0).getId()+"  "+contentLabel.getId());
		}
		else
		{
			session.saveOrUpdate(contentLabel);	        	
			Logger.sysLog(LogValues.debug, this.getClass().getName(),"insert"+"  "+contentLabel.getId());
		}       
        return contentLabel;
	}
	
	@Transactional
	public ContentCategory saveContentCategory(ContentCategory contentCategory) 
	{
		Session session = sessionFactorySCP.getCurrentSession();
		Criteria criteria = session.createCriteria(ContentCategory.class).add(Restrictions.eq("name",contentCategory.getName()));
		List<ContentCategory>contentCategoryList=criteria.list();
		if(contentCategoryList.size()>0)
		{
			contentCategory=contentCategoryList.get(0);
		}
		else
		{
			session.saveOrUpdate(contentCategory);	        
		}
        return contentCategory;
	}

	@Transactional
	public void saveContentMeta(ContentMeta contentMeta) 
	{
		Session session = sessionFactorySCP.getCurrentSession();
        session.save(contentMeta);
	}
	
	@Transactional
	public void saveContentMetaExtra(ContentMetaExtra contentMetaExtra) 
	{
		Session session = sessionFactorySCP.getCurrentSession();
        session.saveOrUpdate(contentMetaExtra);
	}
	
	@Transactional
	public void saveContentPlaylistMapper(ContentPlaylistMapper contentPlaylistMapper) 
	{
		Session session = sessionFactorySCP.getCurrentSession();
        session.saveOrUpdate(contentPlaylistMapper);
	}
	
	@Transactional
	public void saveContentMapper(ContentMapper contentMapper) 
	{
		Session session = sessionFactorySCP.getCurrentSession();
        session.saveOrUpdate(contentMapper);
	}		
}
