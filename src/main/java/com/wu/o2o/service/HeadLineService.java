package com.wu.o2o.service;

import java.io.IOException;
import java.util.List;

import com.wu.o2o.entity.HeadLine;

public interface HeadLineService {
	/**
	 * 根据传入的信息返回指定的头条信息
	 * @param headLineCondition
	 * @return
	 * @throws IOException
	 */
	List<HeadLine> queryHeadLineList(HeadLine headLineCondition) throws IOException;
}
