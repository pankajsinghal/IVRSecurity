/**
 * 
 */
package com.bng.core.da;

import com.bng.core.entity.Content;
import com.bng.core.entity.ContentCategory;
import com.bng.core.entity.ContentLabel;
import com.bng.core.entity.ContentMapper;
import com.bng.core.entity.ContentMeta;
import com.bng.core.entity.ContentMetaExtra;
import com.bng.core.entity.ContentPlaylist;
import com.bng.core.entity.ContentPlaylistMapper;
import com.bng.core.entity.ContentProvider;

public interface FileUploadDao 
{
	public ContentProvider saveContentProvider(ContentProvider contentProvider);
	public void saveContent(Content content);
	public ContentLabel saveContentLabel(ContentLabel contentLabel);
	public ContentCategory saveContentCategory(ContentCategory contentCategory);
	public void saveContentMeta(ContentMeta contentMeta);
	public void saveContentMetaExtra(ContentMetaExtra contentMetaExtra);
	public void saveContentMapper(ContentMapper contentMapper);
	public ContentPlaylist saveContentPlaylist(ContentPlaylist contentPlaylist);
	public void saveContentPlaylistMapper(ContentPlaylistMapper contentPlaylistMapper);
}
