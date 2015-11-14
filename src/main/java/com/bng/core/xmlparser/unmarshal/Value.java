package com.bng.core.xmlparser.unmarshal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(namespace = "values")
@XmlAccessorType(XmlAccessType.FIELD)
public class Value {
	
	@XmlAttribute(name="dependency")
	private String dependency;
	
	@XmlValue
	private String value;

	public String getDependency() {
		return dependency;
	}

	public void setDependency(String dependency) {
		this.dependency = dependency;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	} 

	
	
}
