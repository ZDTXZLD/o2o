package com.wu.o2o.service;

import java.util.List;

import com.wu.o2o.entity.ShopCategory;

public interface ShopCategoryService {
	/**
	 * 返回店铺类别列表
	 * @return
	 */
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
