package com.xquant.platform.component.mock.generator;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.xquant.platform.component.itf.cfets.common.send.IReqSerialNoGenerator;

public class MockReqSerialNoGenerator implements IReqSerialNoGenerator {

	@Override
	public String nextSerialNo() {
		return new SimpleDateFormat("yyyyMMddHHmmsssss").format(new Date());
	}

}
