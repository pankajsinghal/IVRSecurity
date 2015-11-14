package com.bng.parse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
 
@XmlRootElement(name="dtmf")
public class Dtmf {
	
	private String dtmftime;
	private String dtmfinput;
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="dtmftime")
	public String getDtmftime() {
		return dtmftime;
	}
	public void setDtmftime(String dtmftime) {
		this.dtmftime = dtmftime;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="dtmfinput")
	public String getDtmfinput() {
		return dtmfinput;
	}
	public void setDtmfinput(String dtmfinput) {
		this.dtmfinput = dtmfinput;
	}
	
	@Override
	public String toString() {
		return "Dtmf ["
				+ (dtmftime != null ? "dtmftime=" + dtmftime + ", " : "")
				+ (dtmfinput != null ? "dtmfinput=" + dtmfinput + "" : "")
				+ "]";		
	}

}
