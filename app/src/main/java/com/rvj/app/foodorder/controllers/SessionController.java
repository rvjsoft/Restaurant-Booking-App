package com.rvj.app.foodorder.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvj.app.foodorder.models.BaseResponse;
import com.rvj.app.foodorder.models.LoginRequest;
import com.rvj.app.foodorder.services.LoginService;
import com.rvj.app.foodorder.utils.ValidationUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/login")
@RestController
public class SessionController {

	@Autowired
	LoginService loginService;

	@PostMapping()
	public ResponseEntity<BaseResponse> login(@RequestBody LoginRequest request, BindingResult bindingResult) {
		BaseResponse response = new BaseResponse();
		HttpStatus status = null;
		response.setMessageId(request.getMessageId());
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = ValidationUtils.getErrorMap(bindingResult);
			response.setErrors(errors);
			response.setMessage("Request processing failed, Enter the valide values");
			status = HttpStatus.BAD_REQUEST;
		} else {
			boolean isValidUser = loginService.processLogin(request);
			if (isValidUser) {
				response.setMessage("login successfull.");
				status = HttpStatus.OK;
			} else {
				response.getErrors().put("username", "invalid username/password");
				response.setMessage("login failed.");
				status = HttpStatus.BAD_REQUEST;
			}
		}
		return new ResponseEntity<BaseResponse>(response, status);
	}
}
