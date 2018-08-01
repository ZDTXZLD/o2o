package com.wu.o2o.entity;

import java.io.Serializable;
import java.util.Date;

public class HeadLine implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long lineId;
	private String lineName;
	private String linelink;
	private String lineImg;
	private Integer priority;
	//0 不可用 1 可用
	private Integer enableStatus;
	private Date createTime;
	private Date lastEditTime;
	
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public Long getLineId() {
		return lineId;
	}
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getLinelink() {
		return linelink;
	}
	public void setLinelink(String linelink) {
		this.linelink = linelink;
	}
	public String getLineImg() {
		return lineImg;
	}
	public void setLineImg(String lineImg) {
		this.lineImg = lineImg;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
}
