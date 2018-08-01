package com.wu.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wu.o2o.BaseTest;
import com.wu.o2o.entity.ProductCategory;

public class ProductCategoryDaoTest extends BaseTest {
	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Test
	public void shopCategoryDaoTest() {
		long shopId = 1;
		List<ProductCategory> list = productCategoryDao.queryProductCategoryList(shopId);
		System.out.println("店铺类别" + list.size());
	}

	@Test
	public void batchInsertProductCategory() {
		ProductCategory pc1 = new ProductCategory();
		pc1.setProductCategoryName("商品类别1");
		pc1.setCreateTime(new Date());
		pc1.setPriority(1);
		pc1.setShopId(1L);
		ProductCategory pc2 = new ProductCategory();
		pc2.setProductCategoryName("商品类别3");
		pc2.setCreateTime(new Date());
		pc2.setPriority(2);
		pc2.setShopId(1L);
		ProductCategory pc3 = new ProductCategory();
		pc3.setProductCategoryName("商品类别2");
		pc3.setCreateTime(new Date());
		pc3.setPriority(3);
		pc3.setShopId(1L);
		ArrayList<ProductCategory> list = new ArrayList<ProductCategory>();
		list.add(pc1);
		list.add(pc2);
		list.add(pc3);
		int effectedNum = productCategoryDao.batchInsertProductCategory(list);
		assertEquals(3, effectedNum);
	}

	@Test
	public void deleteProductCategory() {
		long shopId = 1;
		List<ProductCategory> list = productCategoryDao.queryProductCategoryList(shopId);
		System.out.println(list.size());
		for (ProductCategory pc : list) {
			if ("商品类别1".equals(pc.getProductCategoryName()) || "商品类别2".equals(pc.getProductCategoryName())) {
				int effectedNums = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
				assertEquals(1, effectedNums);
				//System.out.println(pc.getProductCategoryName()+pc.getProductCategroyId());
			}

		}

	}
}
