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
@Table(name="content_mapper")//TODO
public class ContentMapper  implements java.io.Serializable 
{
     private Integer id;
     private Content content;
     private int users;//TODO
     private String downloadUrl;
     private String crbtUrl;
     private String billingUrl;

     public ContentMapper() 
     {
    	 
     }

     public ContentMapper(Content content,int users, String downloadUrl, String crbtUrl, String billingUrl) 
     {
    	this.content = content;
       	this.users=users;
       	this.downloadUrl = downloadUrl;
       	this.crbtUrl = crbtUrl;
       	this.billingUrl = billingUrl;
     }
   
     @Id @GeneratedValue(strategy=IDENTITY)
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
     @JoinColumn(name="content_id")
     public Content getContent() 
     {
    	 return this.content;
     }
    
     public void setContent(Content content) 
     {
    	 this.content = content;
     }
    
     @Column(name="user_mapping_id")
     public int getUsers() 
     {
    	 return this.users;
     }
    
     public void setUsers(int users) 
     {
    	 this.users = users;
     }
    
     @Column(name="download_url", length=2000)
     public String getDownloadUrl() 
     {
    	 return this.downloadUrl;
     }
    
     public void setDownloadUrl(String downloadUrl) 
     {
    	 this.downloadUrl = downloadUrl;
     }
    
     @Column(name="crbt_url", length=2000)
     public String getCrbtUrl() 
     {
    	 return this.crbtUrl;
     }
    
     public void setCrbtUrl(String crbtUrl) 
     {	
    	 this.crbtUrl = crbtUrl;
     }
    
     @Column(name="billing_url", length=2000)
     public String getBillingUrl() 
     {
    	 return this.billingUrl;
     }
    
     public void setBillingUrl(String billingUrl) 
     {
    	 this.billingUrl = billingUrl;
     }
}