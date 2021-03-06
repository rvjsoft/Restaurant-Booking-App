package com.rvj.app.foodorder.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvj.app.foodorder.config.AppOperationConfiguration;
import com.rvj.app.foodorder.models.RegisterUserRequest;
import com.rvj.app.foodorder.models.RegisterUserResponse;
import com.rvj.app.foodorder.ops.UserRegistrationOperation;
import com.rvj.app.foodorder.utils.ValidationUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("register")
public class UserRegistrationController {
	
	@Autowired
	private AppOperationConfiguration opsConfiguration;
	
	@PostMapping(path = "user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request, BindingResult bindingResult) {
		log.info("Started Processing register user Request, messageId=" + request.getMessageId());
		RegisterUserResponse response = new RegisterUserResponse();
		response.setMessageId(request.getMessageId());
		if(bindingResult.hasErrors()) {
			Map<String, String> errors = ValidationUtils.getErrorMap(bindingResult);
			response.setErrors(errors);
			response.setMessage("Request processing failed, Enter the valide values");
			log.info("Started Processing register user Request, messageId=" + request.getMessageId());
			return new ResponseEntity<RegisterUserResponse>(response, HttpStatus.BAD_REQUEST);
		}
		else {
			log.info("No constraint errors,started creating the user");
			UserRegistrationOperation operation = opsConfiguration.getUserRegistrationOperation(request);
			response = operation.run();
			response.setMessageId(request.getMessageId());
			if(response.getErrors().isEmpty()) {
				response.setMessage("User created successfully.");
				return new ResponseEntity<RegisterUserResponse>(response, HttpStatus.OK);
			}
			else {
				response.setMessage("User creation failed");
				return new ResponseEntity<RegisterUserResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}
	}

}
