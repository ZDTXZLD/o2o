package com.wu.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.javassist.bytecode.LineNumberAttribute.Pc;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.wu.o2o.BaseTest;
import com.wu.o2o.entity.Product;
import com.wu.o2o.entity.ProductCategory;
import com.wu.o2o.entity.ProductImg;
import com.wu.o2o.entity.Shop;
import com.wu.o2o.entity.ShopCategory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class productDaoTest extends BaseTest {
	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductImgDao productImgDao;

	@Test
	public void testAInsertProduct() throws Exception {
		Shop shop1 = new Shop();
		shop1.setShopId(1L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		Product product = new Product();
		product.setProductName("测试1");
		product.setProductDesc("测试desc1");
		product.setImgAddr("test");
		product.setCreateTime(new Date());
		product.setPriority(1);
		product.setEnableStatus(1);
		product.setLastEditTime(new Date());
		product.setShop(shop1);
		product.setProductCategory(pc);
		
		Product product2 = new Product();
		product2.setProductName("测试1");
		product2.setProductDesc("测试desc1");
		product2.setImgAddr("test");
		product2.setCreateTime(new Date());
		product2.setPriority(1);
		product2.setEnableStatus(1);
		product2.setLastEditTime(new Date());
		product2.setShop(shop1);
		product2.setProductCategory(pc);
		
		Product product3 = new Product();
		product3.setProductName("测试1");
		product3.setProductDesc("测试desc1");
		product3.setImgAddr("test");
		product3.setCreateTime(new Date());
		product3.setPriority(1);
		product3.setEnableStatus(1);
		product3.setLastEditTime(new Date());
		product3.setShop(shop1);
		product3.setProductCategory(pc);
		int i = productDao.insertProduct(product3);
		int i1 = productDao.insertProduct(product2);
		int i2 = productDao.insertProduct(product);
		System.out.println(1+i1+i2);

	}
	@Test
	public void testQueryProductList() {
		Product product = new Product();
		List<Product> list = productDao.queryProductList(product, 0, 3);
		System.out.println(list.size());
		int effected = productDao.queryProductCount(product);
		System.out.println(effected);
		product.setProductName("测试");
		int count = productDao.queryProductCount(product);
		System.out.println(count);
		List<Product> list2 = productDao.queryProductList(product, 0, 3);
		System.out.println(list2.size());
	}
	@Test
	public void testBUpdateProduct()throws Exception{
		Product product = new Product();
		ProductCategory productCategory = new ProductCategory();
			Shop shop = new Shop();
			shop.setShopId(1L);
			productCategory.setProductCategoryId(1L);
			product.setProductId(1L);
			product.setShop(shop);
			product.setProductName("第I个产品");
			product.setProductCategory(productCategory);
			int effected = productDao.updateProduct(product);
			System.out.println(effected);
	}
	@Test
	public void  testCQueryProductByProductId()throws Exception{
		long productId=1;
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("图片1");
		productImg1.setImgAddr("测试图片1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProducdId(productId);
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("图片2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProducdId(productId);
		ArrayList<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2, effectedNum);
		Product product = productDao.queryProductByProductId(productId);
		assertEquals(2, product.getProductImgList().size());
		effectedNum = productImgDao.deleteProductImgByProductId(productId);
		assertEquals(2, effectedNum);
	}
	@Test
	public void testUpdateProductCategoryToNull() {
		int effected = productDao.updateProductCategoryToNull(10L);
		System.out.println(effected);
	}

}
