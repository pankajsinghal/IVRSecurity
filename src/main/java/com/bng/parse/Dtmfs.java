package com.bng.parse;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name="dtmfs")
public class Dtmfs {
	
	private List<Dtmf> dtmfs;

	@XmlElement(name = "dtmf")
	public List<Dtmf> getDtmfs() {
		if (null==dtmfs) {
			dtmfs = new ArrayList<Dtmf>();
        }
		return dtmfs;
	}

	public void setDtmfs(List<Dtmf> dtmfs) {
		this.dtmfs = dtmfs;
	}
	
	@Override
    public String toString() {
        return "Dtmfs [" + (dtmfs != null ? "dtmf=" + dtmfs : "") + "]";
    }

}
