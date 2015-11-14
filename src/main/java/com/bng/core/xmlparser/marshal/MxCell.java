/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bng.core.xmlparser.marshal;

/**
 *
 * @author richa
 */
import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
  
//Below statement means that class "IVR.java" is the root-element of our example  
@XmlRootElement(namespace = "root")  
@XmlAccessorType(XmlAccessType.FIELD)
public class MxCell implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlAttribute(name="id")
    private Integer id;
    
    @XmlAttribute(name="type")
    private String type;
    
    @XmlAttribute(name="value")
    private String value;
    
    @XmlAttribute(name="source")
    private Integer source;
    
    @XmlAttribute(name="target")
    private Integer target;   
        
    @XmlElementWrapper(name = "mxParams") 
    @XmlElement(name = "mxParam" , type = MxParam.class)  
    private ArrayList<MxParam> listOfMxParam;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public ArrayList<MxParam> getListOfMxParam() {
		return listOfMxParam;
	}

	public void setListOfMxParam(ArrayList<MxParam> listOfMxParam) {
		this.listOfMxParam = listOfMxParam;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    	

}