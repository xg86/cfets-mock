package com.xquant.platform.component.mock.netty;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;
import com.xquant.cfets.trade.protocol.util.CfetsTradeSerializeUtil;
import com.xquant.platform.component.itf.cfets.skeleton.enums.common.ActionCodeEnum;
import com.xquant.platform.component.itf.cfets.skeleton.transport.CfetsHeartBeatMessageBuilder;
import com.xquant.platform.component.itf.cfets.transport.netty.CfetsProtocolVersions;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

/**
 * 负责进行CfetsTradeMessage的编码和解码工作 netty中用二进制进行传输
 * 
 * @author Administrator
 *
 */
public class MyCfetsTradeMessageCodec extends ByteToMessageCodec<CfetsTradeMessage> {

	Logger logger = LoggerFactory.getLogger(MyCfetsTradeMessageCodec.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, CfetsTradeMessage message, ByteBuf out) throws Exception {
		try {
			if (ActionCodeEnum.HEART_BEAT.getValue().equals(message.getHeader().getAction())) {
				byte[] heartBeat = new CfetsHeartBeatMessageBuilder().getHeartBeatMsgContent();
				out.writeBytes(heartBeat);
				return;
			}

			byte[] bodyByteArray;
			try {
				bodyByteArray = CfetsTradeSerializeUtil.toProtobuf(message);
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
			logger.error("MyCfetsTradeMessageCodec exception", e);
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
			if (packLen == 0 && ActionCodeEnum.HEART_BEAT.getValue().equals(String.valueOf(action))) {
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
			Object fromProtobuf = CfetsTradeSerializeUtil
					.fromProtobuf(MyCfetsReqActionTypeRegistry.getTypeViaAction(action), bodyByteArray);
			out.add(fromProtobuf);
		} catch (Exception e) {
			logger.error("MyCfetsTradeMessageCodec exception", e);
		}
	}
}
