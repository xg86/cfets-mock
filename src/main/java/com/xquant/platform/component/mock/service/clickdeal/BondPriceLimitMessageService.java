package com.xquant.platform.component.mock.service.clickdeal;

import java.util.List;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondPriceLimitQuoteResMessage;
import com.xquant.platform.component.mock.dto.cfets.MockBondPriceLimitQuoteResMessageBody;

public interface BondPriceLimitMessageService {

    /**
     * 本方新增报价
     *
     * @param message
     * @return
     */
    public boolean addMessage(CfetsTradeBondPriceLimitQuoteResMessage message);

    /**
     * 本方撤销报价
     *
     * @param message
     * @return
     */
    public boolean cancelMessage(CfetsTradeBondPriceLimitQuoteResMessage message);

    /**
     * 本方新增报价过期
     *
     * @param message
     * @return
     */
    public boolean expireMessage(CfetsTradeBondPriceLimitQuoteResMessage message);

    /**
     * 查询有效时间在当前时间之后的未成交的报价
     *
     * @return
     */
    public List<MockBondPriceLimitQuoteResMessageBody> queryUnFinalValidQuote();
}
