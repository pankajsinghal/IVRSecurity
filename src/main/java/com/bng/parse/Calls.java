package com.bng.parse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
 
@XmlRootElement(name="call")
public class Calls {
	
	private String status;  
    private String reason;
    
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
	
	@Override
	public String toString() {
		return "Call ["
				+ (status != null ? "status=" + status + ", " : "")
				+ (reason != null ? "reason=" + reason + " " : "")
				+ "], ";		
	}

}