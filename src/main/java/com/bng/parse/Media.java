package com.bng.parse;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
 
@XmlRootElement(name="media")
public class Media {
     
	private String type;  
    private String location;
    private String filename;  
    private String starttime;
    private String endtime;
    private String code;
    
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="type")
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="location")
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="filename")
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="starttime")
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="endtime")
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlElement(name="code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return "Media ["
				+ (type != null ? "type=" + type + ", " : "")
				+ (location != null ? "location=" + location + ", " : "")
				+ (filename != null ? "filename=" + filename + ", " : "")
				+ (starttime != null ? "starttime=" + starttime + ", " : "")
				+ (endtime != null ? "endtime=" + endtime + ", " : "")
				+ (code != null ? "code=" + code + "" : "")
				+ "]";		
	}
 
}
