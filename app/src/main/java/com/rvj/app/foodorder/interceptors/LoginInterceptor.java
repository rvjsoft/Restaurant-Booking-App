package com.rvj.app.foodorder.interceptors;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rvj.app.foodorder.models.BaseResponse;
import com.rvj.app.foodorder.utils.AppConstants;

public class LoginInterceptor implements HandlerInterceptor{

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if(request.getServletPath().startsWith("/logout"))
			return true;
		if(Objects.nonNull(session) && Objects.nonNull(session.getAttribute(AppConstants.APP_USER))) {
			response.setHeader(AppConstants.USR_LEVEL_HEADER, session.getAttribute(AppConstants.USR_LEVEL).toString());
			if(request.getServletPath().startsWith("/register")) {
				response.sendError(403, "logout to continue");
				writeToResponse(response, "logout to continue");
				return false;
			} else {
				response.setStatus(200, "already loged in");
				writeToResponse(response, "already loded in");
				return false;
			}
		}
		return true;
	}
	
	private void writeToResponse(HttpServletResponse response, String message) {
		BaseResponse res = new BaseResponse();
		res.setMessage(message);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String resBody = mapper.writeValueAsString(res);
			response.getWriter().print(resBody);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession(false);
		if(Objects.nonNull(session) && (response.getStatus() != 200 || Objects.isNull(session.getAttribute(AppConstants.APP_USER)))) {
			session.invalidate();
		}
	}
	
}
