package com.wu.o2o.util;

public class PageCalculator {
	
	public static int calculatorRowsIndex(int pageIndex,int pageSize) {
		return (pageIndex>0)?(pageIndex-1)*pageSize:0;
	}

}
