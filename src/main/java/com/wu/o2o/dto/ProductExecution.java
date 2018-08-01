package com.wu.o2o.dto;

import java.util.List;

import com.wu.o2o.entity.Product;
import com.wu.o2o.enums.ProductStateEnum;

public class ProductExecution {

	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 店铺数量
	private int count;

	// 操作的shop(增删改查店铺的使用)
	private Product product;

	// shop列表
	private List<Product> productList;

	public ProductExecution() {
		super();
	}
   //操作失败构造器
	public ProductExecution(ProductStateEnum productStateEnum) {
		super();
		this.state = productStateEnum.getState();
		this.stateInfo = productStateEnum.getStateInfo();
	}
   //成功构造器
	public ProductExecution(ProductStateEnum productStateEnum, Product product) {
		super();
		this.state = productStateEnum.getState();
		this.stateInfo = productStateEnum.getStateInfo();
		this.product = product;
	}
    //成功构造器
	public ProductExecution(ProductStateEnum productStateEnum, List<Product> productList) {
		super();
		this.state = productStateEnum.getState();
		this.stateInfo = productStateEnum.getStateInfo();
		this.productList = productList;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	
	
	
	

	
}
