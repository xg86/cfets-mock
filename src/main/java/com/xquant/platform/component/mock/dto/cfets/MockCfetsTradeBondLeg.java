/**
 * 
 */
package com.xquant.platform.component.mock.dto.cfets;

import com.xquant.cfets.trade.protocol.common.CfetsTradeBondLeg;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.dto.cfets
 * @author: guanglai.zhou
 * @date: 2018-08-18 20:58:37
 */
public class MockCfetsTradeBondLeg extends CfetsTradeBondLeg {

	private String legDirtyprice;
	private String legAccruedinterestamt;
	private String legTradecashamt;
	private String legAccruedinteresttotalamt;
	private String legSettlcurramt;
	private String legSettldate;
	private String legTotalprincipal;
	private String legLeavesqty;

	public String getLegDirtyprice() {
		return legDirtyprice;
	}

	public void setLegDirtyprice(String legDirtyprice) {
		this.legDirtyprice = legDirtyprice;
	}

	public String getLegAccruedinterestamt() {
		return legAccruedinterestamt;
	}

	public void setLegAccruedinterestamt(String legAccruedinterestamt) {
		this.legAccruedinterestamt = legAccruedinterestamt;
	}

	public String getLegTradecashamt() {
		return legTradecashamt;
	}

	public void setLegTradecashamt(String legTradecashamt) {
		this.legTradecashamt = legTradecashamt;
	}

	public String getLegAccruedinteresttotalamt() {
		return legAccruedinteresttotalamt;
	}

	public void setLegAccruedinteresttotalamt(String legAccruedinteresttotalamt) {
		this.legAccruedinteresttotalamt = legAccruedinteresttotalamt;
	}

	public String getLegSettlcurramt() {
		return legSettlcurramt;
	}

	public void setLegSettlcurramt(String legSettlcurramt) {
		this.legSettlcurramt = legSettlcurramt;
	}

	public String getLegSettldate() {
		return legSettldate;
	}

	public void setLegSettldate(String legSettldate) {
		this.legSettldate = legSettldate;
	}

	public String getLegTotalprincipal() {
		return legTotalprincipal;
	}

	public void setLegTotalprincipal(String legTotalprincipal) {
		this.legTotalprincipal = legTotalprincipal;
	}

	public String getLegLeavesqty() {
		return legLeavesqty;
	}

	public void setLegLeavesqty(String legLeavesqty) {
		this.legLeavesqty = legLeavesqty;
	}

}
