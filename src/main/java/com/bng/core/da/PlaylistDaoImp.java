package com.bng.core.da;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bng.core.entity.Content;
import com.bng.core.entity.ContentPlaylist;
import com.bng.core.entity.ContentPlaylistMapper;
import com.bng.core.entity.Playlist;
import com.bng.core.util.ConnectionPool;
import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.jolbox.bonecp.BoneCPDataSource;
import com.mysql.jdbc.Statement;

public class PlaylistDaoImp implements PlaylistDao {

	private SessionFactory sessionFactorySCP;

	//private ComboPooledDataSource dataSource;

	private ConnectionPool connectionPool;

	private BoneCPDataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	

	@Override
	@Transactional
	public List<Playlist> getPlayList()
	{
		Playlist playlist = null;		
		Session session = sessionFactorySCP.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(Playlist.class);
		List<Playlist> play = criteria.list();
		return play;
	}

	@Override
	@Transactional
	public List<ContentPlaylistMapper> getContent(int playlistId) 
	{
		List<ContentPlaylistMapper> contentPlaylistMapperList=null;
		Session session = sessionFactorySCP.getCurrentSession();
		Criteria criteria =session.createCriteria(ContentPlaylist.class).add(Restrictions.eq("id",playlistId));
		List<ContentPlaylist> contentPlaylists=criteria.list();
		Iterator<ContentPlaylist> contentPlaylistIterator=contentPlaylists.iterator();
		if(contentPlaylistIterator.hasNext())
		{
			ContentPlaylist contentPlaylist=contentPlaylistIterator.next();
			Criteria criteria1 = session.createCriteria(ContentPlaylistMapper.class).add(Restrictions.eq("contentPlaylist", contentPlaylist));
			contentPlaylistMapperList=criteria1.list();
			Iterator<ContentPlaylistMapper> contentPlaylistMapperIterator=contentPlaylistMapperList.iterator();
			while(contentPlaylistMapperIterator.hasNext())
			{
				ContentPlaylistMapper contentPlaylistMapper=contentPlaylistMapperIterator.next();
				Logger.sysLog(LogValues.info, this.getClass().getName(),"" + contentPlaylistMapper.toString());
				Content content=contentPlaylistMapper.getContent();
				Logger.sysLog(LogValues.info, this.getClass().getName(),"" + content.getPath());				
			}			
		}	
		return contentPlaylistMapperList;
	}
	
	@Override
	@Transactional
	public int addNewPlayList(String playlistName)
	{
		Session session =sessionFactorySCP.getCurrentSession();
		Criteria criteria = session.createCriteria(ContentPlaylist.class).add(Restrictions.eq("playlistName", playlistName));
		int returnId = 0;
		List<ContentPlaylist> list = criteria.list();
		
		Iterator<ContentPlaylist> iterator = list.iterator();
		
		if(!iterator.hasNext())		
		{
			PreparedStatement ps;
			Connection connection = this.connectionPool.getConnection();
			String sql = "insert into content_playlist(playlist_name) values (?)";	
			try {
				ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, playlistName);
				ps.executeUpdate();
				ResultSet keys = ps.getGeneratedKeys();
				keys.next();
				returnId = keys.getInt(1);
				Logger.sysLog(LogValues.info, this.getClass().getName(), ps.toString());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		
		return returnId;
	}
	

	@Override
	@Transactional
	public int savePlaylistContents(int playlistId,int[] deleteContent ,int[] playContent)
	{
		try
		{
			Session session =sessionFactorySCP.openSession();
			Transaction tx = session.beginTransaction();

			if(deleteContent[0] != -1)
			{
				for(int i=0;i<deleteContent.length;i++)
				{
					ContentPlaylistMapper contentPlaylistMapper=(ContentPlaylistMapper)session.get(ContentPlaylistMapper.class, deleteContent[i]);
					session.delete(contentPlaylistMapper);
				}
			}

			ContentPlaylist contentPlaylist=(ContentPlaylist)session.get(ContentPlaylist.class,playlistId);

			if(playContent[0] != -1)
			{
				for(int i=0;i<playContent.length;i++)
				{
					ContentPlaylistMapper contentPlaylistMapper=new ContentPlaylistMapper();
					Content content=(Content)session.get(Content.class,playContent[i]);			
					contentPlaylistMapper.setContent(content);
					contentPlaylistMapper.setContentPlaylist(contentPlaylist);
					session.save(contentPlaylistMapper);
				}
			}
			
			tx.commit();
			return 1;
		}
		catch(Exception e)
		{
			Logger.sysLog(LogValues.error, this.getClass().getName(),e);
			return 0;
		}
		
	}
}
