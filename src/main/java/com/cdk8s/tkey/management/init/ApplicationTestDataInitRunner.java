package com.cdk8s.tkey.management.init;

import com.cdk8s.tkey.management.service.OauthClientService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Slf4j
@Profile({"dev", "devmysql", "gatling", "test"})
@Component
public class ApplicationTestDataInitRunner implements ApplicationRunner {

	@Autowired
	private OauthClientService oauthClientService;

	//=====================================业务处理 start=====================================

	@SneakyThrows
	@Override
	public void run(ApplicationArguments args) {
		log.info("=================================预设 Redis 测试数据 Start=================================");
		oauthClientService.initAllClientToRedis();
		log.info("=================================预设 Redis 测试数据 End=================================");
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	//=====================================私有方法  end=====================================
}
