package com.wu.o2o.web.frontend;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wu.o2o.dto.ProductExecution;
import com.wu.o2o.entity.Product;
import com.wu.o2o.entity.ProductCategory;
import com.wu.o2o.entity.Shop;
import com.wu.o2o.service.ProductCategoryService;
import com.wu.o2o.service.ProductService;
import com.wu.o2o.service.ShopService;
import com.wu.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * 获取店铺信息以及该店铺下面的商品类别列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listshopdetailpageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取前台传过来的shopId
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		Shop shop = null;
		List<ProductCategory> productCategoryList = null;
		if (shopId != -1) {
			// 获取店铺Id为shopId的店铺信息
			shop = shopService.getByShopId(shopId);
			// 获取店铺下面的商品累类别列表
			productCategoryList = productCategoryService.getProductCategoryList(shopId);
			modelMap.put("shop", shop);
			modelMap.put("productCategoryList", productCategoryList);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/listproductsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductByShop(HttpServletRequest request) {
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取页码大小
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		// 获取店铺Id
		long shopId = HttpServletRequestUtil.getInt(request, "shopId");
		// 非空判断
		if ((pageIndex > -1 && (pageSize > -1)) && (shopId > -1)) {
			// 获取一级列表的Id
			Long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
			// 获取特定二级类别Id
			String productName = HttpServletRequestUtil.getString(request, "productName");

			Product productCondition = compactShopCondition4Search(shopId, productCategoryId, productName);
			ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageIndex or pageSize or shopId ");
		}
		return modelMap;

	}

	private Product compactShopCondition4Search(long shopId, Long productCategoryId, String productName) {
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		if ((productCategoryId != -1)) {
			// 查询某个商品类别下面的商品列表
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		if (productName!=null) {
			//查询名字下面包含的productName的店铺列表
			productCondition.setProductName(productName);
		}
		
		//只允许选出状态为1的商品(即为上架状态)
		productCondition.setEnableStatus(1);
		return productCondition;
	}

}
