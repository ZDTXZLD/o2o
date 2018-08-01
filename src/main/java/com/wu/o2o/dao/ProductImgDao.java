package com.wu.o2o.dao;

import java.util.List;

import com.wu.o2o.entity.ProductImg;

public interface ProductImgDao {
	/**
	 * 查询商品详情图列表
	 * @param productId
	 * @return
	 */
	List<ProductImg> queryProductImgList(long productId);
	/**
	 * 批量添加商品详情图片
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	/**
	 * 删除指定商品详情图下的所有图
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);
}
