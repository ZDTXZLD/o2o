package com.wu.o2o.service;

import java.util.List;

import com.wu.o2o.dto.ImageHolder;
import com.wu.o2o.dto.ProductExecution;
import com.wu.o2o.entity.Product;
import com.wu.o2o.exceptions.ProductOperationException;

public interface ProductService {
	
	/**
	 * 查询商品列表并分页,可输入的条件有:商品名(模糊) ,商品状态, 店铺ID,商品类别
	 * @param productCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ProductExecution getProductList(Product productCondition,int pageIndex,int pageSize);
	/**
	 * 增加商品信息
	 * @param product  商品信息
	 * @param thumbnail 缩略图
	 * @param productImgList 详情图
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperationException;
	
	/**
	 * 通过商品Id获取商品信息	
	 * @param productId
	 * @return
	 */
	Product getProductById(long productId);
	
	/**
	 * 修改商品信息以及图片处理
	 * @param product
	 * @param thumbnail
	 * @param productImgList
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperationException;

}
