package com.wu.o2o.service;

import java.util.List;

import com.wu.o2o.dto.ProductCategoryExecution;
import com.wu.o2o.entity.ProductCategory;
import com.wu.o2o.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {

	/**
	 * 查询指定某个店铺下的所有商品类别信息
	 * 
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> getProductCategoryList(long shopId);

	/**
	 * 批量插入商品类别信息
	 * 
	 * @param productCategoryList
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException;

	
	/**
	 * 将此类别下的商品列表的Id设置为空,再删除商品列表信息
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException;

}
