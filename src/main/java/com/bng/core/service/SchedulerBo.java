package com.bng.core.service;

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
import com.bng.core.entity.Service;

public interface SchedulerBo {
	
	public Service getService(String serviceName, String shortCode);
	public List getmxgraph(int id);
	public Mxgraph getmxgraph(String servicename, String shortCode);
	public void addJob(Service service);
	public ObdBlackoutHours getobh(int i);
	public void addCli(ObdCli cli);
	public void addobh(ObdBlackoutHours blackout);
	public String getJobname(String jobname);
	public List getServicelist(Integer id);
	public Mxgraph mxgraph(int id);
//	public void addMsisdns(ArrayList<String> msisdns, String jobname);
	public void dndFilter(String tableName, int i);
	public Service getService(String jobname);
	public void updatejob(Service service);
	public List<ObdCli> getCli(Service service);
	void deleteCli(Service service);
//	public void addMsisdns(String Path, String jobname);
	public void addJob(String absolutePath, Service service, String serviceName, String shortCode, JobBean jb);
	public NameValuePair addMsisdnsMysql(ArrayList<String> create_table, String sql);
	public List getObdFailureReasons();

	public String insertMsisdn(String servicename, String msisdn);

	public ResultSet executeQuery(String tableExistsCheck);
	public List getplaylist();
	public List<ContentPlaylist> getPlaylists();
	public List<ContentPlaylistMapper> getContent(int playlistId);

	public int addNewPlayList(String playlistName);
	public int savePlaylistContents(int playlistId,int[] insertContent);
	public void savePlaylistContents(ContentPlaylist playlist,ArrayList<Content> contentArrayList);
	public List<ContentPlaylistMapper> getPlaylists(int[] playlists);

}
