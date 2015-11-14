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
 * Userss generated by hbm2java
 */
@Entity
@Table(name="userss")
public class Userss  implements java.io.Serializable {


     private Integer id;
     private String password;
     private Integer status;
     private String username;
     private Set<UserRoless> userRolesses = new HashSet<UserRoless>(0);

    public Userss() {
    }

    public Userss(String password, Integer status, String username, Set<UserRoless> userRolesses) {
       this.password = password;
       this.status = status;
       this.username = username;
       this.userRolesses = userRolesses;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="password")
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name="status")
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    @Column(name="username")
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="userss")
    public Set<UserRoless> getUserRolesses() {
        return this.userRolesses;
    }
    
    public void setUserRolesses(Set<UserRoless> userRolesses) {
        this.userRolesses = userRolesses;
    }




}

