package com.wu.o2o.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wu.o2o.BaseTest;
import com.wu.o2o.entity.HeadLine;

public class HeadLineDaoTest extends BaseTest {
	@Autowired
	private HeadLineDao headLineDao;
    @Test
    public void testQueryHeadLine() {
    	
    	List<HeadLine> list = headLineDao.queryHeadLine(new HeadLine());
    	System.out.println(list.size());
    }
}
