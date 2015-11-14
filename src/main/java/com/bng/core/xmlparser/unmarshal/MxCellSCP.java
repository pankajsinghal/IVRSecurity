package com.bng.core.xmlparser.unmarshal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class MxCellSCP {

	@XmlAttribute(name = "id")
	private String id;

	@XmlAttribute(name = "value")
	private String value;

	@XmlAttribute(name = "style")
	private String style;

	@XmlAttribute(name = "vertex")
	private String vertex;

	@XmlAttribute(name = "imageName")
	private String imageName;

	@XmlAttribute(name = "type")
	private String type;

	@XmlAttribute(name = "parent")
	private String parent;

	@XmlAttribute(name = "xmlFileData")
	private String xmlFileData;
	
	@XmlAttribute(name = "xmlParamsData")
	private String xmlParamsData;

	@XmlAttribute(name = "edge")
	private String edge;

	@XmlAttribute(name = "source")
	private String source;

	@XmlAttribute(name = "target")
	private String target;

	@XmlElement(name = "mxGeometry", type = MxGeometrySCP.class)
	private MxGeometrySCP mxGeometry;
	
	@XmlElement(name = "mxParams", type = MxParamsSCP.class)
	private MxParamsSCP mxparams;

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

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getVertex() {
		return vertex;
	}

	public void setVertex(String vertex) {
		this.vertex = vertex;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getXmlFileData() {
		return xmlFileData;
	}

	public void setXmlFileData(String xmlFileData) {
		this.xmlFileData = xmlFileData;
	}

	public String getXmlParamsData() {
		return xmlParamsData;
	}

	public void setXmlParamsData(String xmlParamsData) {
		this.xmlParamsData = xmlParamsData;
	}

	public String getEdge() {
		return edge;
	}

	public void setEdge(String edge) {
		this.edge = edge;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public MxGeometrySCP getMxGeometry() {
		return mxGeometry;
	}

	public void setMxGeometry(MxGeometrySCP mxGeometry) {
		this.mxGeometry = mxGeometry;
	}

	public MxParamsSCP getMxparams() {
		return mxparams;
	}

	public void setMxparams(MxParamsSCP mxparams) {
		this.mxparams = mxparams;
	}

}
