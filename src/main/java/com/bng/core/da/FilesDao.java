package com.bng.core.da;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bng.core.bean.File;

@XmlRootElement(name = "files")
public class FilesDao 
{
	@XmlAnyElement
    public List<File> files;

    public FilesDao() {
        files = new LinkedList<File>();
    }

    public void addFile(File file) {
        files.add(file);
    }
    public void normalise()
    {
    	int size=files.size();
    	if(size==0||size==1)
    		files.add(new File());    	
    }
}
