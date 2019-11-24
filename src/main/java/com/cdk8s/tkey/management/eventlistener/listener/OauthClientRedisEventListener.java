package com.cdk8s.tkey.management.eventlistener.listener;


import com.cdk8s.tkey.management.constant.GlobalVariable;
import com.cdk8s.tkey.management.eventlistener.event.OauthClientRedisEvent;
import com.cdk8s.tkey.management.eventlistener.po.OauthClientRedisEventInfo;
import com.cdk8s.tkey.management.mapstruct.OauthClientMapstruct;
import com.cdk8s.tkey.management.pojo.bo.cache.OauthClientToRedisBO;
import com.cdk8s.tkey.management.pojo.entity.OauthClient;
import com.cdk8s.tkey.management.util.CollectionUtil;
import com.cdk8s.tkey.management.util.JsonUtil;
import com.cdk8s.tkey.management.util.redis.StringRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class OauthClientRedisEventListener {

	@Autowired
	private OauthClientMapstruct oauthClientMapstruct;

	/**
	 * 这里必须采用 value 为 String 类型，不然 客户端和服务端都要读取该信息，但是又没有共同的类进行序列化，所以必须转换成 JSON 字符串
	 * 如果后面出现的公共类越来越多我再考虑独立出一个 jar 包出来维护
	 */
	@Autowired
	private StringRedisService<String, String> clientRedisService;

	//=====================================业务处理 start=====================================


	@Async
	@EventListener
	public void register(OauthClientRedisEvent oauthClientRedisEvent) {
		OauthClientRedisEventInfo oauthClientRedisEventInfo = oauthClientRedisEvent.getOauthClientRedisEventInfo();
		List<OauthClient> oauthClientListByCreate = oauthClientRedisEventInfo.getOauthClientListByCreate();
		List<OauthClient> oauthClientListByDelete = oauthClientRedisEventInfo.getOauthClientListByDelete();

		if (CollectionUtil.isNotEmpty(oauthClientListByCreate)) {
			List<OauthClientToRedisBO> oauthClientToRedisBOList = oauthClientMapstruct.toRedisBOList(oauthClientListByCreate);
			for (OauthClientToRedisBO oauthClientToRedisBO : oauthClientToRedisBOList) {
				clientRedisService.set(GlobalVariable.REDIS_CLIENT_ID_KEY_PREFIX + oauthClientToRedisBO.getClientId(), JsonUtil.toJson(oauthClientToRedisBO));
			}
		}

		if (CollectionUtil.isNotEmpty(oauthClientListByDelete)) {
			List<String> keyList = new ArrayList<>();
			for (OauthClient oauthClient : oauthClientListByDelete) {
				keyList.add(GlobalVariable.REDIS_CLIENT_ID_KEY_PREFIX + oauthClient.getClientId());
			}
			clientRedisService.deleteByKeys(keyList);
		}

	}

	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================

	//=====================================私有方法  end=====================================
}
