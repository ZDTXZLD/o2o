package com.wu.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wu.o2o.dto.ImageHolder;
import com.wu.o2o.dto.ProductExecution;
import com.wu.o2o.entity.Product;
import com.wu.o2o.entity.ProductCategory;
import com.wu.o2o.entity.Shop;
import com.wu.o2o.enums.ProductStateEnum;
import com.wu.o2o.exceptions.ProductOperationException;
import com.wu.o2o.service.ProductCategoryService;
import com.wu.o2o.service.ProductService;
import com.wu.o2o.util.CodeUtil;
import com.wu.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
	@Autowired
	private ProductService productService;

	@Autowired
	private ProductCategoryService productCategoryService;
	Logger logger = LoggerFactory.getLogger(ProductManagementController.class);

	// 支持上床商品详情图的最大数量
	private static final int IMAGEMAXCOUNT = 6;

	
	
	@RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductListByShop(HttpServletRequest request) {
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		//获取前台传过来的页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		//获取当前店铺信息
		Shop shop = (Shop) request.getSession().getAttribute("currentShop");
		//空值判断
		if((pageIndex>-1)&&(pageSize>-1)&&(shop!=null)&&(shop.getShopId()!=null)) {
			//获取传入的需要检索的条件
			Long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
			String productName = HttpServletRequestUtil.getString(request,"productCategoryName");
			Product productCondition=compactProductCondition(shop.getShopId(),productCategoryId,productName);
			//传入条件以及分页信息,返回相应商品列表以及总数
			ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}
	private Product compactProductCondition(Long shopId, Long productCategoryId, String productName) {
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		//若有指定类别的要求则添加进去
		if(productCategoryId!=-1L) {
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
			
		}
		if(productName!=null) {
			productCondition.setProductName(productName);
		}
		
		return productCondition;
	}
	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProduct(HttpServletRequest request) {
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.cheakVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入有误!");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		MultipartHttpServletRequest multipartHttpServletRequest = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {

			// 若请求中存在文件流,则取出相关文件
			if (multipartResolver.isMultipart(request)) {
				thumbnail = handleImage(request,thumbnail, productImgList);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			logger.debug(productStr);
			// 尝试获取前端传过来的的表单string流并将其转换成Product实体类
			product = mapper.readValue(productStr, Product.class);
			logger.debug("" + product);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}

		// 若Product信息,缩略图以及详情图列表为空,则进行商品添加操作
		if (product != null && thumbnail != null && productImgList.size() > 0) {
			try {
				// 从session中获取当前店铺信息
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				// 执行添加操作
				ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;
	}

	private ImageHolder handleImage(HttpServletRequest request,ImageHolder thumbnail, List<ImageHolder> productImgList) throws IOException {
		MultipartHttpServletRequest multipartHttpServletRequest;
		
		multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
		thumbnail = new ImageHolder(thumbnailFile.getInputStream(), thumbnailFile.getOriginalFilename());
		for (int i = 0; i < IMAGEMAXCOUNT; i++) {
			CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartHttpServletRequest
					.getFile("productImg" + i);
			if (productImgFile != null) {
				ImageHolder imageHolder = new ImageHolder(productImgFile.getInputStream(),
						productImgFile.getOriginalFilename());
				productImgList.add(imageHolder);
			} else {
				break;
			}

		}
		return thumbnail;
	}

	@RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> removeProductCategorys(@RequestParam Long productId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 非空判断
		if (productId > -1) {
			Product product = productService.getProductById(productId);
			List<ProductCategory> productCategoryList = productCategoryService
					.getProductCategoryList(product.getShop().getShopId());
			modelMap.put("success", true);
			modelMap.put("product", product);
			modelMap.put("productCategoryList", productCategoryList);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> removeProductCategorys(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 商品编辑时调用还是商品上下架时调用,若为商品上下架时调用,则跳过验证码校验
		boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
		// 验证码判断
		if (!statusChange && !CodeUtil.cheakVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入有误!");
			return modelMap;
		}

		// 接受前端参数的初始值,包括商品,缩略图,详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			// 若请求中存在文件流,则取出相关文件
			if (multipartResolver.isMultipart(request)) {
				thumbnail= handleImage(request,thumbnail, productImgList);
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			String productStr = HttpServletRequestUtil.getString(request, "productStr");
			// 尝试获取前端传过来的的表单string流并将其转换成Product实体类
			product = mapper.readValue(productStr, Product.class);
			logger.debug("" + product);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}

		// 非空判断
		if (product != null) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				product.setShop(currentShop);
				// 开始进行商品信息变更操作
				ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入商品信息");
		}
		return modelMap;
	}

}
