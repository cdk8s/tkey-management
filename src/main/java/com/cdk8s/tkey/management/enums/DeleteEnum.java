package com.cdk8s.tkey.management.enums;


public enum DeleteEnum implements BasicEnum {

	NOT_DELETE(1, "未删除"),
	DELETED(2, "已删除");

	private int code;
	private String description;

	DeleteEnum(final int code, final String description) {
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
