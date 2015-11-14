package com.bng.core.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

import com.bng.core.bean.JobBean;
import com.bng.core.da.SchedulerDao;
import com.bng.core.entity.Content;
import com.bng.core.entity.ContentPlaylist;
import com.bng.core.entity.ContentPlaylistMapper;
import com.bng.core.entity.Mxgraph;
import com.bng.core.entity.ObdBlackoutHours;
import com.bng.core.entity.ObdCli;
import com.bng.core.entity.Playlist;
import com.bng.core.entity.Service;

public class SchedulerBoImp implements SchedulerBo {

	private SchedulerDao schedulerDao;

	public void setSchedulerDao(SchedulerDao schedulerDao) {
		this.schedulerDao = schedulerDao;
	}

	public List<Mxgraph> getService() {

		return schedulerDao.getService();
	}

	@Override
	public List getmxgraph(int id) {

		return schedulerDao.getmxgraph(id);
	}

	@Override
	public Mxgraph getmxgraph(String servicename, String shortcode) {
		return schedulerDao.getmxgraph(servicename, shortcode);
	}

	@Override
	public void addJob(Service service) {
		schedulerDao.addJob(service);
	}

	@Override
	public ObdBlackoutHours getobh(int i) {
		return schedulerDao.getobh(i);
	}

	@Override
	public Service getService(String servicename, String shortcode) {
		return schedulerDao.getService(servicename, shortcode);
	}

	@Override
	public void addCli(ObdCli cli) {
		schedulerDao.addCli(cli);

	}

	@Override
	public void addobh(ObdBlackoutHours blackout) {
		schedulerDao.addobh(blackout);
	}

	@Override
	public String getJobname(String jobname) {
		return schedulerDao.getJobname(jobname);
	}

	@Override
	public List getServicelist(Integer id) {

		return schedulerDao.getServicelist(id);
	}

	// @Override
	// public void addMsisdns(ArrayList<String> msisdns,String jobname) {
	// schedulerDao.addMsisdns(msisdns,jobname);
	// }

	@Override
	public Mxgraph mxgraph(int id) {

		return schedulerDao.mxgraph(id);
	}

	@Override
	public void dndFilter(String tableName, int i) {
		// TODO Auto-generated method stub
		schedulerDao.dndFilter(tableName, i);
	}

	@Override
	public Service getService(String jobname) {
		return schedulerDao.getService(jobname);
	}

	@Override
	public void updatejob(Service service) {
		schedulerDao.updatejob(service);
	}

	@Override
	public List<ObdCli> getCli(Service service) {
		return schedulerDao.getCli(service);
	}

	@Override
	public void deleteCli(Service service) {
		schedulerDao.deleteCli(service);

	}

	// @Override
	// public void addMsisdns(String path, String jobname) {
	// // TODO Auto-generated method stub
	// schedulerDao.addMsisdns(path,jobname);
	// }

	@Override
	public void addJob(String absolutePath, Service service,
			String serviceName, String shortCode, JobBean jobBean) {
		schedulerDao.addJob(absolutePath, service, serviceName, shortCode,
				jobBean);
	}

	@Override
	public NameValuePair addMsisdnsMysql(ArrayList<String> create_table, String sql) {
		return schedulerDao.addMsisdnsMysql(create_table, sql);
	}

	@Override
	public List getObdFailureReasons() {
		// TODO Auto-generated method stub
		return schedulerDao.getObdFailureReasons();
	}


	@Override
	public String insertMsisdn(String servicename, String msisdn) {
		return schedulerDao.insertMsisdn(servicename,msisdn);
		
	}


	@Override
	public ResultSet executeQuery(String tableExistsCheck) {
		// TODO Auto-generated method stub
		return schedulerDao.executeQuery(tableExistsCheck);
	}
	
	@Override
	public List<Playlist> getplaylist() {

		return schedulerDao.getPlayList();
	}
	
	@Override
	public List<ContentPlaylistMapper> getContent(int playlistId)
	{
		return schedulerDao.getContent(playlistId);
	}
	
	@Override
	public int addNewPlayList(String playlistName)
	{
		return schedulerDao.addNewPlayList(playlistName);
	}
	
	//@Override
	public int savePlaylistContents(int playlistId,int[] insertContent)
	{
		return schedulerDao.savePlaylistContents(playlistId,insertContent);
	}
	
	@Override
	public List<ContentPlaylistMapper> getPlaylists(int[] playlists)
	{
		return schedulerDao.getPlaylists(playlists);
	}
	
	@Override
	public List<ContentPlaylist> getPlaylists()
	{
		return schedulerDao.getPlaylists();
	}
	
	@Override
	public void savePlaylistContents(ContentPlaylist playlist,ArrayList<Content> contentArrayList)
	{
		schedulerDao.savePlaylistContents(playlist,contentArrayList);
	}
}
