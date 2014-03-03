package com.elivoa.aliprint.common.dal;

import java.util.Date;

/**
 * @author royzuo
 *
 */
public class QueryTimeParameter {

	private Date startTime;
	
	private Date endTime;
	
	public QueryTimeParameter(Date startTime, Date endTime){
		this.setStartTime(startTime);
		this.setEndTime(endTime);
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public java.sql.Date getStartTime(java.sql.Date t){
		t = new java.sql.Date(startTime.getTime());
		return t;
	}
	
	public java.sql.Date getEndTime(java.sql.Date t){
		t = new java.sql.Date(endTime.getTime());
		return t;
	}
	
	public java.sql.Timestamp getStartTime(java.sql.Timestamp t){
		t = new java.sql.Timestamp(startTime.getTime());
		return t;
	}
	
	public java.sql.Timestamp getEndTime(java.sql.Timestamp t){
		t = new java.sql.Timestamp(endTime.getTime());
		return t;
	}
	
}
