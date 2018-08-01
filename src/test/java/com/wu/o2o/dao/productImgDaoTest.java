package com.wu.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.wu.o2o.BaseTest;
import com.wu.o2o.entity.ProductImg;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class productImgDaoTest extends BaseTest {
	@Autowired
	private ProductImgDao productImgDao;

	@Test
	public void testAbatchInsertProductImg() {

		ProductImg p1 = new ProductImg();
		p1.setImgAddr("图片1");
		p1.setImgDesc("测试图");
		p1.setCreateTime(new Date());
		p1.setPriority(1);
		p1.setProducdId(1L);
		ProductImg p2 = new ProductImg();
		p2.setImgAddr("图片2");
		p2.setImgDesc("测试图片");
		p2.setCreateTime(new Date());
		p2.setPriority(1);
		p2.setProducdId(1L);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(p1);
		productImgList.add(p2);
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2, effectedNum);;

	}
	@Test
	public void testCdeleteProductImgByProductId()throws Exception{
		//删除新增的两条商品详情图片纪录
		long productId=1;
		int effectedNnum = productImgDao.deleteProductImgByProductId(productId);
		assertEquals(2, effectedNnum);
	}
}
