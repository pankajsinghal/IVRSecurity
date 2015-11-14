package com.bng.core.bean;

import org.springframework.web.multipart.MultipartFile;

public class Upload {
	
	private MultipartFile blacklist;
	private MultipartFile whitelist;
	private MultipartFile redcarpet;
	private Integer isSeries;
	
	public MultipartFile getBlacklist() {
		return blacklist;
	}
	public void setBlacklist(MultipartFile blacklist) {
		this.blacklist = blacklist;
	}
	public MultipartFile getWhitelist() {
		return whitelist;
	}
	public void setWhitelist(MultipartFile whitelist) {
		this.whitelist = whitelist;
	}
	public MultipartFile getRedcarpet() {
		return redcarpet;
	}
	public void setRedcarpet(MultipartFile redcarpet) {
		this.redcarpet = redcarpet;
	}
	public Integer getIsSeries() {
		return isSeries;
	}
	public void setIsSeries(Integer isSeries) {
		this.isSeries = isSeries;
	}
	
	
}
