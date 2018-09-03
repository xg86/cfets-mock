package com.xquant.platform.component.back.mock;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xquant.platform.component.commons.ATypeDefines;
import com.xquant.platform.component.commons.MarketTypeEnum;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.entity.instrument.InstrumentKey;
import com.xquant.platform.component.mock.util.ComputeServiceUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.back.mock
 * @author: guanglai.zhou
 * @date: 2018-07-19 16:49:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appContext-component-mock-service.xml")
public class TestComputeService {

	@Autowired
	private ComputeService computeService;

	@Test
	public void test() {
		assertNotNull(computeService);
		// 指定key
		InstrumentKey instrumentKey = ComputeServiceUtil.getInstrumentKey("1080023",
				ATypeDefines.A_TYPE_CODE_SPT_BD, MarketTypeEnum.X_CNBD.getValue());
		// 应计利息
		double ai = ComputeServiceUtil.getai(computeService, instrumentKey, "2018-07-19", 0);
		System.out.println(ai);
	}
	
	@Test
	public void testComputeService() {
		String securityID = "010214";
		InstrumentKey instrumentKey = ComputeServiceUtil.getInstrumentKey(securityID,
				ATypeDefines.A_TYPE_CODE_SPT_BD, MarketTypeEnum.X_CNBD.getValue());
		double npv = 103.9467;
		String valueDate = "2018-08-21";
		double ytm = computeService.ytm(0, npv, instrumentKey, valueDate);
		double ai = computeService.ai(0, instrumentKey, valueDate);
		System.out.println(ytm);
		System.out.println(ai);
		
		valueDate = "2018-08-21";
		ytm = computeService.ytm(0, npv, instrumentKey, valueDate);
		ai = computeService.ai(0, instrumentKey, valueDate);
		System.out.println(ytm);
		System.out.println(ai);
		
	}

}
