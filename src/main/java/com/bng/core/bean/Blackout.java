package com.bng.core.bean;

public class Blackout {
	private String startBtime;
	private String endBtime;
	
	public Blackout() {
		super();
	}
	public Blackout(String startBtime, String endBtime) {
		super();
		this.startBtime = startBtime;
		this.endBtime = endBtime;
	}
	public String getStartBtime() {
		return startBtime;
	}
	public void setStartBtime(String startBtime) {
		this.startBtime = startBtime;
	}
	public String getEndBtime() {
		return endBtime;
	}
	public void setEndBtime(String endBtime) {
		this.endBtime = endBtime;
	}
}
