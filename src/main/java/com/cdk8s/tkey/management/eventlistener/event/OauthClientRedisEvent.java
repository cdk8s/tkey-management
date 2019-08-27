package com.cdk8s.tkey.management.eventlistener.event;

import com.cdk8s.tkey.management.eventlistener.po.OauthClientRedisEventInfo;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OauthClientRedisEvent extends ApplicationEvent {

	private OauthClientRedisEventInfo oauthClientRedisEventInfo;


	public OauthClientRedisEvent(Object source, OauthClientRedisEventInfo oauthClientRedisEventInfo) {
		super(source);
		this.oauthClientRedisEventInfo = oauthClientRedisEventInfo;
	}
}
