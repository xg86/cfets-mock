package com.xquant.platform.component.mock.resolver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.mock.dto.ResolverMessageInfo;

@Component
public class MessageResolverManager {

	private Logger logger = LoggerFactory.getLogger(MessageResolverManager.class);
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private List<IMessageResolver> messageResolvers;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void resolveOptypeAndFillTempIfNull(ResolverMessageInfo resolverMessageInfo) {
		try {
			// 用于判断报价类型的实体 如果有模板 用模板 没有模板 用原报价信息
			Object usedMessage = resolverMessageInfo.getMessageTempldate() != null
					? resolverMessageInfo.getMessageTempldate()
					: resolverMessageInfo.getOriginMessage();
			IMessageResolver messageResolver = getMessageResolver(resolverMessageInfo);
			String key = messageResolver.generateUniqueKey(usedMessage);
			OpTypeEnum opTypeEnum = messageResolver.getOptypeWithKey(key);
			if (opTypeEnum == null) {
				throw new RuntimeException("通过消息对象类型无法获取报价类型,key = " + key);
			}
			if (resolverMessageInfo.getMessageTempldate() == null) {
				resolverMessageInfo.setMessageTempldate(messageResolver.getMessageWithKey(key));
			}
			resolverMessageInfo.setOptype(opTypeEnum);
			Assert.notNull(resolverMessageInfo.getMessageTempldate(), "messageTemplate can not be null");
			Assert.notNull(resolverMessageInfo.getOptype(), "optype can not be null");
		} catch (Exception e) {
			logger.error("MessageResolverManager resolveOptypeAndFillTempIfNull Exception ", e);
		}
	}

	@SuppressWarnings("rawtypes")
	private IMessageResolver getMessageResolver(ResolverMessageInfo resolverMessageInfo) {
		for (IMessageResolver iMessageResolver : messageResolvers) {
			if (iMessageResolver.supportSys().equals(resolverMessageInfo.getTargetSys())) {
				return iMessageResolver;
			}
		}
		throw new RuntimeException("根据当前的目标系统类型找不到消息的解析器" + resolverMessageInfo.getTargetSys());
	}
}
