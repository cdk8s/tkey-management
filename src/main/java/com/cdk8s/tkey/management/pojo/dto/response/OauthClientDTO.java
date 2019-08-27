package com.cdk8s.tkey.management.pojo.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OauthClientDTO {
	private Long id;
	private String clientName;
	private String clientId;
	private String clientSecret;
	private String clientUrl;
	private String clientDesc;
	private String logoUrl;
	private Integer ranking;
	private String remark;
	private Integer stateEnum;
	private Integer deleteEnum;
	private Long createDate;
	private Long createUserId;
	private Long updateDate;
	private Long updateUserId;
}
