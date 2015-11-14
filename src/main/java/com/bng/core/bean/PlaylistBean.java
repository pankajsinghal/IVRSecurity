
package com.bng.core.bean;
import java.io.Serializable;

public class PlaylistBean implements Serializable {

	private String playlist;
	private String contentlist;
	private String playlistcontent;
	
	public String getContentlist() {
		return contentlist;
	}

	public void setContentlist(String contentlist) {
		this.contentlist = contentlist;
	}

	public String getPlaylistcontent() {
		return playlistcontent;
	}

	public void setPlaylistcontent(String playlistcontent) {
		this.playlistcontent = playlistcontent;
	}

	public String getPlaylist() {
		return playlist;
	}

	public void setPlaylist(String playlist) {
		this.playlist = playlist;
	}	
	
	
}
