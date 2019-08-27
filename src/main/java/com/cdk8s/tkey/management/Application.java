package com.cdk8s.tkey.management;

import com.cdk8s.tkey.client.rest.EnableTkeySso;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTkeySso
@EnableTransactionManagement
@EnableAsync
@ServletComponentScan
@tk.mybatis.spring.annotation.MapperScan(basePackages = {"com.cdk8s.tkey.management.mapper"})
@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Value("${server.port:9091}")
	private String serverPort;

	@Value("${server.servlet.context-path:/demo}")
	private String serverContextPath;

	@Value("${management.server.servlet.context-path:/tkey-actuator}")
	private String managementContextPath;

	@Value("${management.server.port:19091}")
	private String managementPort;

	//=================================================================================

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... strings) {
		log.info("=================================Application Startup Success=================================");
		log.info("index >> http://management.cdk8s.com:{}{}", serverPort, serverContextPath);
		log.info("client detail >> http://management.cdk8s.com:{}{}/client/detail?id=598661060102193152", serverPort, serverContextPath);
		log.info("swagger >> http://management.cdk8s.com:{}{}/swagger-ui.html", serverPort, serverContextPath);
		log.info("actuator-health >> http://management.cdk8s.com:{}{}/actuator/health", managementPort, managementContextPath);
		log.info("=================================Application Startup Success=================================");
	}


}
