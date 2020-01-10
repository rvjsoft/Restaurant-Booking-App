package com.rvj.app.foodorder.interceptors;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.rvj.app.foodorder.services.LoginService;
import com.rvj.app.foodorder.utils.AppConstants;

@Component
public class AutenticationInterceptor implements HandlerInterceptor{
	
	@Autowired
	LoginService loginService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String user = (String)session.getAttribute(AppConstants.APP_USER);
		String url = request.getRequestURL().toString();
		System.out.println(url); 
		if(Objects.nonNull(user)) {
			return true;
		} else {
			session.invalidate();
			return false;
		}
	}
}
