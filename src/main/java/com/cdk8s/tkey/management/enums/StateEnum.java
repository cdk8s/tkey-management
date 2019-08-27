package com.cdk8s.tkey.management.enums;


public enum StateEnum implements BasicEnum {

	ENABLE(1, "启用"),
	DISABLE(2, "禁用");

	private int code;
	private String description;

	StateEnum(final int code, final String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
