package com.xquant.platform.component.mock.resolver;

/**   
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.resolver 
 * @author: guanglai.zhou   
 * @date: 2018-07-24 15:50:48
 */
public interface IMessageKeyGeneratorStrategy<T> {

	  public String generateKey(T message);
}
