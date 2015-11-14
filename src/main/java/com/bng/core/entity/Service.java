package com.bng.core.entity;

// Generated 5 Mar, 2014 2:37:46 PM by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Service generated by hbm2java
 */
@Entity
@Table(name = "service", uniqueConstraints = @UniqueConstraint(columnNames = "jobname"))
public class Service implements java.io.Serializable {

	private Integer id;
	private Mxgraph mxgraph;
	private ObdBlackoutHours obdBlackoutHours;
	private String jobname;
	private Date startDate;
	private Date endDate;
	private Date startTime;
	private Date endTime;
	private int priority;
	private String status;
	private int maxRetry;
	private int remainingRetry;
	private boolean starcopy;
	private boolean recorddedication;
	private List<ObdCli> obdClis = new ArrayList<ObdCli>(0);

	public Service() {
	}

	public Service(Mxgraph mxgraph, ObdBlackoutHours obdBlackoutHours,
			String jobname, Date startDate, Date endDate, Date startTime,
			Date endTime, int priority, String status, int maxRetry,
			int remainingRetry, boolean starcopy, boolean recorddedication) {
		this.mxgraph = mxgraph;
		this.obdBlackoutHours = obdBlackoutHours;
		this.jobname = jobname;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.priority = priority;
		this.status = status;
		this.maxRetry = maxRetry;
		this.remainingRetry = remainingRetry;
		this.starcopy = starcopy;
		this.recorddedication = recorddedication;
	}

	public Service(Mxgraph mxgraph, ObdBlackoutHours obdBlackoutHours,
			String jobname, Date startDate, Date endDate, Date startTime,
			Date endTime, int priority, String status, int maxRetry,
			int remainingRetry, boolean starcopy, boolean recorddedication,
			List<ObdCli> obdClis) {
		this.mxgraph = mxgraph;
		this.obdBlackoutHours = obdBlackoutHours;
		this.jobname = jobname;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.priority = priority;
		this.status = status;
		this.maxRetry = maxRetry;
		this.remainingRetry = remainingRetry;
		this.starcopy = starcopy;
		this.recorddedication = recorddedication;
		this.obdClis = obdClis;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_id", nullable = false)
	public Mxgraph getMxgraph() {
		return this.mxgraph;
	}

	public void setMxgraph(Mxgraph mxgraph) {
		this.mxgraph = mxgraph;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "blackout_hours", nullable = false)
	public ObdBlackoutHours getObdBlackoutHours() {
		return this.obdBlackoutHours;
	}

	public void setObdBlackoutHours(ObdBlackoutHours obdBlackoutHours) {
		this.obdBlackoutHours = obdBlackoutHours;
	}

	@Column(name = "jobname", unique = true, nullable = false, length = 25)
	public String getJobname() {
		return this.jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "start_date", nullable = false, length = 10)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "end_date", nullable = false, length = 10)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "start_time", nullable = false, length = 8)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "end_time", nullable = false, length = 8)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "priority", nullable = false)
	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Column(name = "status", nullable = false, length = 15)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "max_retry", nullable = false)
	public int getMaxRetry() {
		return this.maxRetry;
	}

	public void setMaxRetry(int maxRetry) {
		this.maxRetry = maxRetry;
	}

	@Column(name = "remaining_retry", nullable = false)
	public int getRemainingRetry() {
		return this.remainingRetry;
	}

	public void setRemainingRetry(int remainingRetry) {
		this.remainingRetry = remainingRetry;
	}

	@Column(name = "starcopy", nullable = false)
	public boolean isStarcopy() {
		return this.starcopy;
	}

	public void setStarcopy(boolean starcopy) {
		this.starcopy = starcopy;
	}

	@Column(name = "recorddedication", nullable = false)
	public boolean isRecorddedication() {
		return this.recorddedication;
	}

	public void setRecorddedication(boolean recorddedication) {
		this.recorddedication = recorddedication;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "service")
	public List<ObdCli> getObdClis() {
		return this.obdClis;
	}

	public void setObdClis(List<ObdCli> obdClis) {
		this.obdClis = obdClis;
	}

}