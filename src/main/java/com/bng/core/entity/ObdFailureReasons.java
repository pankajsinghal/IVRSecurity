package com.bng.core.entity;

// Generated 19 Dec, 2013 11:39:50 AM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * ObdFailureReasons generated by hbm2java
 */
@Entity
@Table(name = "obd_failure_reasons", uniqueConstraints = @UniqueConstraint(columnNames = {
		"reason_value", "ce_reason" }))
public class ObdFailureReasons implements java.io.Serializable {

	private int reasonId;
	private String reasonValue;
	private String ceReason;

	public ObdFailureReasons() {
	}

	public ObdFailureReasons(int reasonId, String reasonValue, String ceReason) {
		this.reasonId = reasonId;
		this.reasonValue = reasonValue;
		this.ceReason = ceReason;
	}

	@Id
	@Column(name = "reason_id", unique = true, nullable = false)
	public int getReasonId() {
		return this.reasonId;
	}

	public void setReasonId(int reasonId) {
		this.reasonId = reasonId;
	}

	@Column(name = "reason_value", nullable = false, length = 100)
	public String getReasonValue() {
		return this.reasonValue;
	}

	public void setReasonValue(String reasonValue) {
		this.reasonValue = reasonValue;
	}

	@Column(name = "ce_reason", nullable = false, length = 100)
	public String getCeReason() {
		return this.ceReason;
	}

	public void setCeReason(String ceReason) {
		this.ceReason = ceReason;
	}

}
