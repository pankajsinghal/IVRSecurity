package com.bng.parse;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name="urls")
public class Urls {
	
	private List<Url> urls;

	@XmlElement(name = "url")
	public List<Url> getUrls() {
		if (null==urls) {
			urls = new ArrayList<Url>();
        }
		return urls;
	}

	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}
	
	@Override
    public String toString() {
        return "Url [" + (urls != null ? "url=" + urls : "") + "]";
    }

}
