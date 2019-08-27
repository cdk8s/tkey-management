package com.cdk8s.tkey.management.mapper;

import com.cdk8s.tkey.management.pojo.entity.OauthClient;
import com.cdk8s.tkey.management.util.DatetimeUtil;
import com.cdk8s.tkey.management.util.id.GenerateIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OauthClientMapperTest {



	@Autowired
	private OauthClientMapper oauthClientMapper;

	//=================================================================================

	@Test
	public void selectAll() {
		List<OauthClient> oauthClientList = oauthClientMapper.selectAll();
		assertThat(oauthClientList.size(), greaterThan(0));
	}

	@Test
	public void insert() {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		OauthClient oauthClient = new OauthClient();
		oauthClient.setId(GenerateIdUtil.getId());
		oauthClient.setClientName("TKey客户端管理系统");
		oauthClient.setClientId("client_management");
		oauthClient.setClientSecret("a8V0t0f7LusVZghwY3qDYMzcJ6RpSg3G");
		oauthClient.setClientUrl("^(http|https)://.*");
		oauthClient.setClientDesc("TKey客户端管理系统");
		oauthClient.setLogoUrl("https://www.easyicon.net/api/resizeApi.php?id=1200686&size=32");
		oauthClient.setRanking(100);
		oauthClient.setRemark("这是备注");
		oauthClient.setStateEnum(1);
		oauthClient.setDeleteEnum(1);
		oauthClient.setCreateDate(currentEpochMilli);
		oauthClient.setCreateUserId(123456L);
		oauthClient.setUpdateDate(currentEpochMilli);
		oauthClient.setUpdateUserId(123456L);

		int result = oauthClientMapper.insert(oauthClient);
		assertThat(result, greaterThan(0));
	}

}
