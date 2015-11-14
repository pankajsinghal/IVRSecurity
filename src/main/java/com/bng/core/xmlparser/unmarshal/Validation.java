package com.bng.core.xmlparser.unmarshal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

//@XmlJavaTypeAdapter(value=StringNullAdapter.class,type=String.class)
@XmlAccessorType(XmlAccessType.FIELD)
public class Validation {
	
	@XmlElement(name="minlimit")
	private String minlimit;
	
	@XmlElement(name="maxlimit")
	private String maxlimit;

	@XmlElement(name="minvalue")
	private String minvalue;	
	
	@XmlElement(name="maxvalue")
	private String maxvalue;	

	@XmlElement(name="datatype")
	private String datatype;	

	@XmlElement(name="optional")
	private String optional;	

	@XmlElement(name="message")
	private String message;

	public String getMinlimit() {
		return minlimit;
	}

	public void setMinlimit(String minlimit) {
		this.minlimit = minlimit;
	}

	public String getMaxlimit() {
		return maxlimit;
	}

	public void setMaxlimit(String maxlimit) {
		this.maxlimit = maxlimit;
	}

	public String getMinvalue() {
		return minvalue;
	}

	public void setMinvalue(String minvalue) {
		this.minvalue = minvalue;
	}

	public String getMaxvalue() {
		return maxvalue;
	}

	public void setMaxvalue(String maxvalue) {
		this.maxvalue = maxvalue;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getOptional() {
		return optional;
	}

	public void setOptional(String optional) {
		this.optional = optional;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}
