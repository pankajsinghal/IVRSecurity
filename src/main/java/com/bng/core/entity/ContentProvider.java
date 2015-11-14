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
@Table(name="content_provider")
public class ContentProvider  implements java.io.Serializable 
{
     private Integer id;
     private String providerName;
     private Boolean active;
     private Set<ContentMeta> contentMetas = new HashSet(0);

     public ContentProvider() 
     {
     }

     public ContentProvider(String providerName, Boolean active, Set<ContentMeta> contentMetas) 
     {
       this.providerName = providerName;
       this.active = active;
       this.contentMetas = contentMetas;
     }
     
   
     public ContentProvider(String providerName) 
     {
		this.providerName = providerName;		
     }
     
     public ContentProvider(String providerName,Boolean active) 
     {
		this.providerName = providerName;		
		this.active=active;
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
    
     @Column(name="provider_name")
     public String getProviderName() 
     {
        return this.providerName;
     }
    
     public void setProviderName(String providerName) 
     {
        this.providerName = providerName;
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

     @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="contentProvider")
     public Set<ContentMeta> getContentMetas() 
     {
        return this.contentMetas;
     }
    
     public void setContentMetas(Set<ContentMeta> contentMetas) 
     {
        this.contentMetas = contentMetas;
     }
}