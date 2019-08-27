package com.cdk8s.tkey.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Configuration
@EnableSwagger2
@Profile({"dev", "devmysql", "test"})
public class Swagger2Config {

	@Bean
	public Docket commonControllerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("TKey SSO Client Management")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.cdk8s.tkey.management.controller"))
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.directModelSubstitute(LocalDate.class, Long.class)
				.directModelSubstitute(ZonedDateTime.class, Long.class)
				.directModelSubstitute(LocalDateTime.class, Long.class)
				.directModelSubstitute(LocalDateTime.class, Long.class)
				.directModelSubstitute(Date.class, Long.class)
				.genericModelSubstitutes(ResponseEntity.class);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("TKey SSO Client Management API")
				.description("TKey SSO Client Management API")
				.termsOfServiceUrl("https://github.com/cdk8s")
				.version("1.0.0")
				.build();
	}

}
