package com.bng.core.da;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

import com.bng.core.bean.JobBean;
import com.bng.core.entity.Content;
import com.bng.core.entity.ContentPlaylist;
import com.bng.core.entity.ContentPlaylistMapper;
import com.bng.core.entity.Mxgraph;
import com.bng.core.entity.ObdBlackoutHours;
import com.bng.core.entity.ObdCli;
import com.bng.core.entity.Playlist;
import com.bng.core.entity.Service;
import com.jolbox.bonecp.BoneCPDataSource;

public interface SchedulerDao {
	
	public void setDataSource(BoneCPDataSource ds);
	public List<Mxgraph> getService();
	public List getmxgraph(int id);
	public Mxgraph getmxgraph(String servicename, String shortcode);
	public void addJob(Service service);
	public ObdBlackoutHours getobh(int i);
	public void addCli(ObdCli cli);
	public void addobh(ObdBlackoutHours blackout);
	public String getJobname(String jobname);
	public Service getService(String servicename, String shortcode);
	public List getServicelist(Integer id);
	public Mxgraph mxgraph(int id);
//	public void addMsisdns(ArrayList<String> msisdns, String jobname);
	public void dndFilter(String tableName, int i);
	public Service getService(String jobname);
	public void updatejob(Service service);
	public List<ObdCli> getCli(Service service);
	void deleteCli(Service service);
//	public boolean addMsisdns(String path, String jobname);
	public void addJob(String absolutePath, Service service, String serviceName, String shortCode, JobBean jobBean);
	public NameValuePair addMsisdnsMysql(ArrayList<String> create_table, String sql);
	public List getObdFailureReasons();

	public String insertMsisdn(String servicename, String msisdn);

	public ResultSet executeQuery(String tableExistsCheck);
	public List<Playlist> getPlayList();
	public List<ContentPlaylistMapper> getContent(int playlistId);
	public int addNewPlayList(String playlistName);
	public int savePlaylistContents(int playlistId,int[] insertContent);
	public List<ContentPlaylistMapper> getPlaylists(int[] playlists);
	
	public List<ContentPlaylist> getPlaylists();
	public void savePlaylistContents(ContentPlaylist playlist,ArrayList<Content> contentArrayList);
}
