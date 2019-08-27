package com.cdk8s.tkey.management.exception;

import com.cdk8s.tkey.management.enums.ResponseProduceTypeEnum;
import com.cdk8s.tkey.management.util.response.ResponseEnum;

public class OauthApiException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String message;
	private int code = 500;
	private String pagePath;
	private ResponseProduceTypeEnum responseProduceTypeEnum;

	public OauthApiException(String message) {
		super(message);
		this.message = message;
	}

	public OauthApiException(String message, Throwable e) {
		super(message, e);
		this.message = message;
	}

	public OauthApiException(ResponseEnum responseEnum) {
		super(responseEnum.getDescription());
		this.message = responseEnum.getDescription();
		this.code = responseEnum.getCode();
	}

	public OauthApiException(String message, ResponseEnum responseEnum) {
		super(message);
		this.message = message;
		this.code = responseEnum.getCode();
	}

	public OauthApiException(String message, ResponseProduceTypeEnum responseProduceTypeEnum) {
		super(message);
		this.message = message;
		this.responseProduceTypeEnum = responseProduceTypeEnum;
	}

	public OauthApiException(String message, ResponseEnum responseEnum, ResponseProduceTypeEnum responseProduceTypeEnum) {
		super(message);
		this.message = message;
		this.responseProduceTypeEnum = responseProduceTypeEnum;
		this.code = responseEnum.getCode();
	}

	public OauthApiException(String message, ResponseProduceTypeEnum responseProduceTypeEnum, String pagePath) {
		super(message);
		this.message = message;
		this.pagePath = pagePath;
		this.responseProduceTypeEnum = responseProduceTypeEnum;
	}

	public OauthApiException(String message, ResponseEnum responseEnum, ResponseProduceTypeEnum responseProduceTypeEnum, String pagePath) {
		super(message);
		this.message = message;
		this.pagePath = pagePath;
		this.responseProduceTypeEnum = responseProduceTypeEnum;
		this.code = responseEnum.getCode();
	}

	public OauthApiException(String message, ResponseEnum responseEnum, Throwable e) {
		super(message, e);
		this.message = message;
		this.code = responseEnum.getCode();
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public ResponseProduceTypeEnum getResponseProduceTypeEnum() {
		return responseProduceTypeEnum;
	}

	public void setResponseProduceTypeEnum(ResponseProduceTypeEnum responseProduceTypeEnum) {
		this.responseProduceTypeEnum = responseProduceTypeEnum;
	}

	public String getPagePath() {
		return pagePath;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}
}
