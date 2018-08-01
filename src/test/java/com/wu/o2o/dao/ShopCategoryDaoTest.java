package com.wu.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wu.o2o.BaseTest;
import com.wu.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest {
  
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	@Test
	public void testQueryShopCategory() {
		
		List<ShopCategory> shopCategory = shopCategoryDao.queryShopCategory(null);
		System.out.println(shopCategory.size());
//		assertEquals(1, shopCategory.size());
//		ShopCategory parent=new ShopCategory();
//		ShopCategory testShopCategory=new ShopCategory();
//		parent.setShopCategoryId(1L);
//		testShopCategory.setParent(parent);
//		List<ShopCategory> list = shopCategoryDao.queryShopCategory(testShopCategory);
//		assertEquals(1, list.size());
//		System.out.println(list.get(0));
	}
	
}
