/**
 * 
 */
package com.xquant.platform.component.mock.util;

import com.xquant.platform.component.itf.cfets.skeleton.enums.quote.SideEnum;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.util
 * @author: guanglai.zhou
 * @date: 2018-06-16 18:01:15
 */
public class CfetsTradeSideUtil {

    public static String changeSide(String side) {
	if (SideEnum.SELL.getValue().equals(side)) {
	    return SideEnum.PURCHASE.getValue();
	}
	if (SideEnum.PURCHASE.getValue().equals(side)) {
	    return SideEnum.SELL.getValue();
	}
	if (SideEnum.PLEDGEDREPO.getValue().equals(side)) {
	    return SideEnum.PLEDGEDREVERSE.getValue();
	}
	if (SideEnum.PLEDGEDREVERSE.getValue().equals(side)) {
	    return SideEnum.PLEDGEDREPO.getValue();
	}
	throw new RuntimeException("不存在指定的交易方向" + side);
    }
}
