package com.bng.core.bean;
 import com.bng.core.service.FileUpload;

public class UploadConfig {
	
	public static FileUpload fileup;
	public static String suburl;
	public String getSuburl() {
		return suburl;
	}

	public void setSuburl(String suburl) {
		this.suburl = suburl;
	}

	public FileUpload getFileup() {
		return UploadConfig.fileup;
	}

	public void setFileup(FileUpload fileup) {
		UploadConfig.fileup = fileup;
	}
}
