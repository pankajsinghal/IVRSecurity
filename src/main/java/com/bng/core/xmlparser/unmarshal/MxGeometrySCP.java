package com.bng.core.xmlparser.unmarshal;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class MxGeometrySCP {

	@XmlAttribute(name = "x")
	private String x;
	
	@XmlAttribute(name = "y")
	private String y;
	
	@XmlAttribute(name = "width")
	private String width;
	
	@XmlAttribute(name = "height")
	private String height;
	
	@XmlAttribute(name = "as")
	private String as;

	@XmlElement(name = "mxPoint", type = MxPoint.class)
	private ArrayList<MxPoint> mxPointList;

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getAs() {
		return as;
	}

	public void setAs(String as) {
		this.as = as;
	}

	public ArrayList<MxPoint> getMxPointList() {
		return mxPointList;
	}

	public void setMxPointList(ArrayList<MxPoint> mxPointList) {
		this.mxPointList = mxPointList;
	}


}
