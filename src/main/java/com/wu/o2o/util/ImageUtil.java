package com.wu.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wu.o2o.dto.ImageHolder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	// 获取path绝对路径
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random random = new Random();
	private static Logger log = LoggerFactory.getLogger(ImageUtil.class);

	/**
	 * 将CommonsMultipartFile转换成File
	 * 
	 * @param cFile
	 * @return
	 */
	public static File transferCommonsMultipartFiletoFile(CommonsMultipartFile cFile) {
		File newFile = new File(cFile.getOriginalFilename());
		try {
			cFile.transferTo(newFile);
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}

	/**
	 * 处理缩略图,并返回新生成图片的相对值路径
	 * 
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		log.debug("current relativeAddr is " + relativeAddr);
		log.debug("the complete Addr is " + PathUtil.getImgBasePath() + relativeAddr);
		log.debug(basePath + "/1.jpg");
		try {
			Thumbnails.of(thumbnail.getImage()).size(2000, 2000)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/1.jpg")), 0.25f)
					.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return relativeAddr;

	}

	public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		log.debug("current relativeAddr is " + relativeAddr);
		log.debug("the complete Addr is " + PathUtil.getImgBasePath() + relativeAddr);
		log.debug(basePath + "/1.jpg");
		try {
			Thumbnails.of(thumbnail.getImage()).size(337, 640)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/1.jpg")), 0.25f)
					.outputQuality(0.9f).toFile(dest);
		} catch (IOException e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return relativeAddr;

	}

	/**
	 * 创建目标路径所涉及的目录
	 * 
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists())
			dirPath.mkdirs();

	}

	/**
	 * 获取输入文件流的扩展名
	 * 
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(String fileName) {

		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 生成随机数名称,当前年月日小时分钟+五位随机数
	 * 
	 * @return
	 */
	public static String getRandomFileName() {
		int rannum = random.nextInt(89999) + 10000;
		String nowTimeStr = sdf.format(new Date());
		return nowTimeStr + rannum;
	}

	/**
	 * storePath 是文件的路径还是目录的路径 如果是文件路径则删除该文件 如果是目录则删除该目录
	 * 
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {

		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		if (fileOrPath.exists()) {
			if (fileOrPath.isDirectory()) {
				File[] files = fileOrPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}

	public static void main(String[] args) {

		System.err.println(basePath);
		try {
			Thumbnails.of(new File("2.jpg")).size(2000, 2000)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/1.jpg")), 0.25f)
					.outputQuality(0.8f).toFile("src/3.jpg");
			;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
