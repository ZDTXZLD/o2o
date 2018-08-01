package com.wu.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wu.o2o.BaseTest;
import com.wu.o2o.dto.ImageHolder;
import com.wu.o2o.dto.ShopExecution;
import com.wu.o2o.entity.Area;
import com.wu.o2o.entity.PersonInfo;
import com.wu.o2o.entity.Shop;
import com.wu.o2o.entity.ShopCategory;
import com.wu.o2o.enums.ShopStateEnum;
import com.wu.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;
	@SuppressWarnings("resource")
	
	@Test
	public void  testGetShopList() {
		Shop shopCondition=new Shop();
		PersonInfo owner=new PersonInfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(3L);
		shopCondition.setShopCategory(shopCategory);
	    ShopExecution se = shopService.getShopList(shopCondition, 0, 2);
		assertEquals(1, se.getShoplist().size());
	}
	
	@Test
	public void modifyShop() throws ShopOperationException, FileNotFoundException {
		Shop shop=new Shop();
		shop.setShopId(24L);
		shop.setShopName("新的店铺名称");
		File shopImg=new File("D:/projectdev/image/ab.jpg");
		FileInputStream is = new FileInputStream(shopImg);
		ImageHolder thumbnail = new ImageHolder(is, "*.jpg");
		ShopExecution execution = shopService.modifyShop(shop, thumbnail);
		System.out.println("新的图片地址为"+execution.getShop().getShopImg());
	}
	@Test
	public void testAddShop() throws FileNotFoundException {
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
		shop.setShopName("测试店铺3");
		shop.setShopDesc("test3");
		shop.setShopAddr("tset3");
		shop.setPhone("test3");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHAKE.getState());
		shop.setAdvice("审核中");
		File shopImg=new File("C:/Users/tbq/Pictures/Saved Pictures/1.jpg");
		InputStream is=new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder(is,shopImg.getName());
		ShopExecution addShop = shopService.addShop(shop, imageHolder);
		assertEquals(ShopStateEnum.CHAKE.getState(), addShop.getState());
	}
}
