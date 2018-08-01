package com.wu.o2o.service.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;

import com.wu.o2o.dao.ProductDao;
import com.wu.o2o.dao.ProductImgDao;
import com.wu.o2o.dto.ImageHolder;
import com.wu.o2o.dto.ProductExecution;
import com.wu.o2o.entity.Product;
import com.wu.o2o.entity.ProductImg;
import com.wu.o2o.enums.ProductStateEnum;
import com.wu.o2o.exceptions.ProductOperationException;
import com.wu.o2o.service.ProductService;
import com.wu.o2o.util.ImageUtil;
import com.wu.o2o.util.PageCalculator;
import com.wu.o2o.util.PathUtil;



@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductImgDao productImgDao;
    Logger log=LoggerFactory.getLogger(ProductServiceImpl.class);
	@Transactional
	@Override
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
			throws ProductOperationException {
		// 处理缩略图,获取缩略图相对路径并赋值给product
		// 往tb_product写入商品信息并获取期productId
		// 根据productId批量处理商品详情图
		// 将商品详情图批量插入tb_product_img中

		// 空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 给商品设置默认信息
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			// 默认为上架的状态
			product.setEnableStatus(1);
			// 如商品缩略图不为空则添加
			if (thumbnail != null) {
				addTumbnail(product, thumbnail);
			}

			try {
				// 创建商品信息
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("商品创建失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("商品创建失败" + e.getMessage());
			}
			// 若商品详情图不为空则添加
			if (productImgHolderList != null && productImgHolderList.size() > 0) {
				addProductImgList(product, productImgHolderList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	/**
	 * 添加缩略图
	 * 
	 * @param product
	 * @param thumbnail
	 */
	private void addTumbnail(Product product, ImageHolder thumbnail) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumbnailAddr);

	}

	/**
	 * 批量添加商品详情图
	 * 
	 * @param product
	 * @param productImgHolderList
	 */
	private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> list = new ArrayList<ProductImg>();
		for (ImageHolder imageHolder : productImgHolderList) {
			String imgAddr = ImageUtil.generateNormalImg(imageHolder, dest);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProducdId(product.getProductId());
			productImg.setCreateTime(new Date());
			list.add(productImg);
		}

		// 如果确实有图片进行添加
		if (list.size() > 0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(list);
				if (effectedNum <= 0) {
					throw new ProductOperationException("创建商品详情图片失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品详情图片失败" + e.toString());
			}
		}
	}

	@Override
	public Product getProductById(long productId) {

		return productDao.queryProductByProductId(productId);
	}

	@Override
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperationException {
		// 如缩略图有值则先处理缩略图---若原先存在缩略图则先添加新图,之后获取缩略图现对路径并赋值给product
		// 若商品详情图参数列表有值,对商品详情图列表进行同样的操作
		// 将tb_product_img下面的该商品原先的商品详情图记录全部清除
		// 更新tb_product的信息	
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			log.debug("进入方法");
			// 给商品设置默认信息
			product.setLastEditTime(new Date());
			// 如商品缩略图不为空则添加
			if (thumbnail != null) {
				Product tempProduct = productDao.queryProductByProductId(product.getProductId());
				//判断原有缩略图是否为空,不为空则删除
				if (tempProduct.getImgAddr() != null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr()	);
				}
				addTumbnail(product, thumbnail);
			}
			//如果有新传入的详情图,则对有的详情图做清除在进行添加
            if(productImgList!=null&&productImgList.size()>0) {
            	deleteProductImgList(product.getProductId());
            	addProductImgList(product, productImgList);
            }
			try {
				// 创建商品信息
				int effectedNum = productDao.updateProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("商品更新失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS,product);
			} catch (Exception e) {
				throw new ProductOperationException("商品更新失败" + e.toString());
			}
		
		} else {
			log.debug(""+product);
			log.debug(""+product.getShop());
			log.debug(""+product.getShop().getShopId());
			log.debug("参数为空");
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
		
	}

	private void deleteProductImgList(Long productId) {
		// 根据peoduct获取原来的图片
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		//删除原来的图片
		for (ProductImg productImg : productImgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		//删除数据库原有的数据信息
		productImgDao.deleteProductImgByProductId(productId);
		
	}

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		// 页码转换成数据库的行码,并调用dao层取回指定的页码商品列表
		int rowIndex = PageCalculator.calculatorRowsIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		int count = productDao.queryProductCount(productCondition);
		ProductExecution pe = new ProductExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}

}
