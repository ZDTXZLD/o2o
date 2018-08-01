package com.wu.o2o.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wu.o2o.dao.ShopDao;
import com.wu.o2o.dto.ImageHolder;
import com.wu.o2o.dto.ShopExecution;
import com.wu.o2o.entity.Shop;
import com.wu.o2o.enums.ShopStateEnum;
import com.wu.o2o.exceptions.ShopOperationException;
import com.wu.o2o.service.ShopService;
import com.wu.o2o.util.ImageUtil;
import com.wu.o2o.util.PageCalculator;
import com.wu.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {
	Logger log = LoggerFactory.getLogger(ShopServiceImpl.class);
	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
		// 空值判断
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NO_SHOP);
		}
		try {
			// 给店铺赋初始值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			// 添加店铺信息
			int effectNum = shopDao.insertShop(shop);
			if (effectNum <= 0) {

				throw new ShopOperationException("店铺创建失败");
			} else {
				
				if (thumbnail.getImage() != null) {
					// 存储图片
					try {
						addShopImg(shop, thumbnail);

					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error" + e.getMessage());
					}
					// 更新店铺图片信息
					effectNum = shopDao.updateShop(shop);
					if (effectNum <= 0)
						throw new ShopOperationException("更新图片地址失败");
				}
			}
		} catch (Exception e) {
			throw new ShopOperationException("addShop error" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHAKE, shop);
	}

	private void addShopImg(Shop shop, ImageHolder thumbnail) {
		// 获取shop图片目录的相对值路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		shop.setShopImg(shopImgAddr);
	}

	@Override
	public Shop getByShopId(long shopId) {

		return shopDao.queryByShopId(shopId);
	}

	@Override
	public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail)
			throws ShopOperationException {
		if (shop == null || shop.getShopId() == null)
			return new ShopExecution(ShopStateEnum.NO_SHOP);
		else {
			try {
			// 1 判断是否需要处理图片
			if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
				Shop tempShop = shopDao.queryByShopId(shop.getShopId());
				if (tempShop.getShopImg() != null) {
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());
				}
				addShopImg(shop, thumbnail);
			}
			// 2 更新店铺信息
			shop.setLastEditTime(new Date());
			log.debug("shop:"+shop);
			int effectNum = shopDao.updateShop(shop);
			if (effectNum <= 0) {
				return new ShopExecution(ShopStateEnum.INNER_ERROR);
			} else {
				shop = shopDao.queryByShopId(shop.getShopId());
				return new ShopExecution(ShopStateEnum.SUCCESS,shop);
			}
			}catch (Exception e) {
				throw new ShopOperationException("modifyShop error"+e.getMessage());
			}
		}
	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex=PageCalculator.calculatorRowsIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
			int count=shopDao.queryShopCount(shopCondition);
			ShopExecution shopExecution = new ShopExecution();
			if(shopList!=null) {
			shopExecution.setShoplist(shopList);
			shopExecution.setCount(count);
			}else {
				shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
			}
		return shopExecution;
	}

}
