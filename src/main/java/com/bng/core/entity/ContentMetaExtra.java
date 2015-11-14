/**
 * 
 */
package com.bng.core.entity;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * @author Harish Shan
 *
 */

@Entity
@Table(name="content_meta_extra")

public class ContentMetaExtra  implements java.io.Serializable 
{
     private Integer id;
     private ContentMeta contentMeta;
     private String options;
    
     public ContentMetaExtra() 
     {
     }

     public ContentMetaExtra(ContentMeta contentMeta, String options) 
     {
    	 this.contentMeta = contentMeta;
    	 this.options = options;
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

     @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="content_meta_id")
     public ContentMeta getContentMeta() 
     {
        return this.contentMeta;
     }
    
     public void setContentMeta(ContentMeta contentMeta) 
     {
        this.contentMeta = contentMeta;
     }
     
     @Column(name="options", length=65535)
     public String getOptions() 
     {
        return this.options;
     }
    
     public void setOptions(String options) 
     {
        this.options = options;
     }
}