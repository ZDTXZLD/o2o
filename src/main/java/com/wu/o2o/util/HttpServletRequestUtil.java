package com.wu.o2o.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpServletRequestUtil {
	private static final Logger log=LoggerFactory.getLogger(HttpServletRequestUtil.class);
	public static int getInt(HttpServletRequest requset,String key) {
		try {
			return Integer.decode(requset.getParameter(key));
		} catch (Exception e) {
			return -1;
		}
		
	}
	
	public static Long getLong(HttpServletRequest request,String key) {
		try {
			String string = request.getParameter(key);
			log.debug("productCategoryId"+string);
			return Long.decode(string);
		} catch (Exception e) {
			return -1L;
		}
		
	}
	public static Double getDouble(HttpServletRequest requset,String key) {
		try {
			return Double.valueOf(requset.getParameter(key));
		} catch (Exception e) {
			return -1d;
		}
		
	}
	public static boolean getBoolean(HttpServletRequest requset,String key) {
		try {
			return Boolean.valueOf(requset.getParameter(key));
		} catch (Exception e) {
			return false;
		}
		
	}
	
	public static String getString(HttpServletRequest requset,String key) {
		String result=requset.getParameter(key);
		try {
			if(result!=null) {
				result=result.trim();
			}
			if("".equals(result))
				result= null;
			return result;
		} catch (Exception e) {
			return null;
		}
	}

}
