package com.xquant.platform.component.mock.netty;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelMatcher;

public class MyChannelMatcher implements ChannelMatcher {

	@Override
	public boolean matches(Channel channel) {
		return true;
	}

}
