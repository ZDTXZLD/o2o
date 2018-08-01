package com.wu.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wu.o2o.dao.ProductCategoryDao;
import com.wu.o2o.dao.ProductDao;
import com.wu.o2o.dao.productDaoTest;
import com.wu.o2o.dto.ProductCategoryExecution;
import com.wu.o2o.entity.ProductCategory;
import com.wu.o2o.enums.ProductCategoryStateEnum;
import com.wu.o2o.exceptions.ProductCategoryOperationException;
import com.wu.o2o.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Autowired
	private ProductDao productDao;

	@Override
	public List<ProductCategory> getProductCategoryList(long shopId) {

		return productCategoryDao.queryProductCategoryList(shopId);
	}

	@Override
	public ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException {
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if (effectNum <= 0)
					throw new ProductCategoryOperationException("店铺类别创建失败");
				else
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);

			} catch (Exception e) {
				throw new ProductCategoryOperationException("batchInsertProductCategory"+e.getMessage());
			}
		}else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
		
	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		//解除tb_product里面的商品与produtCategoryId里面内容的关联	
		try {
			int effected = productDao.updateProductCategoryToNull(productCategoryId);
			if(effected<0) {
				throw new RuntimeException("商品类别更新失败");
			}
			
		} catch (Exception e) {
			throw new RuntimeException("deleteproductCategoryId error"+e.getMessage());
		}
		
		//删除改productCategory
		try {
			int effectedNum=productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if(effectedNum<=0)
				throw new ProductCategoryOperationException("商品类别删除失败");
			else
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
		} catch (Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory"+e.getMessage());
		}
	}

}
