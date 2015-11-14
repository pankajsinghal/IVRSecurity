package com.bng.core.bean;

import com.bng.core.entity.Content;

public class ContentPlaylistBean
{
	private Content content;
	private int playlistNumber;	
	public ContentPlaylistBean()
	{
		super();
	}
	public ContentPlaylistBean(Content content, int playlistNumber)
	{
		super();
		this.content = content;
		this.playlistNumber = playlistNumber;
	}
	public Content getContent()
	{
		return content;
	}
	public void setContent(Content content)
	{
		this.content = content;
	}
	public int getPlaylistNumber()
	{
		return playlistNumber;
	}
	public void setPlaylistNumber(int playlistNumber)
	{
		this.playlistNumber = playlistNumber;
	}
	
}
