package com.bng.core.service;

import java.util.List;

import com.bng.core.da.PlaylistDao;
import com.bng.core.entity.ContentPlaylistMapper;
import com.bng.core.entity.Playlist;

public class PlaylistBoImp implements PlaylistBo {

	PlaylistDao playlistDao;


	
	@Override
	public List<Playlist> getplaylist() {

		return playlistDao.getPlayList();
	}
	
	@Override
	public List<ContentPlaylistMapper> getContent(int playlistId)
	{
		return playlistDao.getContent(playlistId);
	}
	
	@Override
	public int addNewPlayList(String playlistName)
	{
		return playlistDao.addNewPlayList(playlistName);
	}
	
	@Override
	public int savePlaylistContents(int playlistId,int[] deleteContent ,int[] playContent)
	{
		return playlistDao.savePlaylistContents(playlistId,deleteContent, playContent);
	}
}
