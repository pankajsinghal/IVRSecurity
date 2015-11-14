package com.bng.core.bean;

public class StatusBean {
	
	String ivrcode;
	Integer dtmf;
	String aparty;
	String callstate;
	String calltype;

	
	public String getIvrcode() {
		return ivrcode;
	}
	public void setIvrcode(String ivrcode) {
		this.ivrcode = ivrcode;
	}
	
	public String getAparty() {
		return aparty;
	}
	public void setAparty(String aparty) {
		this.aparty = aparty;
	}
	public String getCallstate() {
		return callstate;
	}
	public void setCallstate(String callstate) {
		this.callstate = callstate;
	}
	public Integer getDtmf() {
		return dtmf;
	}
	public void setDtmf(Integer dtmf) {
		this.dtmf = dtmf;
	}
	public String getCalltype() {
		return calltype;
	}
	public void setCalltype(String calltype) {
		this.calltype = calltype;
	}

}
