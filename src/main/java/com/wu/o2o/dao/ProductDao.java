package com.wu.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wu.o2o.entity.Product;

public interface ProductDao {

	/**
	 * 查询商品列表并分页 模糊查询
	 * 
	 * @param productCondition
	 * @param pageSize
	 * @return
	 */
	List<Product> queryProductList(@Param("productCondition") Product productCondition,
			@Param(value = "rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	
	/**
	 * 查询商品数量
	 * @param productCondition
	 * @return
	 */
    int queryProductCount(@Param("productCondition") Product productCondition);
	/**
	 * 插入店铺信息
	 * 
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);

	/**
	 * 通过商品Id查询商品
	 * 
	 * @param productId
	 * @return
	 */
	Product queryProductByProductId(long productId);

	/**
	 * 修改商品信息
	 * 
	 * @param product
	 * @return
	 */
	int updateProduct(Product product);
	/**
	 * 删除商品之前将商品类别Id置为空
	 * @param productCategoryId
	 * @return
	 */
	
	int updateProductCategoryToNull(long productCategoryId);
	
	
	int deleteProduct(long productId);

}
