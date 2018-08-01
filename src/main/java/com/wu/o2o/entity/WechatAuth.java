package com.wu.o2o.entity;

import java.io.Serializable;
import java.util.Date;

public class WechatAuth implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long wechatAuth;
	private String openId;
	private Date createTime;
	private PersonInfo personINfo;
	public Long getWechatAuth() {
		return wechatAuth;
	}
	public void setWechatAuth(Long wechatAuth) {
		this.wechatAuth = wechatAuth;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public PersonInfo getPersonINfo() {
		return personINfo;
	}
	public void setPersonINfo(PersonInfo personINfo) {
		this.personINfo = personINfo;
	}
	

}
