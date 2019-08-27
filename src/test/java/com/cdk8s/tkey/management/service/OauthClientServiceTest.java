package com.cdk8s.tkey.management.service;

import com.cdk8s.tkey.management.enums.StateEnum;
import com.cdk8s.tkey.management.pojo.dto.response.OauthClientDTO;
import com.cdk8s.tkey.management.pojo.dto.param.OauthClientPageQueryParam;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OauthClientServiceTest {

	@Autowired
	private OauthClientService oauthClientService;

	//=================================================================================

	@Test
	public void findByPage() {
		OauthClientPageQueryParam queryParam = new OauthClientPageQueryParam();
		queryParam.setStateEnum(StateEnum.ENABLE.getCode());
		queryParam.setPageNum(1);
		queryParam.setPageSize(20);

		PageInfo<OauthClientDTO> page = oauthClientService.findPageByParam(queryParam);
		assertThat(page.getList().size(), greaterThan(0));
	}
}
