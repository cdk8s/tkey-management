package com.cdk8s.tkey.management.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * hibernate.validator.fail_fast
 * true  快速失败返回模式    false 普通模式
 * 遇到第一个错误就返回
 * https://docs.jboss.org/hibernate/validator/4.2/reference/en-US/html/validator-specifics.html
 */
@Configuration
public class ValidatorConfig {

	@Bean
	public Validator validator() {
		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
				.configure()
				.addProperty("hibernate.validator.fail_fast", "true")
				.buildValidatorFactory();
		return validatorFactory.getValidator();
	}
}
