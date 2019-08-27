package com.cdk8s.tkey.management.exception;

import com.cdk8s.tkey.management.enums.ResponseProduceTypeEnum;
import com.cdk8s.tkey.management.util.ExceptionUtil;
import com.cdk8s.tkey.management.util.StringUtil;
import com.cdk8s.tkey.management.util.response.R;
import com.cdk8s.tkey.management.util.response.ResponseEnum;
import com.cdk8s.tkey.management.util.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

	private static final String JSON_TYPE = "application/json";
	private static final String X_TYPE = "XMLHttpRequest";


	//=====================================业务处理 start=====================================

	/**
	 * 系统异常
	 */
	@ExceptionHandler(SystemException.class)
	public Object handleSystemException(HttpServletRequest httpServletRequest, SystemException e) {
		String message = e.getMessage();
		if (StringUtil.isBlank(message)) {
			message = "系统发生异常，请联系管理员处理";
		}
		return getResponseObject(e, HttpStatus.INTERNAL_SERVER_ERROR, ResponseEnum.SYSTEM_ERROR, httpServletRequest, message, ResponseProduceTypeEnum.JSON);
	}

	/**
	 * OAuth API 业务异常
	 */
	@ExceptionHandler(OauthApiException.class)
	public Object handleOauthApiException(HttpServletRequest httpServletRequest, OauthApiException e) {
		String message = e.getMessage();
		if (StringUtil.isBlank(message)) {
			message = "系统发生异常，请联系管理员处理";
		}
		return getResponseObject(e, HttpStatus.BAD_REQUEST, ResponseEnum.RECORD_ILLEGAL_STATE, httpServletRequest, message, ResponseProduceTypeEnum.JSON);
	}

	/**
	 * Hibernate Validator 参数校验失败错误统一返回
	 */
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public Object bindExceptionHandler(HttpServletRequest httpServletRequest, MethodArgumentNotValidException e) {
		String message;
		FieldError fieldError = e.getBindingResult().getFieldError();
		if (null == fieldError) {
			message = "请求参数有误";
		} else {
			message = fieldError.getDefaultMessage();
		}
		return getResponseObject(e, HttpStatus.BAD_REQUEST, ResponseEnum.PARAM_FORMAT_ILLEGAL, httpServletRequest, message, ResponseProduceTypeEnum.JSON);
	}

	/**
	 * Hibernate Validator 参数校验失败错误统一返回
	 */
	@ExceptionHandler(value = {BindException.class})
	public Object bindExceptionHandler(HttpServletRequest httpServletRequest, BindException e) {
		String message;
		FieldError fieldError = e.getBindingResult().getFieldError();
		if (null == fieldError) {
			message = "请求参数有误";
		} else {
			message = fieldError.getDefaultMessage();
		}
		return getResponseObject(e, HttpStatus.BAD_REQUEST, ResponseEnum.PARAM_FORMAT_ILLEGAL, httpServletRequest, message, ResponseProduceTypeEnum.JSON);
	}

	/**
	 * 其他异常
	 */
	@ExceptionHandler(Exception.class)
	public Object handleException(HttpServletRequest httpServletRequest, Exception e) {
		String message = e.getMessage();
		if (StringUtil.isBlank(message)) {
			message = "系统发生异常";
		} else {
			log.error(message);
			for (ExceptionDescriptionEnum exceptionDescription : ExceptionDescriptionEnum.values()) {
				if (message.contains(exceptionDescription.getKeyWord())) {
					message = exceptionDescription.getDescription();
					break;
				}
			}
		}
		return getResponseObject(e, HttpStatus.INTERNAL_SERVER_ERROR, ResponseEnum.SYSTEM_ERROR, httpServletRequest, message, ResponseProduceTypeEnum.JSON);

	}

	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================

	private Object getResponseObject(Exception e, HttpStatus httpStatus, ResponseEnum responseEnum, HttpServletRequest httpServletRequest, String message, ResponseProduceTypeEnum responseProduceTypeEnum) {
		log.error("------zch------统一异常处理器 URI：<{}>", httpServletRequest.getRequestURI());
		log.error("------zch------统一异常处理器 message：<{}>", message);
		if (log.isDebugEnabled()) {
			log.debug(ExceptionUtil.getStackTraceAsString(e));
		}

		// 指定返回类型
		if (null != responseProduceTypeEnum) {
			if (responseProduceTypeEnum == ResponseProduceTypeEnum.JSON) {
				return returnJson(httpStatus, responseEnum, message);
			}
			if (responseProduceTypeEnum == ResponseProduceTypeEnum.HTML) {
				return returnHtml(httpServletRequest, message);
			}
		}

		// 根据 http 请求头进行判断返回什么类型
		String contentTypeHeader = httpServletRequest.getHeader("Content-Type");
		String acceptHeader = httpServletRequest.getHeader("Accept");
		String xRequestedWith = httpServletRequest.getHeader("X-Requested-With");
		if ((contentTypeHeader != null && contentTypeHeader.contains(JSON_TYPE)) || (acceptHeader != null && acceptHeader.contains(JSON_TYPE)) || X_TYPE.equalsIgnoreCase(xRequestedWith)) {
			return returnJson(httpStatus, responseEnum, message);
		} else {
			return returnHtml(httpServletRequest, message);
		}
	}

	private ResponseEntity<ResponseObject> returnJson(HttpStatus httpStatus, ResponseEnum responseEnum, String message) {
		return R.failure(httpStatus, responseEnum, message);
	}

	private ModelAndView returnHtml(HttpServletRequest httpServletRequest, String message) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("errorMsg", message);
		modelAndView.addObject("url", httpServletRequest.getRequestURL());
		//modelAndView.addObject("stackTrace", e.getStackTrace());
		modelAndView.setViewName("error");
		return modelAndView;
	}

	//=====================================私有方法  end=====================================
}
