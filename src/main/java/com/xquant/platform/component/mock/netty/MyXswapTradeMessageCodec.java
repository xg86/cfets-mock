package com.xquant.platform.component.mock.netty;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xquant.platform.component.itf.cfets.transport.netty.CfetsProtocolVersions;
import com.xquant.platform.component.itf.cfets.xswap.api.enums.XSwapActionEnum;
import com.xquant.platform.component.itf.cfets.xswap.skeleton.transport.XswapHeartBeatMessageBuilder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import xquant.xswap.protocol.XSwapMessage;
import xquant.xswap.util.XSwapSerializeUtil;

/**
 * 负责进行XSwapMessage的编码和解码工作 netty中用二进制进行传输
 * 
 * @author Administrator
 *
 */
public class MyXswapTradeMessageCodec extends ByteToMessageCodec<XSwapMessage> {

	Logger logger = LoggerFactory.getLogger(MyXswapTradeMessageCodec.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, XSwapMessage message, ByteBuf out) throws Exception {
		try {
			if (XSwapActionEnum.HEARTBEAT_MESSAGE.getValue().equals(message.getHeader().getAction())) {
				byte[] heartBeat = new XswapHeartBeatMessageBuilder().getHeartBeatMsgContent();
				out.writeBytes(heartBeat);
				return;
			}

			byte[] bodyByteArray;
			try {
				bodyByteArray = XSwapSerializeUtil.toProtobuf(message);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			ByteBuf byteBuf = Unpooled.buffer();
			byteBuf.writeIntLE(CfetsProtocolVersions.VERSION);
			byteBuf.writeIntLE(bodyByteArray.length);
			byteBuf.writeIntLE(Integer.valueOf(message.getHeader().getAction()));
			byte[] headByteArray = new byte[12];
			byteBuf.readBytes(headByteArray);
			out.writeBytes(ArrayUtils.addAll(headByteArray, bodyByteArray));
		} catch (Exception e) {
			logger.error("MyXswapTradeMessageCodec exception", e);
		}
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		try {
			if (in.readableBytes() < 12) {
				return;
			}
			int readerIndex = in.readerIndex();
			@SuppressWarnings("unused")
			int pVersion = in.readIntLE();
			int packLen = in.readIntLE();
			int action = in.readIntLE();
			if (packLen == 0 && XSwapActionEnum.HEARTBEAT_MESSAGE.getValue().equals(String.valueOf(action))) {
				// 处理心跳消息
				out.add(in);
				return;
			}
			if (in.readableBytes() < packLen) {
				in.readerIndex(readerIndex);
				return;
			}
			byte[] bodyByteArray = new byte[packLen];
			in.readBytes(bodyByteArray);
			// 对于客户端只解析客户端的请求信息
			Object fromProtobuf = XSwapSerializeUtil.fromProtobuf(MyXswapReqActionTypeRegistry.getTypeViaAction(action),
					bodyByteArray);
			out.add(fromProtobuf);
		} catch (Exception e) {
			logger.error("MyXswapTradeMessageCodec exception", e);
		}
	}
}
