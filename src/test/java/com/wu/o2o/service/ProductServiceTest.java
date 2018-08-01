package com.wu.o2o.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mysql.jdbc.Field;
import com.wu.o2o.BaseTest;
import com.wu.o2o.dao.ProductDao;
import com.wu.o2o.dto.ImageHolder;
import com.wu.o2o.dto.ProductExecution;
import com.wu.o2o.entity.Product;
import com.wu.o2o.entity.ProductCategory;
import com.wu.o2o.entity.Shop;
import com.wu.o2o.enums.ProductStateEnum;

public class ProductServiceTest extends BaseTest {

	@Autowired
	private ProductService productService;

	@SuppressWarnings("resource")
	@Test
	public void testAddProduct() throws FileNotFoundException {
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("测试商品1");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		File file = new File("E:/image/1.jpg");
		FileInputStream is = new FileInputStream(file);
		ImageHolder imageHolder = new ImageHolder(is, file.getName());
		File file1 = new File("E:/image/2.jpg");
		FileInputStream is1 = new FileInputStream(file1);
		
		File file2 = new File("E:/image/3.jpg");
		FileInputStream is2 = new FileInputStream(file2);
		ArrayList<ImageHolder> list = new ArrayList<ImageHolder>();
		list.add(new ImageHolder(is1, file1.getName()));
		list.add(new ImageHolder(is2, file2.getName()) );
		ProductExecution addProduct = productService.addProduct(product, imageHolder, list);
		System.out.println(addProduct.getState());
		
	}
	@Test
	public void testModifyProduct() throws FileNotFoundException {
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setProductId(1L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("正式商品");
		product.setProductDesc("正式商品");
		File file = new File("E:/image/1.jpg");
		FileInputStream is = new FileInputStream(file);
		ImageHolder imageHolder = new ImageHolder(is, file.getName());
		File file1 = new File("E:/image/2.jpg");
		FileInputStream is1 = new FileInputStream(file1);
		
		File file2 = new File("E:/image/3.jpg");
		FileInputStream is2 = new FileInputStream(file2);
		ArrayList<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(is1, file1.getName()));
		productImgList.add(new ImageHolder(is2, file2.getName()) );
		ProductExecution pe = productService.modifyProduct(product, imageHolder, productImgList);
		System.out.println(pe.getState());
		
	}

}
