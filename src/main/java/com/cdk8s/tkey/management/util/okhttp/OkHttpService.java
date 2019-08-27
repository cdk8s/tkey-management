package com.cdk8s.tkey.management.util.okhttp;

import com.cdk8s.tkey.management.util.StringUtil;
import okhttp3.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class OkHttpService {

	private static final Logger log = LoggerFactory.getLogger(OkHttpService.class);
	private static final MediaType JSON_TYPE_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
	private static final MediaType XML_TYPE_MEDIA_TYPE = MediaType.parse("application/xml; charset=utf-8");
	private static final Integer SUCCESS_CODE = 200;

	@Autowired
	private OkHttpClient okHttpClient;

	//=====================================业务处理 start=====================================

	public OkHttpResponse get(String url) {
		Request request = new Request.Builder().url(url).build();
		return getResponse(request);
	}

	public OkHttpResponse get(String url, Map<String, String> queries, Map<String, String> headers) {
		StringBuilder fullUrl = new StringBuilder(url);
		if (queries != null && queries.keySet().size() > 0) {
			fullUrl.append("?");

			for (Map.Entry<String, String> entry : queries.entrySet()) {
				if (StringUtil.isNotBlank(entry.getValue()) && !StringUtil.equalsIgnoreCase(entry.getValue(), "null")) {
					fullUrl.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
				}
			}

			fullUrl.deleteCharAt(fullUrl.length() - 1);
		}

		Request.Builder builderRequest = new Request.Builder();

		if (headers != null && headers.keySet().size() > 0) {
			headers.forEach((key, value) -> {
				builderRequest.addHeader(key, value);
			});
		}

		Request request = builderRequest.url(fullUrl.toString()).build();
		return getResponse(request);
	}

	/**
	 * post 请求 form 参数
	 *
	 * @param url    请求的url
	 * @param params post form 提交的参数
	 * @return
	 */
	public OkHttpResponse post(String url, Map<String, String> params, Map<String, String> headers) {
		FormBody.Builder builder = new FormBody.Builder();
		if (params != null && params.keySet().size() > 0) {
			params.forEach(builder::add);
		}

		Request.Builder builderRequest = new Request.Builder();
		if (headers != null && headers.keySet().size() > 0) {
			headers.forEach((key, value) -> {
				builderRequest.addHeader(key, value);
			});
		}

		Request request = builderRequest.url(url).post(builder.build()).build();
		return getResponse(request);
	}

	/**
	 * Post 请求发送 JSON 数据
	 * 参数一：请求Url
	 * 参数二：请求的JSON
	 * 参数三：请求回调
	 */
	public OkHttpResponse postJsonParams(String url, String jsonParams, Map<String, String> headers) {
		RequestBody requestBody = RequestBody.create(JSON_TYPE_MEDIA_TYPE, jsonParams);
		Request request = buildPostRequestBody(url, requestBody, headers);
		return getResponse(request);
	}

	/**
	 * Post请求发送xml数据
	 * 参数一：请求Url
	 * 参数二：请求的xmlString
	 * 参数三：请求回调
	 */
	public OkHttpResponse postXmlParams(String url, String xml, Map<String, String> headers) {
		RequestBody requestBody = RequestBody.create(XML_TYPE_MEDIA_TYPE, xml);
		Request request = buildPostRequestBody(url, requestBody, headers);
		return getResponse(request);
	}

	/**
	 * 返回结果已经带 Basic 开头
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	public String basicAuthorization(String username, String password) {
		return Credentials.basic(username, password);
	}

	public String bearerAuthorization(String token) {
		return "Bearer " + token;
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================
	private Request buildPostRequestBody(String url, RequestBody requestBody, Map<String, String> headers) {
		Request.Builder builderRequest = new Request.Builder();
		if (headers != null && headers.keySet().size() > 0) {
			headers.forEach((key, value) -> {
				builderRequest.addHeader(key, value);
			});
		}
		return builderRequest.url(url).post(requestBody).build();
	}

	private OkHttpResponse getResponse(Request request) {
		Response response = null;
		try {
			response = okHttpClient.newCall(request).execute();
			OkHttpResponse okHttpResponse = new OkHttpResponse();
			okHttpResponse.setStatus(response.code());
			okHttpResponse.setResponse(response.body().string());
			return okHttpResponse;
		} catch (Exception e) {
			log.error("okhttp3 put error >> ex = {}", ExceptionUtils.getStackTrace(e));
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}


	//=====================================私有方法  end=====================================

}
