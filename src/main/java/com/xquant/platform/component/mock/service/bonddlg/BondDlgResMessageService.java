package com.xquant.platform.component.mock.service.bonddlg;

import java.util.List;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessage;
import com.xquant.platform.component.mock.dto.cfets.MockBondDialogueQuoteResMessageBody;

public interface BondDlgResMessageService {

    /**
     * 本方新增报价
     *
     * @param message
     * @return
     */
    public boolean addMessage(CfetsTradeBondDialogueQuoteResMessage message);

    /**
     * 本方修改报价
     *
     * @param message
     * @return
     */
    public boolean updateMessage(CfetsTradeBondDialogueQuoteResMessage message);

    /**
     * 本方撤销报价
     *
     * @param message
     * @return
     */
    public boolean cancelMessage(CfetsTradeBondDialogueQuoteResMessage message);

    /**
     * 本方新增报价过期
     *
     * @param message
     * @return
     */
    public boolean expireMessage(CfetsTradeBondDialogueQuoteResMessage message);

    /**
     * 对手成交
     *
     * @param message
     * @return
     */
    public boolean confirmMessage(CfetsTradeBondDialogueQuoteResMessage message);


    /**
     * 对手拒绝
     *
     * @param message
     * @return
     */
    public boolean rejectMessage(CfetsTradeBondDialogueQuoteResMessage message);

    /**
     * 查询有效时间在当前时间之后的未成交的报价
     *
     * @return
     */
    public List<MockBondDialogueQuoteResMessageBody> queryUnFinalValidQuote();

}
