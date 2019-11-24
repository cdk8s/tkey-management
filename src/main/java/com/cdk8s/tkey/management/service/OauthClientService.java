package com.cdk8s.tkey.management.service;

import com.cdk8s.tkey.management.constant.GlobalVariable;
import com.cdk8s.tkey.management.enums.DeleteEnum;
import com.cdk8s.tkey.management.enums.StateEnum;
import com.cdk8s.tkey.management.eventlistener.event.OauthClientRedisEvent;
import com.cdk8s.tkey.management.eventlistener.po.OauthClientRedisEventInfo;
import com.cdk8s.tkey.management.exception.OauthApiException;
import com.cdk8s.tkey.management.mapper.OauthClientMapper;
import com.cdk8s.tkey.management.mapstruct.OauthClientMapstruct;
import com.cdk8s.tkey.management.pojo.bo.cache.OauthClientToRedisBO;
import com.cdk8s.tkey.management.pojo.dto.param.OauthClientPageQueryParam;
import com.cdk8s.tkey.management.pojo.dto.response.OauthClientDTO;
import com.cdk8s.tkey.management.pojo.entity.OauthClient;
import com.cdk8s.tkey.management.util.CollectionUtil;
import com.cdk8s.tkey.management.util.DatetimeUtil;
import com.cdk8s.tkey.management.util.JsonUtil;
import com.cdk8s.tkey.management.util.StringUtil;
import com.cdk8s.tkey.management.util.id.GenerateIdUtil;
import com.cdk8s.tkey.management.util.redis.StringRedisService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
public class OauthClientService {

	@Autowired
	private OauthClientMapper oauthClientMapper;

	@Autowired
	private OauthClientMapstruct oauthClientMapstruct;

	@Autowired
	private StringRedisService<String, String> clientRedisService;

	@Autowired
	private ApplicationContext applicationContext;

	//=====================================业务处理 start=====================================

	public OauthClientToRedisBO findOneByClientId(String clientId) {
		String clientIdRedisKey = GlobalVariable.REDIS_CLIENT_ID_KEY_PREFIX + clientId;
		String result = clientRedisService.get(clientIdRedisKey);
		if (StringUtil.isBlank(result)) {
			return null;
		}
		return JsonUtil.toObject(result, OauthClientToRedisBO.class);
	}

	public OauthClient findOneById(Long id) {
		return oauthClientMapper.selectByPrimaryKey(id);
	}

	public List<OauthClient> findListByIdList(List<Long> idList) {
		return oauthClientMapper.selectByIdIn(idList);
	}

	public PageInfo findPageByParam(OauthClientPageQueryParam param) {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<OauthClient> oauthClientList = oauthClientMapper.selectByPageQueryParam(param);

		List<OauthClientDTO> oauthClientDTOList = oauthClientMapstruct.toDTOList(oauthClientList);
		return new PageInfo(oauthClientDTOList);
	}

	@Transactional
	public void create(OauthClient oauthClient) {
		OauthClientToRedisBO clientByClientId = findOneByClientId(oauthClient.getClientId());
		if (null != clientByClientId) {
			throw new OauthApiException("clientId 已存在");
		}

		initCreateBasicParam(oauthClient);
		oauthClientMapper.insert(oauthClient);

		OauthClientRedisEventInfo oauthClientRedisEventInfo = new OauthClientRedisEventInfo();
		oauthClientRedisEventInfo.setOauthClientListByCreate(Lists.newArrayList(oauthClient));
		applicationContext.publishEvent(new OauthClientRedisEvent(this, oauthClientRedisEventInfo));
	}

	@Transactional
	public void update(OauthClient oauthClient) {
		OauthClientToRedisBO clientByClientId = findOneByClientId(oauthClient.getClientId());
		if (null != clientByClientId && !clientByClientId.getId().equals(oauthClient.getId())) {
			throw new OauthApiException("clientId 已存在");
		}

		oauthClientMapper.updateByPrimaryKeySelective(oauthClient);

		OauthClientRedisEventInfo oauthClientRedisEventInfo = new OauthClientRedisEventInfo();
		oauthClientRedisEventInfo.setOauthClientListByCreate(Lists.newArrayList(oauthClient));
		applicationContext.publishEvent(new OauthClientRedisEvent(this, oauthClientRedisEventInfo));
	}

	@Transactional
	public void batchDelete(List<Long> idList) {
		oauthClientMapper.updateDeleteEnumByIdList(DeleteEnum.DELETED.getCode(), idList);

		List<OauthClient> listByIdList = findListByIdList(idList);
		if (CollectionUtil.isEmpty(listByIdList)) {
			return;
		}

		OauthClientRedisEventInfo oauthClientRedisEventInfo = new OauthClientRedisEventInfo();
		oauthClientRedisEventInfo.setOauthClientListByDelete(listByIdList);
		applicationContext.publishEvent(new OauthClientRedisEvent(this, oauthClientRedisEventInfo));
	}

	@Transactional
	public void batchUpdateState(Integer stateEnum, List<Long> idList) {
		oauthClientMapper.updateStateEnumByIdList(stateEnum, idList);

		List<OauthClient> listByIdList = findListByIdList(idList);
		if (CollectionUtil.isEmpty(listByIdList)) {
			return;
		}

		OauthClientRedisEventInfo oauthClientRedisEventInfo = new OauthClientRedisEventInfo();
		if (StateEnum.ENABLE.getCode() == stateEnum) {
			oauthClientRedisEventInfo.setOauthClientListByCreate(listByIdList);
		} else {
			oauthClientRedisEventInfo.setOauthClientListByDelete(listByIdList);
		}

		applicationContext.publishEvent(new OauthClientRedisEvent(this, oauthClientRedisEventInfo));
	}

	public void initAllClientToRedis() {
		List<OauthClient> oauthClientList = oauthClientMapper.selectAll();
		OauthClientRedisEventInfo oauthClientRedisEventInfo = new OauthClientRedisEventInfo();
		oauthClientRedisEventInfo.setOauthClientListByCreate(oauthClientList);
		applicationContext.publishEvent(new OauthClientRedisEvent(this, oauthClientRedisEventInfo));
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	private void initCreateBasicParam(OauthClient oauthClient) {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		oauthClient.setId(GenerateIdUtil.getId());
		if (null == oauthClient.getStateEnum()) {
			oauthClient.setStateEnum(StateEnum.ENABLE.getCode());
		}
		oauthClient.setDeleteEnum(DeleteEnum.NOT_DELETE.getCode());
		oauthClient.setCreateDate(currentEpochMilli);
		oauthClient.setCreateUserId(currentEpochMilli);
		oauthClient.setUpdateDate(currentEpochMilli);
		oauthClient.setUpdateUserId(currentEpochMilli);
	}
	//=====================================私有方法  end=====================================

}
