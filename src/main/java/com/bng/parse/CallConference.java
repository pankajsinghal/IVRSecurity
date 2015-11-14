package com.bng.parse;

import com.bng.parse.CallConf;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="callconference")
public class CallConference {
	
	private List<CallConf> callconferences;
	 
    @XmlElement(name = "callconf")
    public List<CallConf> getCallConference() {
        if (null==callconferences) {
        	callconferences = new ArrayList<CallConf>();
        }
        return callconferences;
    }
 
    public void setPlays(List<CallConf> callconferences) {
        this.callconferences = callconferences;
    }
 
    @Override
    public String toString() {
        return "CallConference [" + (callconferences != null ? "callconf=" + callconferences : "") + "]";
    }

}
