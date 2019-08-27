package com.cdk8s.tkey.management.util.response;

import com.cdk8s.tkey.management.util.DatetimeUtil;
import lombok.*;

import java.io.Serializable;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject implements Serializable {

	private static final long serialVersionUID = 9010786825517645779L;

	private int code;
	private boolean isSuccess;
	private String msg;
	private Long timestamp;
	private Object data;

	public ResponseObject setCode(ResponseEnum responseEnum) {
		this.code = responseEnum.getCode();
		return this;
	}

	public Long getTimestamp() {
		if (null == timestamp) {
			return DatetimeUtil.currentEpochMilli();
		}
		return timestamp;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
