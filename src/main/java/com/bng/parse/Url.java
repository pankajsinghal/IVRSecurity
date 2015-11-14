package com.bng.parse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
 
@XmlRootElement(name="url")
public class Url {
	
	private String uri;
	private String method;
	private String time;
	private String response;
	private String type;
	private String option;
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="uri")
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="method")
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="time")
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="response")
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="option")
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	
	@Override
	public String toString() {
		return "Url ["
				+ (uri != null ? "uri=" + uri + ", " : "")
				+ (method != null ? "method=" + method + ", " : "")
				+ (time != null ? "time=" + time + ", " : "")
				+ (response != null ? "response=" + response + ", " : "")
				+ (type != null ? "type=" + type + ", " : "")
				+ (option != null ? "option=" + option + "" : "")
				+ "]";		
	}

}
