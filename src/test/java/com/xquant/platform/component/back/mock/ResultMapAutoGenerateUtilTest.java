package com.xquant.platform.component.back.mock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xquant.platform.component.back.test.base.MyTestWaiter;
import com.xquant.platform.component.itf.cfets.common.dao.ArchiveApproveQuoteRelationMapper;
import com.xquant.platform.component.itf.cfets.common.dao.ArchiveQuoteOrderSnapshotMapper;
import com.xquant.platform.component.itf.cfets.common.dao.ArchiveRequestLogMapper;
import com.xquant.platform.component.itf.cfets.common.entity.archive.ArchiveApproveQuoteRelation;
import com.xquant.platform.component.itf.cfets.common.entity.archive.ArchiveQuoteOrderSnapshot;
import com.xquant.platform.component.itf.cfets.common.entity.archive.ArchiveRequestLog;
import com.xquant.platform.component.mock.dao.DatabaseQueryDao;
import com.xquant.platform.component.mock.util.mybatis.ResultMapAutoGenerateUtil;

import io.netty.util.CharsetUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.back.cfets.notify
 * @author: guanglai.zhou
 * @date: 2018-06-14 20:52:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appContext-component-mock-service.xml")
public class ResultMapAutoGenerateUtilTest {

	@Autowired
	private DatabaseQueryDao databaseQueryDao;

	@Test
	public void test() {
		MyTestWaiter.testWaitBefore();
		Class<?> namespaceType = ArchiveRequestLogMapper.class;
		String tableName = "ARCHIVE_TTRD_XCC_CFETS_REQUEST_LOG";
		Class<?> parameterType = ArchiveRequestLog.class;
		String generateXml = ResultMapAutoGenerateUtil.generateMapperXml(databaseQueryDao, namespaceType, tableName,
				parameterType);
		System.out.println("****************************************");
		System.out.println(generateXml);
	}
}
