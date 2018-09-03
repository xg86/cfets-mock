package com.xquant.platform.component.mock.netty;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xquant.platform.component.mock.registry.SheduledTaskRegister;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyCfetsChannelGroup {

	private static Logger logger = LoggerFactory.getLogger(MyCfetsChannelGroup.class);

	private static ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup("CFETS_CHANNEL_GROUP",
			GlobalEventExecutor.INSTANCE);

	/**
	 * 添加一个channel
	 * 
	 * @param channel
	 */
	public static void addChannel(Channel channel) {
		if (channel != null) {
			boolean flag = CHANNEL_GROUP.add(channel);
			if (flag) {
				if (logger.isInfoEnabled()) {
					logger.info("add channel success " + channel.remoteAddress().toString());
					logger.info("ChannelGroup size = " + CHANNEL_GROUP.size());
				}
			}
		}
	}

	/**
	 * 移除一个channel
	 * 
	 * @param channel
	 */
	public static void removeChannel(Channel channel) {
		if (channel != null && CHANNEL_GROUP.contains(channel)) {
			boolean flag = CHANNEL_GROUP.remove(channel);
			if (flag) {
				if (logger.isInfoEnabled()) {
					logger.info("remove channel success " + channel.remoteAddress().toString());
					logger.info("ChannelGroup size = " + CHANNEL_GROUP.size());
					if (CHANNEL_GROUP.size() == 0) {
						SheduledTaskRegister.removeAllTask();
					}
				}
			}
		}
	}

	/**
	 * 获取所有注册的channel
	 * 
	 * @return
	 */
	public static Set<Channel> getRegisterChannels() {
		return CHANNEL_GROUP;
	}

	/**
	 * 进行消息的广播
	 * 
	 * @param message
	 * @param matcher
	 * @return
	 */
	public static ChannelGroupFuture broadcast(Object message, ChannelMatcher matcher) {
		return CHANNEL_GROUP.writeAndFlush(message, matcher);
	}

	/**
	 * 获取当前连接的客户端个数
	 * 
	 * @return
	 */
	public static int getChannelCount() {
		return CHANNEL_GROUP.size();
	}

}
