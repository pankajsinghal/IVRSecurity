package com.bng.core.bean;
import java.io.Serializable;
import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
/**
 * @author Anjali Sarkar
 *
 */
public class JobBean implements Serializable {
	
	private int id;
	private String jobName;
	private String jobType;
	private String serviceName;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private boolean dndcheck;
	private int dndOption;
	private String cliNumber;
	private MultipartFile msisdn;
	private int priority;
	private boolean retrycheck;
	private String retryreason;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCliNumber() {
		return cliNumber;
	}
	public void setCliNumber(String cliNumber) {
		this.cliNumber = cliNumber;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public MultipartFile getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(CommonsMultipartFile commonsMultipartFile) {
		this.msisdn = commonsMultipartFile;
	}
	public int getDndOption() {
		return dndOption;
	}
	public void setDndOption(int dndOption) {
		this.dndOption = dndOption;
	}
	public boolean isDndcheck() {
		return dndcheck;
	}
	public void setDndcheck(boolean dndcheck) {
		this.dndcheck = dndcheck;
	}
	public boolean isRetrycheck() {
		return retrycheck;
	}
	public void setRetrycheck(boolean retrycheck) {
		this.retrycheck = retrycheck;
	}
	public String getRetryreason() {
		return retryreason;
	}
	public void setRetryreason(String retryreason) {
		this.retryreason = retryreason;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
}
