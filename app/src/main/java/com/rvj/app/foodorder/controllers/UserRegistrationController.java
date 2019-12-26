package com.rvj.app.foodorder.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvj.app.foodorder.models.RegisterUserRequest;

@RestController
@RequestMapping("register")
public class UserRegistrationController {
	
	@PostMapping(path = "user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String register(@RequestBody RegisterUserRequest request) {
		System.out.println(request);
		return "hai";
	}

}
