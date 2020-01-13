package com.rvj.app.foodorder.interceptors;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.rvj.app.foodorder.utils.AppConstants;

public class LoginInterceptor implements HandlerInterceptor{

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if(request.getServletPath().startsWith("/logout"))
			return true;
		if(Objects.nonNull(session) && Objects.nonNull(session.getAttribute(AppConstants.APP_USER))) {
			if(request.getServletPath().startsWith("/register")) {
				response.sendError(403, "logout to continue");
				return false;
			} else {
				response.setStatus(200, "already loged in");
				return false;
			}
		}
		return true;
	}
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		if(Objects.nonNull(session) && (response.getStatus() != 200 || Objects.isNull(session.getAttribute(AppConstants.APP_USER)))) {
			session.invalidate();
		}
	}
	
}
