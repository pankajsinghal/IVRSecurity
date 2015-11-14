package com.bng.core.xmlparser.unmarshal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class MxPoint {
	@XmlAttribute(name = "x")
	private String x;

	@XmlAttribute(name = "y")
	private String y;
	
	@XmlAttribute(name = "as")
	private String as;

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

	public String getAs() {
		return as;
	}

	public void setAs(String as) {
		this.as = as;
	}
	
}
