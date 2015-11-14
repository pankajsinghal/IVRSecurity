package com.bng.parse;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name="play")
public class Plays {

    private List<Media> plays;
 
    @XmlElement(name = "media")
    public List<Media> getPlays() {
        if (null==plays) {
            plays = new ArrayList<Media>();
        }
        return plays;
    }
 
    public void setPlays(List<Media> plays) {
        this.plays = plays;
    }
 
    @Override
    public String toString() {
        return "Play [" + (plays != null ? "media=" + plays : "") + "]";
    }
 
}
