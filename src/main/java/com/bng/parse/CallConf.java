package com.bng.parse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="callconf")
public class CallConf {
	
	private String confid;  
    private String systemip;
    private String hardware;  
    private String protocol;
    private String calltype;
    private String aparty;
	private String bparty;
	private String vid;  
    private String cic;
    private String dialtime;  
    private String startdatetime;
    private String enddatetime;
    private String status;
	private String reason;
	private String operator;
    private String circle;
	
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="confid")
    public String getConfid() {
		return confid;
	}
	public void setConfid(String confid) {
		this.confid = confid;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="systemip")
	public String getSystemip() {
		return systemip;
	}
	public void setSystemip(String systemip) {
		this.systemip = systemip;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="hardware")
	public String getHardware() {
		return hardware;
	}
	public void setHardware(String hardware) {
		this.hardware = hardware;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="protocol")
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="calltype")
	public String getCalltype() {
		return calltype;
	}
	public void setCalltype(String calltype) {
		this.calltype = calltype;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="aparty")
	public String getAparty() {
		return aparty;
	}
	public void setAparty(String aparty) {
		this.aparty = aparty;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="bparty")
	public String getBparty() {
		return bparty;
	}
	public void setBparty(String bparty) {
		this.bparty = bparty;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="vid")
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="cic")
	public String getCic() {
		return cic;
	}
	public void setCic(String cic) {
		this.cic = cic;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="dialtime")
	public String getDialtime() {
		return dialtime;
	}
	public void setDialtime(String dialtime) {
		this.dialtime = dialtime;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="startdatetime")
	public String getStartdatetime() {
		return startdatetime;
	}
	public void setStartdatetime(String startdatetime) {
		this.startdatetime = startdatetime;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="enddatetime")
	public String getEnddatetime() {
		return enddatetime;
	}
	public void setEnddatetime(String enddatetime) {
		this.enddatetime = enddatetime;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="reason")
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="operator")
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="circle")
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	
	@Override
	public String toString() {
		return "CallConf ["
				+ (confid != null ? "confid=" + confid + ", " : "")
				+ (systemip != null ? "systemip=" + systemip + ", " : "")
				+ (hardware != null ? "hardware=" + hardware + ", " : "")
				+ (protocol != null ? "protocol=" + protocol + ", " : "")
				+ (calltype != null ? "calltype=" + calltype + ", " : "")
				+ (aparty != null ? "aparty=" + aparty + ", " : "")
				+ (bparty != null ? "bparty=" + bparty + ", " : "")
				+ (vid != null ? "vid=" + vid + ", " : "")
				+ (cic != null ? "cic=" + cic + ", " : "")
				+ (dialtime != null ? "dialtime=" + dialtime + ", " : "")
				+ (startdatetime != null ? "startdatetime=" + startdatetime + ", " : "")
				+ (enddatetime != null ? "enddatetime=" + enddatetime + ", " : "")
				+ (status != null ? "status=" + status + ", " : "")
				+ (reason != null ? "reason=" + reason + ", " : "")
				+ (operator != null ? "operator=" + operator + ", " : "")
				+ (circle != null ? "circle=" + circle + "" : "")
				+ "]";		
	}
    
}
