package com.cdk8s.tkey.management.config;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomTomcatConfig {

	/**
	 * https://docs.spring.io/spring-boot/docs/current/reference/html/howto-embedded-web-servers.html#howto-use-tomcat-legacycookieprocessor
	 */
	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
		return (factory) -> factory
				.addContextCustomizers((context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
	}

}
