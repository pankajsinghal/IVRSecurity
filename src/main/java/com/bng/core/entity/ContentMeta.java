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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Harish Shan
 *
 */

@Entity
@Table(name="content_meta")
public class ContentMeta  implements java.io.Serializable 
{
  private Integer id;
     private Content content;
     private ContentProvider contentProvider;
     private ContentLabel contentLabel;
     private ContentCategory contentCategory;
     private String language;
     private String songcode;
     private String songname;
     private Set<ContentMetaExtra> contentMetaExtras = new HashSet();

     public ContentMeta() 
     {
     }

     public ContentMeta(Content content, ContentProvider contentProvider, ContentLabel contentLabel, ContentCategory contentCategory, String language, String songcode, String songname, Set<ContentMetaExtra> contentMetaExtras) 
     {
       this.content = content;
       this.contentProvider = contentProvider;
       this.contentLabel = contentLabel;
       this.contentCategory = contentCategory;
       this.language = language;
       this.songcode = songcode;
       this.songname = songname;
       this.contentMetaExtras = contentMetaExtras;
     }
 
     public ContentMeta(Content content, ContentProvider contentProvider, ContentLabel contentLabel, ContentCategory contentCategory, String language, String songcode, String songname) 
     {
       this.content = content;
       this.contentProvider = contentProvider;
       this.contentLabel = contentLabel;
       this.contentCategory = contentCategory;
       this.language = language;
       this.songcode = songcode;
       this.songname = songname;      
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
     @JoinColumn(name="content_id")
     public Content getContent() 
     {
        return this.content;
     }
    
     public void setContent(Content content) 
     {
        this.content = content;
     }
     
     @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="content_provider_id")
     public ContentProvider getContentProvider() 
     {
        return this.contentProvider;
     }
    
     public void setContentProvider(ContentProvider contentProvider) 
     {
        this.contentProvider = contentProvider;
     }

     @ManyToOne(fetch=FetchType.EAGER)
     @JoinColumn(name="content_label_id")
     public ContentLabel getContentLabel() 
     {
        return this.contentLabel;
     }
    
     public void setContentLabel(ContentLabel contentLabel) 
     {
        this.contentLabel = contentLabel;
     }

     @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="category_id")
     public ContentCategory getContentCategory() 
     {
        return this.contentCategory;
     }
    
     public void setContentCategory(ContentCategory contentCategory) 
     {
        this.contentCategory = contentCategory;
     }
    
     @Column(name="language")
     public String getLanguage() 
     {
        return this.language;
     }
    
     public void setLanguage(String language) 
     {
        this.language = language;
     }
    
     @Column(name="songcode")
     public String getSongcode() 
     {
        return this.songcode;
     }
     
     public void setSongcode(String songcode) 
     {
        this.songcode = songcode;
     }
    
     @Column(name="songname")
     public String getSongname() 
     {
        return this.songname;
     }
    
     public void setSongname(String songname) 
     {
      this.songname = songname;
     }

     @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="contentMeta")
     public Set<ContentMetaExtra> getContentMetaExtras() 
     {
        return this.contentMetaExtras;
     }    
    
     public void setContentMetaExtras(Set<ContentMetaExtra> contentMetaExtras) 
     {
      this.contentMetaExtras = contentMetaExtras;
     }
}