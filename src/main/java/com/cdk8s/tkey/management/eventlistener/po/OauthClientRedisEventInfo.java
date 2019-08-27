package com.cdk8s.tkey.management.eventlistener.po;

import com.cdk8s.tkey.management.pojo.entity.OauthClient;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OauthClientRedisEventInfo implements Serializable {

	private static final long serialVersionUID = 4768780299364601364L;

	private List<OauthClient> oauthClientListByCreate;
	private List<OauthClient> oauthClientListByDelete;

}
