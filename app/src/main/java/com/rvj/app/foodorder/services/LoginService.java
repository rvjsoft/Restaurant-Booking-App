package com.rvj.app.foodorder.services;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvj.app.dataaccess.UserRepository;
import com.rvj.app.foodorder.entity.enums.UserLevel;
import com.rvj.app.foodorder.models.LoginRequest;
import com.rvj.app.foodorder.utils.AppConstants;

@Service
public class LoginService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	HttpSession session;
	
	public boolean processLogin(LoginRequest request, HttpServletResponse response) {
		if(isValidUser(request.getUserName(), request.getPassword())) {
			session.setAttribute(AppConstants.APP_USER, request.getUserName());
			session.setAttribute(AppConstants.USR_LEVEL, getUserLevel(request.getUserName()));
			String level = getUserLevel(request.getUserName()).toString();
			Cookie userLevel = new Cookie(AppConstants.USR_LEVEL, level);
			userLevel.setSecure(true);
			response.addCookie(userLevel);
			System.out.println(getUserLevel(request.getUserName()));
			//TODO:set auth token here
			return true;
		} else {
			session.invalidate();
			return false;
		}
	}
	
	private UserLevel getUserLevel(String userName) {
		return userRepository.getUserLevel(userName);
	}

	public boolean isValidUser(String userName, String password) {
		return userRepository.findByUserNameAndPassword(userName, password).isPresent();
	}

	public void logout(HttpServletResponse response) {
		session.invalidate();
		Cookie userLevel = new Cookie(AppConstants.USR_LEVEL, null);
		userLevel.setMaxAge(0);
		userLevel.setSecure(true);
		response.addCookie(userLevel);
	}
	
}
