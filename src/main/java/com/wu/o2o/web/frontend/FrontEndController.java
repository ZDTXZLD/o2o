package com.wu.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/frontend")
public class FrontEndController {
	/**
	 * 首页展示路由
	 * @return
	 */
	@RequestMapping(value = "/index")
	private  String Index() {
		return "frontend/index";
	}
	/**
	 * 商品列表页路由
	 * @return
	 */
	@RequestMapping(value="/shoplist",method=RequestMethod.GET)
	private  String showShopList() {
		return "frontend/shoplist";
	}
	/**
	 * 店铺详情页路由
	 * @return
	 */
	@RequestMapping(value="/shopdetail",method=RequestMethod.GET)
	private String showShopDetail() {
		return "frontend/shopdetail";
	}
	@RequestMapping(value="/productdetail",method=RequestMethod.GET)
	private String showProductDetail() {
		return "frontend/productdetail";
	}
	
	
	@RequestMapping(value="/unfound",method=RequestMethod.GET)
	private String showIndex() {
		return "frontend/index";
	}
	
	
}
