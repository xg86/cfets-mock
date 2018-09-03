/**
 * 
 */
package com.xquant.platform.component.back.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xquant.platform.component.mock.CfetsRecvMessageMockManager;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.back.mock
 * @author: guanglai.zhou
 * @date: 2018-07-19 16:49:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicaitonMockContext.xml")
public class TestRecvTask {

	@Autowired
	private CfetsRecvMessageMockManager cfetsRecvMessageMockManager;
	
	@Test
	public void test() {
		cfetsRecvMessageMockManager.addBondDlgRecvAddTask(1);
	}
}
