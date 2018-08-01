package com.wu.o2o;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wu.o2o.entity.Product;
import com.wu.o2o.entity.ProductCategory;
import com.wu.o2o.entity.ShopCategory;

public class TestDemo {
	public static void main(String[] args) {
		
//		Product product = new Product();
//		product.setProductId(1l);
//		product.setProductName("1");
//		product.setProductDesc("1");
//		product.setNormalPrice("1");
//		product.setPromotionPrice("1");
//		product.setPriority(1);
//		ProductCategory productCategory = new ProductCategory();
//		productCategory.setProductCategroyId(10L);
//		product.setProductcategory(productCategory);
//		System.out.println(product);
		String s = new String();
		s+="{\"productName\":\"1\",\"productDesc\":\"1\",\"priority\":\"1\",\"normalPrice\":\"1\",\"promotionPrice\":\"1\",\"productCategory\":{\"productCategroyId\":7},\"productId\":\"\"}";
		ObjectMapper mapper = new ObjectMapper();
		try {
			Product product = mapper.readValue(s, Product.class);
			System.out.println(product);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
