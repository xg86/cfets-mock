package com.xquant.platform.component.mock.holder;

import com.xquant.platform.component.itf.cfets.transport.netty.NettyLongLiveClient;

public class NettyLongLiveClientHolder {
	
	public static NettyLongLiveClient  nettyLongLiveClient;
	
	public static void setClient(NettyLongLiveClient  client) {
		nettyLongLiveClient = client;
	}

	public static NettyLongLiveClient getClient() {
		return nettyLongLiveClient;
	}
}
