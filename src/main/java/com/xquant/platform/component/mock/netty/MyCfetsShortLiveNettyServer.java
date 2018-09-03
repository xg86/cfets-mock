package com.xquant.platform.component.mock.netty;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.SmartLifecycle;

import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessageHeader;
import com.xquant.cfets.trade.protocol.util.CfetsTradeSerializeUtil;
import com.xquant.platform.component.itf.cfets.skeleton.enums.common.ActionCodeEnum;
import com.xquant.platform.component.mock.netty.event.CfetsShortLiveResponseEvent;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;

/**
 * @author guanglai.zhou
 * @version 2017年11月13日 下午1:11:25 类说明 用于接收客户端发送的报价 然后同步返回
 */
public class MyCfetsShortLiveNettyServer implements SmartLifecycle, InitializingBean, ApplicationEventPublisherAware {

	private int port;
	private CfetsRequestHandler requestHandler;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private boolean isRunning;
	private ApplicationEventPublisher applicationEventPublisher;

	public MyCfetsShortLiveNettyServer() {

	}

	public MyCfetsShortLiveNettyServer(int port) {
		this.port = port;
		this.requestHandler = new MyRequestHandler4CfetsProtocol();
	}

	public MyCfetsShortLiveNettyServer(CfetsRequestHandler handler, int port) {
		this.port = port;
		this.requestHandler = handler;
	}

	public interface CfetsRequestHandler {
		CfetsTradeMessage handleRequest(CfetsTradeMessage request);
	}

	public static class DefaultRequestHandler implements CfetsRequestHandler {

		@Override
		public CfetsTradeMessage handleRequest(CfetsTradeMessage request) {
			return request;
		}
	}

	public void mystart() throws Exception {

		System.out.println("=================cfets short live netty server start=================");
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
						ch.pipeline().addLast(new MyCfetsTradeMessageCodec());
						ch.pipeline().addLast(new NettyShortLiveServerHandler());
					}
				});

		// Bind and start to accept incoming connections.
		b.bind(port).sync();
	}

	private Logger logger = LoggerFactory.getLogger(MyCfetsShortLiveNettyServer.class);

	private class NettyShortLiveServerHandler extends ChannelInboundHandlerAdapter {

		// 接收同步消息 返回同步消息 如果返回成功 则进行广播
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

			// 接收报价
			if (CfetsTradeMessage.class.isInstance(msg)) {
				CfetsTradeMessage tradeMessage = (CfetsTradeMessage) msg;

				logger.info("received cfets request message \n+" + CfetsTradeSerializeUtil.toXml(tradeMessage));

				final CfetsTradeMessage message = (CfetsTradeMessage) MyCfetsShortLiveNettyServer.this.requestHandler
						.handleRequest(tradeMessage);
				if (message != null) {
					ChannelFuture future = ctx.writeAndFlush(message);

					future.addListener(new ChannelFutureListener() {
						@Override
						public void operationComplete(ChannelFuture future) throws Exception {
							if (future.isDone() && future.isSuccess()) {
								applicationEventPublisher.publishEvent(new CfetsShortLiveResponseEvent(message));
							}
						}
					});
				}
				// 接收心跳
			} else {
				CfetsTradeMessage heartBeart = new CfetsTradeMessage();
				heartBeart.setHeader(new CfetsTradeMessageHeader());
				heartBeart.getHeader().setAction(ActionCodeEnum.HEART_BEAT.getValue());
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
