package com.bng.core.entity;
// Generated Sep 30, 2013 11:56:12 AM by Hibernate Tools 3.2.1.GA


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
 * Country generated by hbm2java
 */
@Entity
@Table(name="country")
public class Country  implements java.io.Serializable 
{
     private Integer id;
     private String countryName;
     private String dbname;
     private Set<UserMapping> userMapping = new HashSet<UserMapping>(0);

    public Country() 
    {
    }

    public Country(String countryName, String dbname, Set<UserMapping> userMapping) 
    {
       this.countryName = countryName;
       this.dbname = dbname;
       this.userMapping = userMapping;
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
    
    @Column(name="country_name")
    public String getCountryName() 
    {
        return this.countryName;
    }
    
    public void setCountryName(String countryName) 
    {
        this.countryName = countryName;
    }
    
    @Column(name="dbname")
    public String getDbname() {
        return this.dbname;
    }
    
    public void setDbname(String dbname) {
        this.dbname = dbname;
    }
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="country")
    public Set<UserMapping> getUserMapping() 
    {
        return this.userMapping;
    }
    
    public void setUserMapping(Set<UserMapping> userMapping) 
    {
        this.userMapping = userMapping;
    }
}

