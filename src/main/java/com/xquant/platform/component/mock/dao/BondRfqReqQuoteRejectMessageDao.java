package com.xquant.platform.component.mock.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQRejectResMessageBody;

@Repository
public interface BondRfqReqQuoteRejectMessageDao {

	/**
	 * 根据消息内容进行消息的入库
	 * 
	 * @param message
	 * @return
	 */
	public int insert(CfetsTradeBondRFQRejectResMessageBody message);

	/**
	 * 针对报价进行额外参数的添加
	 * 
	 * @param quoteId
	 *            报价编号
	 * @param action
	 *            报价动作编号
	 * @param sendOrRecv
	 *            发送标识
	 * @param myside
	 *            发送方向
	 * @param clientId
	 *            客户端编号
	 * @return
	 */
	public int updateMessage(@Param("quoteId") String quoteId, @Param("action") String action, @Param("sendOrRecv") String sendOrRecv,
			@Param("myside") String myside, @Param("clientId") String clientId);

	public int updateStatus(@Param("quoteId") String quoteId, @Param("status") Integer status, @Param("transactTime") String transactTime);

	/**
	 * 删除报价
	 * 
	 * @param quoteId
	 * @return
	 */
	public int delete(@Param("quoteId") String quoteId);

	/**
	 * 根据报价编号进行查询
	 * 
	 * @param quoteId
	 * @return
	 */
	public CfetsTradeBondRFQRejectResMessageBody queryByQuoteId(@Param("quoteId") String quoteId);
}
