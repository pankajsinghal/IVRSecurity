package com.bng.core.entity;
// Generated Sep 30, 2013 11:56:12 AM by Hibernate Tools 3.2.1.GA


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
 * UserMappingTable generated by hbm2java
 */
@Entity
@Table(name="user_mapping_table"
)
public class UserMapping  implements java.io.Serializable {


     private Integer id;
     private Users users;
     private Operator operator;
     private Country country;

    public UserMapping() {
    }

    public UserMapping(Users users, Operator operator, Country country) {
       this.users = users;
       this.operator = operator;
       this.country = country;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="operator_id")
    public Operator getOperator() {
        return this.operator;
    }
    
    public void setOperator(Operator operator) {
        this.operator = operator;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="country_id")
    public Country getCountry() {
        return this.country;
    }
    
    public void setCountry(Country country) {
        this.country = country;
    }




}


