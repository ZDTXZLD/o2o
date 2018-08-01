package com.wu.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wu.o2o.BaseTest;
import com.wu.o2o.entity.Area;

public class AreaServiceTest extends BaseTest {

	@Autowired
	private AreaService areaService;
	
	@Test
	public void  getAreaService() {
		List<Area> list = areaService.getAreaList();
		assertEquals("西苑", list.get(0).getAreaName());
	}
	  
}
