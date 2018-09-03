package com.xquant.platform.component.mock.netty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.util.Assert;

import com.xquant.platform.component.itf.cfets.common.enums.MsgTypeEnum;
import com.xquant.platform.component.itf.cfets.xswap.api.enums.XSwapActionEnum;
import com.xquant.platform.component.mock.dto.MockMessageInfo;
import com.xquant.platform.component.mock.netty.event.MockMessageBroadCastSuccEvent;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import xquant.xswap.protocol.XSwapHeader;
import xquant.xswap.protocol.XSwapMessage;

/**
 * @author guanglai.zhou
 * @version 2017年11月13日 下午1:11:25 类说明 用于记录注册的用户 并 广播消息
 */
public class MyXswapLongLiveNettyServer implements SmartLifecycle, InitializingBean, ApplicationEventPublisherAware {

	private int port;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private boolean isRunning;
	private ChannelMatcher channelMatcher = new MyChannelMatcher();
	private ApplicationEventPublisher applicationEventPublisher;
	private Boolean isQuote;

	// 进行报价的发送 发送成功 则进行广播
	public void sendPushMessage(@SuppressWarnings("rawtypes") final MockMessageInfo message) {
		ChannelGroupFuture future = MyXswapChannelGroup.broadcast(message.getMessage(), channelMatcher);
				
		Set<Channel> registerChannels = MyXswapChannelGroup.getRegisterChannels();
		
		for (final Channel channel : registerChannels) {
			ChannelFuture channelFuture = future.find(channel);
			
			channelFuture.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isDone() && future.isSuccess()) {
						// 发送成功 进行事件的广播
						applicationEventPublisher.publishEvent(new MockMessageBroadCastSuccEvent(message,channel));
					}
				}
			});
			
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ChannelMatcher getChannelMatcher() {
		return channelMatcher;
	}

	public void setChannelMatcher(ChannelMatcher channelMatcher) {
		this.channelMatcher = channelMatcher;
	}

	public Boolean getIsQuote() {
		return isQuote;
	}

	public void setIsQuote(Boolean isQuote) {
		this.isQuote = isQuote;
	}

	// 开启服务
	public void runServer() throws Exception {

		System.out.println("=================xswap long live netty server start=================");
		// accepts an incoming connection
		this.bossGroup = new NioEventLoopGroup(1);
		this.workerGroup = new NioEventLoopGroup(1);
		// sets up a server.
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup)

				// The Class which is used to create Channel instances from.
				.channel(NioServerSocketChannel.class)

				// Set the ChannelHandler which is used to serve the request for the Channels.
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new MyXswapTradeMessageCodec());
						ch.pipeline().addLast(new NettyLongServerHandler());
					}
				});
		// Bind and start to accept incoming connections.
		b.bind(port).sync();
	}

	private class NettyLongServerHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			if (isQuote) {
				MyXswapChannelGroup.addChannel(ctx.channel());
			}
		}

		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception {
			if (isQuote) {
				MyXswapChannelGroup.removeChannel(ctx.channel());
			}
		}
		
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			// 读到两种类型的数据 一种为心跳 另一种为注册消息
			if (XSwapMessage.class.isInstance(msg)) {
				XSwapMessage tradeMessage = (XSwapMessage) msg;
				tradeMessage.getHeader().setAction(XSwapActionEnum.REGISTER_MESSAGE.getValue());
				tradeMessage.getHeader().setSerialNo(UUID.randomUUID().toString());
				tradeMessage.getHeader().setMsgType(MsgTypeEnum.ACK.getValue());
				tradeMessage.getHeader()
						.setSendingTime(new SimpleDateFormat("yyyyMMdd-HH:mm:ss.sss").format(new Date()));
				ctx.writeAndFlush(tradeMessage);
			} else {
				// 返回心跳消息
				XSwapMessage heartBeart = new XSwapMessage();
				heartBeart.setHeader(new XSwapHeader());
				heartBeart.getHeader().setAction(XSwapActionEnum.HEARTBEAT_MESSAGE.getValue());
				ctx.writeAndFlush(heartBeart);
			}
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			cause.printStackTrace();
			ctx.close();
		}
	}

	public void stop() {
		Future<?> shutdownFuture1 = this.bossGroup.shutdownGracefully();
		Future<?> shutdownFuture2 = this.workerGroup.shutdownGracefully();
		try {
			shutdownFuture1.get();
			shutdownFuture2.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public int getPhase() {
		return 0;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(channelMatcher, "channelMatcher can not be null");
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		isRunning = false;
		stop();
		callback.run();
	}

	@Override
	public void start() {
		try {
			runServer();
			isRunning = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

}
