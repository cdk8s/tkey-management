package com.cdk8s.tkey.management.config;

import com.cdk8s.tkey.management.util.response.R;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public ResponseEntity<?> handleError(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == 404) {
			return R.failure(HttpStatus.NOT_FOUND, "404");
		} else {
			return R.failure(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
