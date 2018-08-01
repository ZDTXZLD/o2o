package com.wu.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wu.o2o.BaseTest;
import com.wu.o2o.entity.Area;
import com.wu.o2o.entity.PersonInfo;
import com.wu.o2o.entity.Shop;
import com.wu.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest {
	
	@Autowired
	private ShopDao shopdao;
	@Test
	public void testQueryShopList() {
		Shop shopCondition=new Shop();
		ShopCategory child = new ShopCategory();
		ShopCategory parent = new ShopCategory();
		parent.setShopCategoryId(1L);
		child.setParent(parent);
		shopCondition.setShopCategory(child);
		System.out.println(shopCondition.getShopCategory().getShopCategoryId());
		List<Shop> list = shopdao.queryShopList(shopCondition, 0,9999);
		for (Shop shop : list) {
			System.out.println(shop);
		}
		//assertEquals(5, list.size());
	}
	
	@Test
	public void testQueryByShopId() {
		Shop shop = shopdao.queryByShopId(1L);
		System.out.println(shop);
		if(shop!=null) {
			
		System.out.println(shop.getArea().getAreaName());
		System.out.println(shop.getArea().getAreaId());
		System.out.println(shop.getShopCategory().getShopCategoryId());
		System.out.println(shop.getShopCategory().getShopCategoryName());
	}
	}
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop=new Shop();
		PersonInfo owner=new PersonInfo();
		Area area=new Area();
		ShopCategory shopCateGory=new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCateGory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCateGory);
		shop.setShopName("测试店铺");
		shop.setShopDesc("test");
		shop.setShopAddr("tset");
		shop.setPhone("test");
		shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		int effectedNums = shopdao.insertShop(shop);
		assertEquals(1, effectedNums);
		
		
	}
	@Test
	public void testupdateShop() {
		Shop shop=new Shop();
		shop.setShopId(1L);;
		shop.setShopDesc("测试描述");
		shop.setShopAddr("测试地址");
		shop.setPhone("test");
		shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		int effectedNums = shopdao.updateShop(shop);
		assertEquals(1, effectedNums);
		
		
	}
	

}
