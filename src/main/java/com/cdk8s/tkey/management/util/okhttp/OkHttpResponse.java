package com.cdk8s.tkey.management.util.okhttp;

public class OkHttpResponse {

	private int status;
	private String response;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
