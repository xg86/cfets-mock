package com.xquant.platform.component.mock.netty;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.SmartLifecycle;

import com.xquant.cfets.trade.protocol.util.CfetsTradeSerializeUtil;
import com.xquant.platform.component.itf.cfets.xswap.api.enums.XSwapActionEnum;
import com.xquant.platform.component.itf.cfets.xswap.skeleton.transport.XswapProtocolEntitySerializeUtil;
import com.xquant.platform.component.mock.netty.event.XswapShortLiveResponseEvent;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.ResourceUtil4Xswap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;
import xquant.xswap.protocol.XSwapHeader;
import xquant.xswap.protocol.XSwapMessage;

/**
 * @author guanglai.zhou
 * @version 2017年11月13日 下午1:11:25 类说明
 */
public class MyXswapShortLiveNettyServer implements SmartLifecycle, InitializingBean, ApplicationEventPublisherAware {

	private int port;
	private XswapRequestHandler requestHandler;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private boolean isRunning;
	private ChannelGroup channelGroup;
	private ApplicationEventPublisher applicationEventPublisher;

	public MyXswapShortLiveNettyServer() {

	}

	public MyXswapShortLiveNettyServer(int port) {
		this.port = port;
		this.requestHandler = new MyRequestHandler4XswapProtocol();
	}

	public MyXswapShortLiveNettyServer(XswapRequestHandler handler, int port) {
		this.port = port;
		this.requestHandler = handler;
	}

	public interface XswapRequestHandler {
		XSwapMessage handleRequest(XSwapMessage request);
	}

	public void sendPushMessage(XSwapMessage message) {
		ChannelGroupFuture future = channelGroup
				.writeAndFlush(Unpooled.wrappedBuffer(XswapProtocolEntitySerializeUtil.serialize(message)));
		try {
			future.sync();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void mystart() throws Exception {

		System.out.println("=================xswap short live netty server start=================");
		// accepts an incoming connection
		this.bossGroup = new NioEventLoopGroup(1);
		this.workerGroup = new NioEventLoopGroup(1);

		this.channelGroup = new DefaultChannelGroup("CHANNEL-GRUOP", GlobalEventExecutor.INSTANCE);

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
						ch.pipeline().addLast(new XswapNettyServerHandler());
					}
				});

		// Bind and start to accept incoming connections.
		b.bind(port).sync();
	}
	
	private Logger logger = LoggerFactory.getLogger(MyXswapShortLiveNettyServer.class);

	private class XswapNettyServerHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

			// 接收报价
			if (XSwapMessage.class.isInstance(msg)) {
				XSwapMessage tradeMessage = (XSwapMessage) msg;
				
				logger.info("received xswap request message \n+" + CfetsTradeSerializeUtil.toXml(tradeMessage));
				
				XSwapMessage reqBack = null;
				if (XSwapActionEnum.ORDER_CREATE.getValue().equals(tradeMessage.getHeader().getAction())) {
					reqBack = ResourceUtil4Xswap.getXSwapMessage4AddAck();
				} else if (XSwapActionEnum.ORDER_CANCEL.getValue().equals(tradeMessage.getHeader().getAction())) {
					reqBack = ResourceUtil4Xswap.getXSwapMessage4CancelAck();
				}
				
				final XSwapMessage message = (XSwapMessage) MyXswapShortLiveNettyServer.this.requestHandler
						.handleRequest(tradeMessage);
				if (message != null && reqBack != null) {
					// 同步返回都只是简单的头信息
					reqBack.getHeader().setSendingTime(MockDateTimeUtil.getTransactimeOfNow());
					reqBack.getHeader().setClientID(tradeMessage.getHeader().getClientID());
					reqBack.getHeader().setSerialNo(tradeMessage.getHeader().getSerialNo());
					ChannelFuture future = ctx.writeAndFlush(reqBack);
					
					logger.info("send xswap synchronous message \n+" + CfetsTradeSerializeUtil.toXml(reqBack));
					
					future.addListener(new ChannelFutureListener() {
						@Override
						public void operationComplete(ChannelFuture future) throws Exception {
							if (future.isDone() && future.isSuccess()) {
								applicationEventPublisher.publishEvent(new XswapShortLiveResponseEvent(message));
							}
						}
					});
				}
				// 接收心跳
			} else {
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
			mystart();
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
