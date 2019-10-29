package com.cdk8s.tkey.management.interceptor;

import com.cdk8s.tkey.client.rest.TkeyProperties;
import com.cdk8s.tkey.client.rest.pojo.dto.TkeyToken;
import com.cdk8s.tkey.management.constant.GlobalVariable;
import com.cdk8s.tkey.management.properties.OauthClientProperties;
import com.cdk8s.tkey.management.util.CollectionUtil;
import com.cdk8s.tkey.management.util.GlobalVariableUtil;
import com.cdk8s.tkey.management.util.StringUtil;
import com.cdk8s.tkey.management.util.redis.StringRedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private TkeyProperties tkeyProperties;

	@Autowired
	private OauthClientProperties oauthClientProperties;

	@Autowired
	private StringRedisService<String, TkeyToken> tokenRedisService;

	//=====================================业务处理 start=====================================

	@SneakyThrows
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, Object handler) {
		if (!oauthClientProperties.getLoginInterceptorCheckEnable()) {
			// 不开启认证校验
			return true;
		}

		String accessToken = request.getHeader(GlobalVariable.HEADER_TOKEN_KEY);
		if (StringUtil.isBlank(accessToken)) {
			responseJson(response, null);
			return false;
		}

		TkeyToken tkeyToken = tokenRedisService.get(GlobalVariableUtil.getManagementClientTokenKey(accessToken));
		if (null == tkeyToken) {
			responseJson(response, null);
			return false;
		}

		// 读取可登陆 management 系统的用户名配置，只有在该配置里面的用户才允许操作 management
		List<String> acceptUsernameList = oauthClientProperties.getAcceptUsernameList();
		if (CollectionUtil.isEmpty(acceptUsernameList)) {
			responseJson(response, "您未被允许登陆该系统");
			return false;
		}

		if (!acceptUsernameList.contains(tkeyToken.getAttributes().getUsername())) {
			responseJson(response, "您未被允许登陆该系统");
			return false;
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
	}

	//=====================================业务处理 end=====================================
	//=====================================私有方法 start=====================================

	@SneakyThrows
	private void responseJson(final HttpServletResponse response, String msg) {
		if (StringUtil.isBlank(msg)) {
			msg = "您还未登录，请先登录";
		}
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		Map<String, Object> responseMap = new HashMap<>(4);
		responseMap.put("isSuccess", false);
		responseMap.put("msg", msg);
		responseMap.put("timestamp", Instant.now().toEpochMilli());
		responseMap.put("code", "0");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(responseMap);
		PrintWriter out = response.getWriter();
		out.print(json);
	}


	//=====================================私有方法  end=====================================


}
