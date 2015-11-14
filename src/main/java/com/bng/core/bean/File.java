/**
 * 
 */
package com.bng.core.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Harish Shan
 *
 */
@XmlRootElement
@XmlType(propOrder = {"id","path","label","checked"})
public class File 
{
	private int id;
	private String path;
	private String label;
	private String checked;

	@XmlElement
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public File()
	{
		
	}
	public File(int id,String path,String label,String checked)
	{
		this.id=id;
		this.path=path;
		this.label=label;
		this.checked=checked;
	}
	
	@XmlElement
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	
	@XmlElement
	public String getPath() 
	{
		return path;
	}
	public void setPath(String path) 
	{
		this.path = path;
	}
	
	@XmlElement
	public String getLabel() 
	{
		return label;
	}
	public void setLabel(String label) 
	{
		this.label = label;
	}
	
}
