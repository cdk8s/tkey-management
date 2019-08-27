package com.cdk8s.tkey.management.controller;

import com.cdk8s.tkey.management.mapstruct.OauthClientMapstruct;
import com.cdk8s.tkey.management.pojo.dto.param.OauthClientCreateRequestParam;
import com.cdk8s.tkey.management.pojo.dto.param.OauthClientPageQueryParam;
import com.cdk8s.tkey.management.pojo.dto.param.OauthClientUpdateRequestParam;
import com.cdk8s.tkey.management.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.tkey.management.pojo.dto.param.bases.OauthClientBatchUpdateStateRequestParam;
import com.cdk8s.tkey.management.properties.OauthProperties;
import com.cdk8s.tkey.management.service.OauthClientService;
import com.cdk8s.tkey.management.util.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/api/client")
public class OauthClientController {

	@Autowired
	private OauthProperties oauthProperties;

	@Autowired
	private OauthClientService oauthClientService;

	@Autowired
	private OauthClientMapstruct oauthClientMapstruct;

	//=====================================业务处理 start=====================================


	@RequestMapping(value = "/page", method = RequestMethod.POST)
	private ResponseEntity<?> page(@Valid @RequestBody OauthClientPageQueryParam param) {
		return R.success(oauthClientService.findPageByParam(param));
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	private ResponseEntity<?> detail(@RequestParam Long id) {
		return R.success(oauthClientService.findOneById(id));
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private ResponseEntity<?> create(@Valid @RequestBody OauthClientCreateRequestParam param) {
		oauthClientService.create(oauthClientMapstruct.toEntity(param));
		return R.success();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	private ResponseEntity<?> update(@Valid @RequestBody OauthClientUpdateRequestParam param) {
		if (oauthProperties.getDemoMode()) {
			return R.failure(HttpStatus.BAD_REQUEST, "演示模式无法修改数据");
		}
		oauthClientService.update(oauthClientMapstruct.toEntity(param));
		return R.success();
	}

	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	private ResponseEntity<?> batchDelete(@Valid @RequestBody IdListRequestParam param) {
		if (oauthProperties.getDemoMode()) {
			return R.failure(HttpStatus.BAD_REQUEST, "演示模式无法删除数据");
		}
		oauthClientService.batchDelete(param.getIdList());
		return R.success();
	}

	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	private ResponseEntity<?> batchUpdateState(@Valid @RequestBody OauthClientBatchUpdateStateRequestParam param) {
		if (oauthProperties.getDemoMode()) {
			return R.failure(HttpStatus.BAD_REQUEST, "演示模式无法修改数据");
		}
		oauthClientService.batchUpdateState(param.getStateEnum(), param.getIdList());
		return R.success();
	}


	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================


	//=====================================私有方法  end=====================================
}
