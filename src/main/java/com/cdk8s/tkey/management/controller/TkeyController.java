package com.cdk8s.tkey.management.controller;

import com.cdk8s.tkey.client.rest.TkeyProperties;
import com.cdk8s.tkey.client.rest.pojo.bo.OAuth2AccessToken;
import com.cdk8s.tkey.client.rest.pojo.dto.TkeyToken;
import com.cdk8s.tkey.client.rest.service.TkeyService;
import com.cdk8s.tkey.client.rest.utils.CodecUtil;
import com.cdk8s.tkey.management.constant.GlobalVariable;
import com.cdk8s.tkey.management.pojo.dto.param.OauthClientCodeRequestParam;
import com.cdk8s.tkey.management.properties.OauthProperties;
import com.cdk8s.tkey.management.util.GlobalVariableUtil;
import com.cdk8s.tkey.management.util.redis.StringRedisService;
import com.cdk8s.tkey.management.util.response.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Controller
public class TkeyController {

	@Autowired
	private TkeyService tkeyService;

	@Autowired
	private TkeyProperties tkeyProperties;

	@Autowired
	private OauthProperties oauthProperties;

	@Autowired
	private StringRedisService<String, TkeyToken> tokenRedisService;

	//=====================================业务处理 start=====================================

	/**
	 * 接收 code，然后换取 token
	 */
	@SneakyThrows
	@RequestMapping(value = "/codeCallback", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> codeCallback(final HttpServletRequest request, final HttpServletResponse response, @Valid @RequestBody OauthClientCodeRequestParam codeRequestParam) {
		String code = codeRequestParam.getCode();
		String redirectUri = codeRequestParam.getRedirectUri();

		TkeyToken tkeyToken = getAccessToken(code);
		String accessToken = tkeyToken.getAccessToken();

		redirectUri = CodecUtil.decodeURL(redirectUri);

		// 前端拿到这两个返回参数进行重定向与设置 LocalStorage
		Map<String, Object> map = new HashMap<>();
		map.put("redirectUri", redirectUri);
		map.put("token", accessToken);

		tokenRedisService.set(GlobalVariableUtil.getManagementClientTokenKey(accessToken), tkeyToken, oauthProperties.getTokenMaxTimeToLiveInSeconds());
		return R.success(map);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(final HttpServletRequest request, HttpServletResponse response) {
		String finalLogoutUri = tkeyProperties.getFinalLogoutUri();

		String accessToken = request.getHeader(GlobalVariable.HEADER_TOKEN_KEY);
		tokenRedisService.delete(GlobalVariableUtil.getManagementClientTokenKey(accessToken));

		return "redirect:" + finalLogoutUri;
	}

	@RequestMapping(value = "/logoutSuccess", method = RequestMethod.GET)
	@ResponseBody
	private String logoutSuccess() {
		return "登出成功";
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	private TkeyToken getAccessToken(String code) {
		OAuth2AccessToken oauthToken = tkeyService.getAccessToken(code);
		String accessToken = oauthToken.getAccessToken();

		TkeyToken tkeyToken = new TkeyToken();
		tkeyToken.setAccessToken(accessToken);
		tkeyToken.setRefreshToken(oauthToken.getRefreshToken());
		tkeyToken.setAttributes(tkeyService.getUserProfile(oauthToken));

		return tkeyToken;
	}

	//=====================================私有方法  end=====================================
}