package com.wu.o2o.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathUtil {
	private static String seperator = System.getProperty("file.separator");
    private static Logger  log=LoggerFactory.getLogger(PathUtil.class);
	public static String getImgBasePath() {

		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/projectdev/image";
		} else {
			basePath = "/home/wu/image";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
	
	public static String getShopImagePath(long shopId) {
		String imagePath="/upload/item/shop/"+shopId+"/";
		return imagePath.replace("/", seperator);
		
	}

}
