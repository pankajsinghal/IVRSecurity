/**
 * 
 */
package com.bng.core.bean;

/**
 * @author Santosh-pc
 *
 */
public class FileMeta 
{
	String path;
	String type;
	String expireDate;
	String contentProvider;
	String categoryName;
	String labelName;
	String language;
	String songcode;
	String songname;	
	String countryName;
	String operatorName;
	String downloadUrl;
	String crbtUrl;
	String billingUrl;
	String options;
	String playlist;
	
	
	public FileMeta(String path, String type, String expireDate,
			String contentProvider, String categoryName, String labelName,
			String language, String songcode, String songname,
			String countryName, String operatorName, String downloadUrl,
			String crbtUrl, String billingUrl, String options,String playlist) 
	{
		super();
		this.path = path;
		this.type = type;
		this.expireDate = expireDate;
		this.contentProvider = contentProvider;
		this.categoryName = categoryName;
		this.labelName = labelName;
		this.language = language;
		this.songcode = songcode;
		this.songname = songname;
		this.countryName = countryName;
		this.operatorName = operatorName;
		this.downloadUrl = downloadUrl;
		this.crbtUrl = crbtUrl;
		this.billingUrl = billingUrl;
		this.options = options;
		this.playlist=playlist;
	}
	public FileMeta(String path, String type, String expireDate,
			String contentProvider, String categoryName, String labelName,
			String language, String songcode, String songname,
			String countryName, String operatorName, String downloadUrl,
			String crbtUrl, String billingUrl, String options) 
	{
		super();
		this.path = path;
		this.type = type;
		this.expireDate = expireDate;
		this.contentProvider = contentProvider;
		this.categoryName = categoryName;
		this.labelName = labelName;
		this.language = language;
		this.songcode = songcode;
		this.songname = songname;
		this.countryName = countryName;
		this.operatorName = operatorName;
		this.downloadUrl = downloadUrl;
		this.crbtUrl = crbtUrl;
		this.billingUrl = billingUrl;
		this.options = options;		
	}
	public String getPath() 
	{
		return path;
	}
	public void setPath(String path) 
	{
		this.path = path;
	}
	public String getType() 
	{
		return type;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	public String getExpireDate() 
	{
		return expireDate;
	}
	public void setExpireDate(String expireDate) 
	{
		this.expireDate = expireDate;
	}
	public String getContentProvider() 
	{
		return contentProvider;
	}
	public void setContentProvider(String contentProvider) 
	{
		this.contentProvider = contentProvider;
	}
	public String getCategoryName() 
	{
		return categoryName;
	}
	public void setCategoryName(String categoryName) 
	{
		this.categoryName = categoryName;
	}
	public String getLabelName() 
	{
		return labelName;
	}
	public void setLabelName(String labelName) 
	{
		this.labelName = labelName;
	}
	public String getLanguage() 
	{
		return language;
	}
	public void setLanguage(String language) 
	{
		this.language = language;
	}
	public String getSongcode() 
	{
		return songcode;
	}
	public void setSongcode(String songcode) 
	{
		this.songcode = songcode;
	}
	public String getSongname() 
	{
		return songname;
	}
	public void setSongname(String songname) 
	{
		this.songname = songname;
	}
	public String getCountryName() 
	{
		return countryName;
	}
	public void setCountryName(String countryName) 
	{
		this.countryName = countryName;
	}
	public String getOperatorName() 
	{
		return operatorName;
	}
	public void setOperatorName(String operatorName) 
	{
		this.operatorName = operatorName;
	}
	public String getDownloadUrl() 
	{
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) 
	{
		this.downloadUrl = downloadUrl;
	}
	public String getCrbtUrl() 
	{
		return crbtUrl;
	}
	public void setCrbtUrl(String crbtUrl) 
	{
		this.crbtUrl = crbtUrl;
	}
	public String getBillingUrl() 
	{
		return billingUrl;
	}
	public void setBillingUrl(String billingUrl) 
	{
		this.billingUrl = billingUrl;
	}
	public String getOptions() 
	{
		return options;
	}
	public void setOptions(String options) 
	{
		this.options = options;
	}
	public String getPlaylist()
	{
		return playlist;
	}
	public void setPlaylist(String playlist)
	{
		this.playlist=playlist;
	}
}