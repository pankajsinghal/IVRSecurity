package com.bng.parse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="bngcallcdr")
public class CallCdr {
	
	private String tesystemip;
	private String cesystemip;
	private String hardware;
	private String protocol;
	private String calltype;
	private String cic;
	private String callid;
	private String aparty;
	private String bparty;
	private String shortcode;
	private String servicename;
	private String timezone;
	private String country;
	private String operator;
	private String circle;
	private String startdatetime;
	private String pickupdatetime;
	private String enddatetime;
	private String callstatus;
	private String releasereason;
	private Plays plays;
	private CallConference callconferences;
	private Dtmfs dtmfs;
	private Urls urls;
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="tpsystemip")
	public String getTesystemip() {
		return tesystemip;
	}
	public void setTesystemip(String tesystemip) {
		this.tesystemip = tesystemip;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="cesystemip")
	public String getCesystemip() {
		return cesystemip;
	}
	public void setCesystemip(String cesystemip) {
		this.cesystemip = cesystemip;
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
    @XmlElement(name="cic")
	public String getCic() {
		return cic;
	}
	public void setCic(String cic) {
		this.cic = cic;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="callid")
	public String getCallid() {
		return callid;
	}
	public void setCallid(String callid) {
		this.callid = callid;
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
    @XmlElement(name="shortcode")
	public String getShortcode() {
		return shortcode;
	}
	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="servicename")
	public String getServicename() {
		return servicename;
	}
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="timezone")
	public String getTimeZone() {
		return timezone;
	}
	public void setTimeZone(String timezone) {
		this.timezone = timezone;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="country")
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="startdatetime")
	public String getStartdatetime() {
		return startdatetime;
	}
	public void setStartdatetime(String startdatetime) {
		this.startdatetime = startdatetime;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="pickupdatetime")
	public String getPickupdatetime() {
		return pickupdatetime;
	}
	public void setPickupdatetime(String pickupdatetime) {
		this.pickupdatetime = pickupdatetime;
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
	public String getCallstatus() {
		return callstatus;
	}
	public void setCallstatus(String callstatus) {
		this.callstatus = callstatus;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="releasereason")
	public String getReleasereason() {
		return releasereason;
	}
	public void setReleasereason(String releasereason) {
		this.releasereason = releasereason;
	}
	
	@XmlElement(name="play")
    public Plays getPlays() {
        return plays;
    }
 
    public void setPlays(Plays plays) {
        this.plays = plays;
    }
    
    @XmlElement(name="callconference")
    public CallConference getCallConference() {
        return callconferences;
    }
 
    public void setCallConference(CallConference callconferences) {
        this.callconferences = callconferences;
    }
    
    @XmlElement(name="dtmfs")
	public Dtmfs getDtmfs() {
		return dtmfs;
	}
	public void setDtmfs(Dtmfs dtmfs) {
		this.dtmfs = dtmfs;
	}
	
	@XmlElement(name="urls")
	public Urls getUrls() {
		return urls;
	}
	public void setUrls(Urls urls) {
		this.urls = urls;
	}
	
	@Override
	public String toString() {
		return "CallCdr ["
				+ (tesystemip != null ? "tesystemip=" + tesystemip + ", " : "")
				+ (cesystemip != null ? "cesystemip=" + cesystemip + ", " : "")
				+ (hardware != null ? "hardware=" + hardware + ", " : "")
				+ (protocol != null ? "protocol=" + protocol + ", " : "")
				+ (calltype != null ? "calltype=" + calltype + ", " : "")
				+ (cic != null ? "cic=" + cic + ", " : "")
				+ (callid != null ? "callid=" + callid + ", " : "")
				+ (aparty != null ? "aparty=" + aparty + ", " : "")
				+ (bparty != null ? "bparty=" + bparty + ", " : "")
				+ (shortcode != null ? "shortcode=" + shortcode + ", " : "")
				+ (servicename != null ? "servicename=" + servicename + ", " : "")
				+ (timezone != null ? "timezone=" + timezone + ", " : "")
				+ (country != null ? "country=" + country + ", " : "")
				+ (operator != null ? "operator=" + operator + ", " : "")
				+ (circle != null ? "circle=" + circle + ", " : "")
				+ (startdatetime != null ? "startdatetime=" + startdatetime + ", " : "")
				+ (pickupdatetime != null ? "pickupdatetime=" + pickupdatetime + ", " : "")
				+ (enddatetime != null ? "enddatetime=" + enddatetime + ", " : "")
				+ (callstatus != null ? "callstatus=" + callstatus + ", " : "")
				+ (releasereason != null ? "releasereason=" + releasereason + ", " : "")
				+ (plays != null ? "play=" + plays + ", " : "")
				+ (callconferences != null ? "callconferences=" + callconferences + "" : "")
				+ (dtmfs != null ? "dtmfs=" + dtmfs + ", " : "")
				+ (urls != null ? "urls=" + urls + "" : "")
				+ "]";		
	}
	
}
