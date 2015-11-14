package com.bng.core.xmlparser.unmarshal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

//@XmlJavaTypeAdapter(value=StringNullAdapter.class,type=String.class)
@XmlAccessorType(XmlAccessType.FIELD)
public class Dependency {
	
	@XmlElement(name="id")
	private String id;
	
	@XmlElement(name="value")
	private String value;

	@XmlElement(name="action")
	private String action;	
	
	@XmlElement(name="tovalidate")
	private String tovalidate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTovalidate() {
		return tovalidate;
	}

	public void setTovalidate(String tovalidate) {
		this.tovalidate = tovalidate;
	}
	
	
}
