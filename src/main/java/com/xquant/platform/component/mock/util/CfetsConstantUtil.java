package com.xquant.platform.component.mock.util;

import com.xQuant.base2.support.config.ConfigSupport;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.util
 * @author: guanglai.zhou
 * @date: 2018-06-16 20:23:45
 */
public class CfetsConstantUtil {

	/**
	 * 设置新创建的报价的有效时间 单位为分钟
	 */
	public static final String VALIDATE_TIME_AFTER_NOW = "com.xquant.cfets.validate.time.delay";

	/**
	 * 是否需要再将报价转到中央控制服务器 而不是由程序自动产生对手报文
	 */
	public static final String USE_CENTER_SERVER = "com.xquant.cfets.use.center.server";
	/**
	 * 中央服务器地址
	 */
	public static final String USE_CENTER_SERVER_HOST = "com.xquant.cfets.center.server.host";
	/**
	 * 中央服务器端口
	 */
	public static final String USE_CENTER_SERVER_PORT = "com.xquant.cfets.center.server.port";

	/**
	 * 是否手工输入对手报价
	 */
	public static final String COUNTER_MESSAGE_AUTOSENT = "com.xquant.cfets.counter.message.autosent";

	/**
	 * 对手自动发送报价开始时间
	 */
	public static final String COUNTER_MESSAGE_AUTOSENT_INITIALDELAY = "com.xquant.cfets.counter.message.autosent.initialdelay";

	/**
	 * 对手自动发送报价间隔时间
	 */
	public static final String COUNTER_MESSAGE_AUTOSENT_DELAY = "com.xquant.cfets.counter.message.autosent.delay";
	
	/**
	 * 对手自动回复请求报价
	 */
	public static final String COUNTER_RFQREPLY_AUTOSENT = "com.xquant.cfets.rfqreply.message.autosent";
	
	/**
	 * 对手自动回复请求报价时间
	 */
	public static final String COUNTER_RFQREPLY_AUTOSENT_DELAY = "com.xquant.cfets.rfqreply.message.autosent.delay";
	
	/**
	 * 对手自动回复对话报价
	 */
	public static final String COUNTER_DLG_AUTOSENT = "com.xquant.cfets.dlg.message.autosent";
	
	/**
	 * 对手自动回复对话报价时间
	 */
	public static final String COUNTER_DLG_AUTOSENT_DELAY = "com.xquant.cfets.dlg.message.autosent.delay";
	

	/**
	 * 获取报价有效时间
	 * 
	 * @return
	 */
	public static String getValidateTimeDelay() {
		return ConfigSupport.getInstance().getProperty(VALIDATE_TIME_AFTER_NOW, "30");
	}

	/**
	 * 获取报价有效时间
	 * 
	 * @return
	 */
	public static Long getAutoSentInitDelay() {
		return Long.valueOf(ConfigSupport.getInstance().getProperty(COUNTER_MESSAGE_AUTOSENT_INITIALDELAY, "5"));
	}

	/**
	 * 获取报价有效时间
	 * 
	 * @return
	 */
	public static Long getAutoSentDelay() {
		return Long.valueOf(ConfigSupport.getInstance().getProperty(COUNTER_MESSAGE_AUTOSENT_DELAY, "10"));
	}
	
	/**
	 * 是否使用中央控制台进行报价转发
	 * 
	 * @return
	 */
	public static Boolean useCenterServer() {
		return new Boolean(ConfigSupport.getInstance().getProperty(USE_CENTER_SERVER, "false"));
	}

	/**
	 * 对手自动报价
	 * 
	 * @return
	 */
	public static Boolean counterMessageAutoSent() {
		return new Boolean(ConfigSupport.getInstance().getProperty(COUNTER_MESSAGE_AUTOSENT, "false"));
	}
	
	/**
	 * 对手自动报价
	 * 
	 * @return
	 */
	public static Boolean counterRfqMessageAutoReply() {
		return new Boolean(ConfigSupport.getInstance().getProperty(COUNTER_RFQREPLY_AUTOSENT, "false"));
	}
	
	/**
	 * 获取报价有效时间
	 * 
	 * @return
	 */
	public static Integer getAutoReplySentDelay() {
		return Integer.valueOf(ConfigSupport.getInstance().getProperty(COUNTER_RFQREPLY_AUTOSENT_DELAY, "5"));
	}
	
	/**
	 * 对手自动报价
	 * 
	 * @return
	 */
	public static Boolean counterDlgMessageAutoReply() {
		return new Boolean(ConfigSupport.getInstance().getProperty(COUNTER_DLG_AUTOSENT, "false"));
	}
	
	/**
	 * 获取报价有效时间
	 * 
	 * @return
	 */
	public static Integer getAutoDlgSentDelay() {
		return Integer.valueOf(ConfigSupport.getInstance().getProperty(COUNTER_DLG_AUTOSENT_DELAY, "5"));
	}

	/**
	 * 中央控制台地址
	 * 
	 * @return
	 */
	public static String getCenterServerHost() {
		return ConfigSupport.getInstance().getProperty(USE_CENTER_SERVER_HOST, "127.0.0.1");
	}

	/**
	 * 中央控制台端口
	 * 
	 * @return
	 */
	public static Integer getCenterServerPort() {
		return Integer.parseInt(ConfigSupport.getInstance().getProperty(USE_CENTER_SERVER_PORT, "9638"));
	}
	
	public static final String CFETS_MOCK_PARTYNAME = "tc.itf.cfets.mock.partyName";
	
	/**
	 * 本方机构名称
	 * @return
	 */
	public static String getPartyName() {
		return ConfigSupport.getInstance().getProperty(CFETS_MOCK_PARTYNAME, "杭州衡泰测试");
	}
	
	public static final String CFETS_MOCK_TRADENAME = "tc.itf.cfets.mock.traderName";
	
	/**
	 * 本方交易员名称
	 * @return
	 */
	public static String getTradeName() {
		return ConfigSupport.getInstance().getProperty(CFETS_MOCK_TRADENAME, "a080");
	}
	
	public static final String CFETS_MOCK_CUSTODIANINSTITUTIONNAME = "tc.itf.cfets.mock.custodianInstitutionName";
	
	/**
	 * 本方托管机构名称
	 * @return
	 */
	public static String getCustodianInstitutionName() {
		return ConfigSupport.getInstance().getProperty(CFETS_MOCK_CUSTODIANINSTITUTIONNAME, "a080");
	}
	
	public static final String CFETS_MOCK_CASHBANKNAME = "tc.itf.cfets.mock.cashBankName";
	
	/**
	 * 本方资金账户行名
	 * @return
	 */
	public static String getCashBankName() {
		return ConfigSupport.getInstance().getProperty(CFETS_MOCK_CASHBANKNAME, "杭州衡泰测试");
	}
	
	public static final String CFETS_MOCK_COUNTERPARTYNAME = "tc.itf.cfets.mock.counterPartyName";
	
	/**
	 * 对手机构名称
	 * @return
	 */
    public static String getCounterPartyName() {
    	return ConfigSupport.getInstance().getProperty(CFETS_MOCK_COUNTERPARTYNAME, "恒天基金");
    }
}
