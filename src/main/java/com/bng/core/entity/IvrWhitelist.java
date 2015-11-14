package com.bng.core.entity;
// default package
// Generated Jan 13, 2014 4:52:59 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * IvrWhitelist generated by hbm2java
 */
@Entity
@Table(name = "ivr_whitelist")
public class IvrWhitelist implements java.io.Serializable {

	private Integer id;
	private Date date;
	private Integer isSeries;
	private String msisdn;

	public IvrWhitelist() {
	}

	public IvrWhitelist(Date date, Integer isSeries, String msisdn) {
		this.date = date;
		this.isSeries = isSeries;
		this.msisdn = msisdn;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE", length = 19)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "IS_SERIES")
	public Integer getIsSeries() {
		return this.isSeries;
	}

	public void setIsSeries(Integer isSeries) {
		this.isSeries = isSeries;
	}

	@Column(name = "MSISDN", length = 25)
	public String getMsisdn() {
		return this.msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

}
