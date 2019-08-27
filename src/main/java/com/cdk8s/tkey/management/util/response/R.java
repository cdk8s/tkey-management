package com.cdk8s.tkey.management.util.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class R {

	private static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";
	private static final String DEFAULT_FAILURE_MESSAGE = "操作失败";
	private static final boolean DEFAULT_SUCCESS = true;
	private static final boolean DEFAULT_FAILURE = false;


	//=====================================正常返回 start=====================================


	public static ResponseEntity<ResponseObject> success() {
		ResponseObject resultObject = new ResponseObject();
		resultObject.setCode(ResponseEnum.SUCCESS);
		resultObject.setIsSuccess(DEFAULT_SUCCESS);
		resultObject.setMsg(DEFAULT_SUCCESS_MESSAGE);
		return ResponseEntity.status(HttpStatus.OK).body(resultObject);
	}


	public static ResponseEntity<ResponseObject> success(Object data) {
		ResponseObject resultObject = new ResponseObject();
		resultObject.setCode(ResponseEnum.SUCCESS);
		resultObject.setIsSuccess(DEFAULT_SUCCESS);
		resultObject.setMsg(DEFAULT_SUCCESS_MESSAGE);
		resultObject.setData(data);
		return ResponseEntity.status(HttpStatus.OK).body(resultObject);
	}

	public static ResponseEntity<ResponseObject> success(String message) {
		ResponseObject resultObject = new ResponseObject();
		resultObject.setCode(ResponseEnum.SUCCESS);
		resultObject.setIsSuccess(DEFAULT_SUCCESS);
		resultObject.setMsg(message);
		return ResponseEntity.status(HttpStatus.OK).body(resultObject);
	}

	public static ResponseEntity<ResponseObject> success(Object data, String message) {
		ResponseObject resultObject = new ResponseObject();
		resultObject.setCode(ResponseEnum.SUCCESS);
		resultObject.setIsSuccess(DEFAULT_SUCCESS);
		resultObject.setMsg(message);
		resultObject.setData(data);
		return ResponseEntity.status(HttpStatus.OK).body(resultObject);
	}

	//=====================================正常返回  end=====================================

	//=====================================异常返回 start=====================================

	public static ResponseEntity<ResponseObject> failure() {
		return failure(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public static ResponseEntity<ResponseObject> failure(HttpStatus httpStatus) {
		ResponseObject resultObject = new ResponseObject();
		resultObject.setCode(ResponseEnum.SYSTEM_ERROR);
		resultObject.setIsSuccess(DEFAULT_FAILURE);
		resultObject.setMsg(DEFAULT_FAILURE_MESSAGE);
		return ResponseEntity.status(httpStatus).body(resultObject);
	}

	public static ResponseEntity<ResponseObject> failure(HttpStatus httpStatus, String message) {
		ResponseObject resultObject = new ResponseObject();
		resultObject.setCode(ResponseEnum.SYSTEM_ERROR);
		resultObject.setIsSuccess(DEFAULT_FAILURE);
		resultObject.setMsg(message);
		return ResponseEntity.status(httpStatus).body(resultObject);
	}

	public static ResponseEntity<ResponseObject> failure(HttpStatus httpStatus, ResponseEnum responseEnum, String message) {
		ResponseObject resultObject = new ResponseObject();
		resultObject.setCode(responseEnum);
		resultObject.setIsSuccess(DEFAULT_FAILURE);
		resultObject.setMsg(message);
		return ResponseEntity.status(httpStatus).body(resultObject);
	}
	//=====================================异常返回  end=====================================
}
