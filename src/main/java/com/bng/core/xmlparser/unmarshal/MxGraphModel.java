package com.bng.core.xmlparser.unmarshal;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mxGraphModel")
@XmlAccessorType(XmlAccessType.FIELD)
public class MxGraphModel {

	@XmlAttribute(name = "scale")
	private String scale;
	
	@XmlAttribute(name = "grid")
	private String grid;

	@XmlAttribute(name = "guides")
	private String guides;

	@XmlAttribute(name = "tooltips")
	private String tooltips;

	@XmlAttribute(name = "connect")
	private String connect;

	@XmlAttribute(name = "fold")
	private String fold;

	@XmlAttribute(name = "page")
	private String page;

	@XmlAttribute(name = "pageScale")
	private String pageScale;

	@XmlAttribute(name = "pageWidth")
	private String pageWidth;

	@XmlAttribute(name = "pageHeight")
	private String pageHeight;

	@XmlElementWrapper(name = "root")
	@XmlElement(name = "mxCell", type = MxCellSCP.class)
	private ArrayList<MxCellSCP> mxCellList;

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public String getGuides() {
		return guides;
	}

	public void setGuides(String guides) {
		this.guides = guides;
	}

	public String getTooltips() {
		return tooltips;
	}

	public void setTooltips(String tooltips) {
		this.tooltips = tooltips;
	}

	public String getConnect() {
		return connect;
	}

	public void setConnect(String connect) {
		this.connect = connect;
	}

	public String getFold() {
		return fold;
	}

	public void setFold(String fold) {
		this.fold = fold;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPageScale() {
		return pageScale;
	}

	public void setPageScale(String pageScale) {
		this.pageScale = pageScale;
	}

	public String getPageWidth() {
		return pageWidth;
	}

	public void setPageWidth(String pageWidth) {
		this.pageWidth = pageWidth;
	}

	public String getPageHeight() {
		return pageHeight;
	}

	public void setPageHeight(String pageHeight) {
		this.pageHeight = pageHeight;
	}

	public ArrayList<MxCellSCP> getMxCellList() {
		return mxCellList;
	}

	public void setMxCellList(ArrayList<MxCellSCP> mxCellList) {
		this.mxCellList = mxCellList;
	}



}
