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

import com.wu.o2o.dto.ShopExecution;
import com.wu.o2o.entity.Area;
import com.wu.o2o.entity.Shop;
import com.wu.o2o.entity.ShopCategory;
import com.wu.o2o.service.AreaService;
import com.wu.o2o.service.ShopCategoryService;
import com.wu.o2o.service.ShopService;
import com.wu.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
	@Autowired
	private AreaService areaService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private ShopService shopService;

	/**
	 * 返回商品列表里的ShopCategory列表(一级或二级),以及区域列表
	 * 
	 * @param request
	 * @return
	 */                        
	@RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 试着从前端请求中获取parentId
		Long parentId = HttpServletRequestUtil.getLong(request, "parentId");

		List<ShopCategory> shopCategoryList = null;
		if (parentId != -1) {
			try {
				ShopCategory shopCategoryCondition = new ShopCategory();
				ShopCategory parent = new ShopCategory();
				shopCategoryCondition.setParent(parent);
				shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());

			}
		} else {
			try {
				// 如果·parentId不存在,则取出所有一级ShopCategory(用户在首页所看到的店铺列表)
				shopCategoryList = shopCategoryService.getShopCategoryList(null);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}

		modelMap.put("shopCategoryList", shopCategoryList);

		List<Area> areaList = null;
		try {
			areaList = areaService.getAreaList();
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
    @RequestMapping(value="/listshops",method=RequestMethod.GET)
    @ResponseBody
	private Map<String, Object> listShops(HttpServletRequest request) {
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取页码大小
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

		// 非空判断
		if ((pageIndex > -1 && (pageSize > -1))) {
			// 获取一级列表的Id
			Long parentId = HttpServletRequestUtil.getLong(request, "parentId");
			// 获取特定二级类别Id
			Long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
			// 获取区域列表Id
			int areaId = HttpServletRequestUtil.getInt(request, "areaId");
			// 获取模糊查询名
			String shopName = HttpServletRequestUtil.getString(request, "shopName");
			// 获取组合之后的查询条件
			Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
			ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
			modelMap.put("shopList", se.getShoplist());
			modelMap.put("count",se.getCount());
			modelMap.put("success",true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageIndex or pageSize ");
		}
       return modelMap;
	}
    /**
     * 组合查询条件,并将查询信息封装到shopCondition对象里面
     * @param parentId
     * @param shopCategoryId
     * @param areaId
     * @param shopName
     * @return
     */
	private Shop compactShopCondition4Search(Long parentId, Long shopCategoryId, int areaId,
			String shopName) {
		Shop shopCondition = new Shop();
		if(parentId!=-1) {
			//查询一级类别下的二级店铺列表信息
			ShopCategory childCategory= new ShopCategory();
			ShopCategory parentCategory = new ShopCategory();
			parentCategory.setShopCategoryId(parentId);
			childCategory.setParent(parentCategory);
			shopCondition.setShopCategory(childCategory);
		}
		if (shopCategoryId!=-1) {
			//查询摸个二级ShopCondition列表之下的店铺列表
			ShopCategory shopCategory = new ShopCategory();
			shopCategory.setShopCategoryId(shopCategoryId);
			shopCondition.setShopCategory(shopCategory);
		}
		if (areaId!=-1) {
			//查询某个区域列表下的店铺信息
			Area area = new Area();
			area.setAreaId(areaId);
			shopCondition.setArea(area);
		}
		if(shopName!=null) {
			//查询名字里含有shopName的店铺列表
			shopCondition.setShopName(shopName);
		}
		shopCondition.setEnableStatus(1);
		return shopCondition;
	}
}
