package com.rvj.app.foodorder.interceptors;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.rvj.app.foodorder.entity.enums.UserLevel;
import com.rvj.app.foodorder.services.LoginService;
import com.rvj.app.foodorder.utils.AppConstants;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor{
	
	@Autowired
	LoginService loginService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String user = (String)session.getAttribute(AppConstants.APP_USER);
		if(Objects.nonNull(user)) {
			UserLevel level = (UserLevel) session.getAttribute(AppConstants.USR_LEVEL);
			if(Objects.isNull(level))
				return false;
			String path = request.getServletPath();
			if(!isGenPath(path) && !level.equals(getLevelByUrl(path))) {
				response.sendError(401, "not authorized");
				return false;
			}
			return true;
		} else {
			session.invalidate();
			return false;
		}
	}

	private boolean isGenPath(String path) {
		return path.startsWith("/gen");
	}

	private Object getLevelByUrl(String servletPath) {
		if(servletPath.startsWith("/customer"))
			return UserLevel.CUSTOMER;
		else if(servletPath.startsWith("/restaurant"))
			return UserLevel.RESTAURANT;
		return null;
	}
}
