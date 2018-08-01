package com.wu.o2o.entity;

import java.io.Serializable;
import java.util.Date;

public class PersonInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long userId;
	private String name;
	private String profileImg;
	private String email;
	private String enableStatus;
    private String gender;
    //1顾客  2店家 3超级管理员
    private Integer userType;
    private Date createTime;
    private Date lastEditTime;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
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
    

}
