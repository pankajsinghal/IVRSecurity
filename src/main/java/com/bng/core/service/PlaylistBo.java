package com.bng.core.service;

import java.util.List;

import com.bng.core.entity.ContentPlaylistMapper;

public interface PlaylistBo {


	public List getplaylist();
	public List<ContentPlaylistMapper> getContent(int playlistId);

	public int addNewPlayList(String playlistName);
	public int savePlaylistContents(int playlistId,int[] deleteContent ,int[] playContent);
}
