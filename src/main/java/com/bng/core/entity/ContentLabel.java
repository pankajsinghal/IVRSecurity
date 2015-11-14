/**
 * 
 */
package com.bng.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Harish Shan
 *
 */

@Entity
@Table(name="content_label")
public class ContentLabel  implements java.io.Serializable 
{
	private Integer id;
    private String labelName;
    private Boolean active;
    private Set<ContentMeta> contentMetas = new HashSet(0);

    public ContentLabel() 
    {
    }
    public ContentLabel(String labelName, Boolean active, Set<ContentMeta> contentMetas) 
    {
       this.labelName = labelName;
       this.active = active;
       this.contentMetas = contentMetas;
    }
    
    public ContentLabel(String labelName, Boolean active) 
    {
       this.labelName = labelName;
       this.active = active;     
    }
    public ContentLabel(String labelName) 
    {
       this.labelName = labelName;      
    }
 
    @Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() 
    {
        return this.id;
    }
    
    public void setId(Integer id) 
    {
        this.id = id;
    }
    
    @Column(name="label_name")
    public String getLabelName() 
    {
        return this.labelName;
    }
    
    public void setLabelName(String labelName) 
    {
        this.labelName = labelName;
    }
    
    @Column(name="active")
    public Boolean getActive() 
    {
        return this.active;
    }
    
    public void setActive(Boolean active) 
    {
        this.active = active;
    }

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="contentLabel" )
    public Set<ContentMeta> getContentMetas() 
    {
        return this.contentMetas;
    }
    
    public void setContentMetas(Set<ContentMeta> contentMetas) 
    {
        this.contentMetas = contentMetas;
    }
}