package com.wu.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wu.o2o.entity.Shop;

public interface ShopDao {
	
	
	/**
	 *  分页查询店铺,可输入条件有:店铺名(模糊),店铺状态,店铺类别,区域Id,owner
	 * @param shopCondition
	 * @param rowIndex 	从哪一行开始取
	 * @param pageSize 返回的行数
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	

	/**
	 * 
	 * @param shopCondition
	 * @return 返回queryShopList的总数
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition );
	
	/**
	 * 
	 * 通过shopId 查询店铺
	 * 
	 * @param shopId
	 * @return
	 */

	Shop queryByShopId(long shopId);

	/**
	 * 新增店铺
	 * 
	 * @param shop
	 * @return 错误返回-1
	 */
	int insertShop(Shop shop);

	/**
	 * 更新店铺信息
	 * 
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
}
