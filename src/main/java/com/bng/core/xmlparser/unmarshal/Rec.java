package com.bng.core.xmlparser.unmarshal;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "recs")
@XmlAccessorType(XmlAccessType.FIELD)
public class Rec {
	
	@XmlElement(name = "id")
	private String id;
	
	@XmlElement(name = "name")
	private String name;
	
	@XmlElement(name = "type")
	private String type;
	
	@XmlElement(name = "dependency")
	private ArrayList<Dependency> dependencyList;
	
	@XmlElement(name = "validation")
	private ArrayList<Validation> validationList;
	
	@XmlElementWrapper(name = "values")
	@XmlElement(name = "value")
	private ArrayList<Value> valueList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<Dependency> getDependencyList() {
		return dependencyList;
	}

	public void setDependencyList(ArrayList<Dependency> dependencyList) {
		this.dependencyList = dependencyList;
	}

	public ArrayList<Validation> getValidationList() {
		return validationList;
	}

	public void setValidationList(ArrayList<Validation> validationList) {
		this.validationList = validationList;
	}

	public ArrayList<Value> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<Value> valueList) {
		this.valueList = valueList;
	}


}
