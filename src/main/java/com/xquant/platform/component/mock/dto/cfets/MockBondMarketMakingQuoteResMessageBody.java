/**
 * 
 */
package com.xquant.platform.component.mock.dto.cfets;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessageBody;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteBizTypeEnum;
import com.xquant.platform.component.mock.dto.IMockCfetsMessageBody;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.dto.cfets
 * @author: guanglai.zhou
 * @date: 2018-08-18 20:28:12
 */
@SuppressWarnings("serial")
public class MockBondMarketMakingQuoteResMessageBody extends CfetsTradeBondMarketMakingQuoteResMessageBody
		implements IMockCfetsMessageBody {

	private String action;
	private String sendOrRecv;
	private String clientId;
	private String updateTime;
	private String bytmyield;
	private String sytmyield;
	private String bstrikeyield;
	private String sstrikeyield;
	private String bprice;
	private String sprice;
	private String bdirtyprice;
	private String sdirtyprice;
	private String baccruedinterestamt;
	private String saccruedinterestamt;
	private String borderqty;
	private String sorderqty;
	private String btradecashamt;
	private String stradecashamt;
	private String baccruedinteresttotalamt;
	private String saccruedinteresttotalamt;
	private String bsettlcurramt;
	private String ssettlcurramt;
	private String bsettlcurrency;
	private String ssettlcurrency;
	private String bsettlcurrfxrate;
	private String ssettlcurrfxrate;
	private String bdeliverytype;
	private String sdeliverytype;
	private String bclearingmethod;
	private String sclearingmethod;
	private String bsettltype;
	private String ssettltype;
	private String bsettldate;
	private String ssettldate;
	private String btotalprincipal;
	private String stotalprincipal;
	private String bleavesqty;
	private String sleavesqty;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSendOrRecv() {
		return sendOrRecv;
	}

	public void setSendOrRecv(String sendOrRecv) {
		this.sendOrRecv = sendOrRecv;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getBytmyield() {
		return bytmyield;
	}

	public void setBytmyield(String bytmyield) {
		this.bytmyield = bytmyield;
	}

	public String getSytmyield() {
		return sytmyield;
	}

	public void setSytmyield(String sytmyield) {
		this.sytmyield = sytmyield;
	}

	public String getBstrikeyield() {
		return bstrikeyield;
	}

	public void setBstrikeyield(String bstrikeyield) {
		this.bstrikeyield = bstrikeyield;
	}

	public String getSstrikeyield() {
		return sstrikeyield;
	}

	public void setSstrikeyield(String sstrikeyield) {
		this.sstrikeyield = sstrikeyield;
	}

	public String getBprice() {
		return bprice;
	}

	public void setBprice(String bprice) {
		this.bprice = bprice;
	}

	public String getSprice() {
		return sprice;
	}

	public void setSprice(String sprice) {
		this.sprice = sprice;
	}

	public String getBdirtyprice() {
		return bdirtyprice;
	}

	public void setBdirtyprice(String bdirtyprice) {
		this.bdirtyprice = bdirtyprice;
	}

	public String getSdirtyprice() {
		return sdirtyprice;
	}

	public void setSdirtyprice(String sdirtyprice) {
		this.sdirtyprice = sdirtyprice;
	}

	public String getBaccruedinterestamt() {
		return baccruedinterestamt;
	}

	public void setBaccruedinterestamt(String baccruedinterestamt) {
		this.baccruedinterestamt = baccruedinterestamt;
	}

	public String getSaccruedinterestamt() {
		return saccruedinterestamt;
	}

	public void setSaccruedinterestamt(String saccruedinterestamt) {
		this.saccruedinterestamt = saccruedinterestamt;
	}

	public String getBorderqty() {
		return borderqty;
	}

	public void setBorderqty(String borderqty) {
		this.borderqty = borderqty;
	}

	public String getSorderqty() {
		return sorderqty;
	}

	public void setSorderqty(String sorderqty) {
		this.sorderqty = sorderqty;
	}

	public String getBtradecashamt() {
		return btradecashamt;
	}

	public void setBtradecashamt(String btradecashamt) {
		this.btradecashamt = btradecashamt;
	}

	public String getStradecashamt() {
		return stradecashamt;
	}

	public void setStradecashamt(String stradecashamt) {
		this.stradecashamt = stradecashamt;
	}

	public String getBaccruedinteresttotalamt() {
		return baccruedinteresttotalamt;
	}

	public void setBaccruedinteresttotalamt(String baccruedinteresttotalamt) {
		this.baccruedinteresttotalamt = baccruedinteresttotalamt;
	}

	public String getSaccruedinteresttotalamt() {
		return saccruedinteresttotalamt;
	}

	public void setSaccruedinteresttotalamt(String saccruedinteresttotalamt) {
		this.saccruedinteresttotalamt = saccruedinteresttotalamt;
	}

	public String getBsettlcurramt() {
		return bsettlcurramt;
	}

	public void setBsettlcurramt(String bsettlcurramt) {
		this.bsettlcurramt = bsettlcurramt;
	}

	public String getSsettlcurramt() {
		return ssettlcurramt;
	}

	public void setSsettlcurramt(String ssettlcurramt) {
		this.ssettlcurramt = ssettlcurramt;
	}

	public String getBsettlcurrency() {
		return bsettlcurrency;
	}

	public void setBsettlcurrency(String bsettlcurrency) {
		this.bsettlcurrency = bsettlcurrency;
	}

	public String getSsettlcurrency() {
		return ssettlcurrency;
	}

	public void setSsettlcurrency(String ssettlcurrency) {
		this.ssettlcurrency = ssettlcurrency;
	}

	public String getBsettlcurrfxrate() {
		return bsettlcurrfxrate;
	}

	public void setBsettlcurrfxrate(String bsettlcurrfxrate) {
		this.bsettlcurrfxrate = bsettlcurrfxrate;
	}

	public String getSsettlcurrfxrate() {
		return ssettlcurrfxrate;
	}

	public void setSsettlcurrfxrate(String ssettlcurrfxrate) {
		this.ssettlcurrfxrate = ssettlcurrfxrate;
	}

	public String getBdeliverytype() {
		return bdeliverytype;
	}

	public void setBdeliverytype(String bdeliverytype) {
		this.bdeliverytype = bdeliverytype;
	}

	public String getSdeliverytype() {
		return sdeliverytype;
	}

	public void setSdeliverytype(String sdeliverytype) {
		this.sdeliverytype = sdeliverytype;
	}

	public String getBclearingmethod() {
		return bclearingmethod;
	}

	public void setBclearingmethod(String bclearingmethod) {
		this.bclearingmethod = bclearingmethod;
	}

	public String getSclearingmethod() {
		return sclearingmethod;
	}

	public void setSclearingmethod(String sclearingmethod) {
		this.sclearingmethod = sclearingmethod;
	}

	public String getBsettltype() {
		return bsettltype;
	}

	public void setBsettltype(String bsettltype) {
		this.bsettltype = bsettltype;
	}

	public String getSsettltype() {
		return ssettltype;
	}

	public void setSsettltype(String ssettltype) {
		this.ssettltype = ssettltype;
	}

	public String getBsettldate() {
		return bsettldate;
	}

	public void setBsettldate(String bsettldate) {
		this.bsettldate = bsettldate;
	}

	public String getSsettldate() {
		return ssettldate;
	}

	public void setSsettldate(String ssettldate) {
		this.ssettldate = ssettldate;
	}

	public String getBtotalprincipal() {
		return btotalprincipal;
	}

	public void setBtotalprincipal(String btotalprincipal) {
		this.btotalprincipal = btotalprincipal;
	}

	public String getStotalprincipal() {
		return stotalprincipal;
	}

	public void setStotalprincipal(String stotalprincipal) {
		this.stotalprincipal = stotalprincipal;
	}

	public String getBleavesqty() {
		return bleavesqty;
	}

	public void setBleavesqty(String bleavesqty) {
		this.bleavesqty = bleavesqty;
	}

	public String getSleavesqty() {
		return sleavesqty;
	}

	public void setSleavesqty(String sleavesqty) {
		this.sleavesqty = sleavesqty;
	}

	@Override
	public QuoteBizTypeEnum getQuoteBizType() {
		return QuoteBizTypeEnum.BOND_MARKETMAKE;
	}

	@Override
	public boolean isSelfQuote() {
		return true;
	}
}
