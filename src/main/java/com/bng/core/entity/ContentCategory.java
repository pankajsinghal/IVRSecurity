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
@Table(name="content_category")
public class ContentCategory  implements java.io.Serializable 
{
     private Integer id;
     private String name;
     private Set<ContentMeta> contentMetas = new HashSet(0);
     
     public ContentCategory() 
     {
     }
     
     public ContentCategory(String name, Set<ContentMeta> contentMetas) 
     {
    	 this.name = name;
    	 this.contentMetas = contentMetas;
     }
     
     public ContentCategory(String name) 
     {
    	 this.name = name;    	 
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
    
    
     @Column(name="name")
     public String getName() 
     {
    	 return this.name;
     }
    
     public void setName(String name) 
     {
    	 this.name = name;
     }

     @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="contentCategory")
     public Set<ContentMeta> getContentMetas() 
     {
    	 return this.contentMetas;
     }
    
     public void setContentMetas(Set<ContentMeta> contentMetas) 
     {
    	 this.contentMetas = contentMetas;
     }
}