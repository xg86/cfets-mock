package com.xquant.platform.component.back.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xquant.platform.component.back.test.base.MyTestWaiter;
import com.xquant.platform.component.mock.netty.MyCfetsLongLiveNettyServer;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.back.cfets.notify
 * @author: guanglai.zhou
 * @date: 2018-06-14 20:52:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appContext-component-mock-netty.xml")
public class NettyInitTest {

	
	@Qualifier("cfetsLongLiveNettySever")
	@Autowired
	private MyCfetsLongLiveNettyServer cfetsLongLiveNettySever;
	
	@Test
	public void test() {
		
		MyTestWaiter.testWaitBefore();
		System.out.println(cfetsLongLiveNettySever.getPort()); 
	}
	


}
