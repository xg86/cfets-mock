/**
 * 
 */
package com.xquant.platform.component.back.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xquant.platform.component.mock.util.CfetsConstantUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.back.mock
 * @author: guanglai.zhou
 * @date: 2018-07-19 16:49:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicaitonMockContext.xml")
public class TestSystemConfig {

	
	@Test
	public void test() {
		System.out.println(CfetsConstantUtil.getValidateTimeDelay()); 
	}
}
