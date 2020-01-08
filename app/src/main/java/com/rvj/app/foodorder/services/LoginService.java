package com.rvj.app.foodorder.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvj.app.dataaccess.UserRepository;
import com.rvj.app.foodorder.models.LoginRequest;
import com.rvj.app.foodorder.utils.AppConstants;

@Service
public class LoginService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	HttpSession session;
	
	public boolean processLogin(LoginRequest request) {
		if(isValidUser(request.getUserName(), request.getPassword())) {
			session.setAttribute(AppConstants.APP_USER, request.getUserName());
			//TODO:set auth token here
			return true;
		} else {
			session.invalidate();
			return false;
		}
	}
	
	public boolean isValidUser(String userName, String password) {
		return userRepository.findByUserNameAndPassword(userName, password).isPresent();
	}
	
}
