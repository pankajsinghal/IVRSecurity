package com.bng.parse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="billing")
public class Billing {
	
	private String mode;  
    private String connection;
    private String destinationip;  
    private String destinationport;
    private String uri;  
    private String amount;
    private String calltime;  
    private String responsetime;
    private String response;
	
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="mode")
    public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="connection")
	public String getConnection() {
		return connection;
	}
	public void setConnection(String connection) {
		this.connection = connection;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="destinationip")
	public String getDestinationip() {
		return destinationip;
	}
	public void setDestinationip(String destinationip) {
		this.destinationip = destinationip;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="destinationport")
	public String getDestinationport() {
		return destinationport;
	}
	public void setDestinationport(String destinationport) {
		this.destinationport = destinationport;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="uri")
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="amount")
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="calltime")
	public String getCalltime() {
		return calltime;
	}
	public void setCalltime(String calltime) {
		this.calltime = calltime;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="responsetime")
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="response")
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
    
	@Override
	public String toString() {
		return "Billing ["
				+ (mode != null ? "mode=" + mode + ", " : "")
				+ (connection != null ? "connection=" + connection + ", " : "")
				+ (destinationip != null ? "destinationip=" + destinationip + ", " : "")
				+ (destinationport != null ? "destinationport=" + destinationport + ", " : "")
				+ (uri != null ? "uri=" + uri + ", " : "")
				+ (amount != null ? "amount=" + amount + ", " : "")
				+ (calltime != null ? "calltime=" + calltime + ", " : "")
				+ (responsetime != null ? "responsetime=" + responsetime + ", " : "")
				+ (response != null ? "response=" + response + " " : "")
				+ "], ";		
	}
}
