package com.bng.core.da;

import java.util.List;

import com.bng.core.entity.ContentPlaylistMapper;
import com.bng.core.entity.Playlist;

public interface PlaylistDao {


	public List<Playlist> getPlayList();
	public List<ContentPlaylistMapper> getContent(int playlistId);
	public int addNewPlayList(String playlistName);
	public int savePlaylistContents(int playlistId,int[] deleteContent ,int[] playContent);
}
