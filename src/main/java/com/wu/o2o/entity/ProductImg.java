package com.wu.o2o.entity;

import java.io.Serializable;
import java.util.Date;

public class ProductImg implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long productImgId;
	private String imgAddr;
	private String imgDesc;
	private Integer priority;
	private Date createTime;
	private Long productId;
	public Long getProductImgId() {
		return productImgId;
	}
	public void setProductImgId(Long productImgId) {
		this.productImgId = productImgId;
	}
	public String getImgAddr() {
		return imgAddr;
	}
	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}
	public String getImgDesc() {
		return imgDesc;
	}
	public void setImgDesc(String imgDesc) {
		this.imgDesc = imgDesc;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getProducdId() {
		return productId;
	}
	public void setProducdId(Long producdId) {
		this.productId = producdId;
	}
	

}
