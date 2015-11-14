package cn.bluejoe.elfinder.impl;

import cn.bluejoe.elfinder.service.FsServiceConfig;

public class DefaultFsServiceConfig implements FsServiceConfig
{
	private int _tmbWidth;

	public void setTmbWidth(int tmbWidth)
	{
		_tmbWidth = tmbWidth;
	}

	public int getTmbWidth()
	{
		return _tmbWidth;
	}
}
