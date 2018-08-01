package com.wu.o2o.dto;

import java.util.List;

import com.wu.o2o.entity.Shop;
import com.wu.o2o.enums.ShopStateEnum;

public class ShopExecution {

	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 店铺数量
	private int count;

	// 操作的shop(增删改查店铺的使用)
	private Shop shop;

	// shop列表
	private List<Shop> shoplist;

	public ShopExecution() {

	}

	// 店铺操作失败时使用失败的构造器
	public ShopExecution(ShopStateEnum sateenum) {
		this.state = sateenum.getState();
		this.stateInfo = sateenum.getStateInfo();
	}

	// 店铺操作成功时使用成功的构造器
	public ShopExecution(ShopStateEnum stateenum, Shop shop) {
		this.state = stateenum.getState();
		this.stateInfo = stateenum.getStateInfo();
		this.shop = shop;
	}

	// 店铺操作成功时使用成功的构造器
	public ShopExecution(ShopStateEnum stateenum, List<Shop> shoplist) {
		this.state = stateenum.getState();
		this.stateInfo = stateenum.getStateInfo();
		this.shoplist = shoplist;
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

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShoplist() {
		return shoplist;
	}

	public void setShoplist(List<Shop> shoplist) {
		this.shoplist = shoplist;
	}
	

}
