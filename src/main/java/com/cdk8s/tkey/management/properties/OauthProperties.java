package com.cdk8s.tkey.management.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix = "custom.properties.oauth")
public class OauthProperties {


	private List<String> acceptUsernameList;
	private Boolean demoMode = false;
	private Boolean loginInterceptorCheckEnable = true;
	private Long tokenMaxTimeToLiveInSeconds = 86400L;

}
