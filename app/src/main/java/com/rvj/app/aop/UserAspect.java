package com.rvj.app.aop;

import com.rvj.app.foodorder.models.BaseRequest;
import com.rvj.app.security.CustomUserDetails;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.DisabledIf;

import java.security.Principal;
import java.util.Objects;

@DisabledIf("${app.customsecurity}")
@Aspect
@Configuration
public class UserAspect {

//	@Autowired
//	HttpSession session;
	
	@Pointcut(value = "@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postMapping() {}
	
	@Pointcut(value = "@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void getMapping() {}
	
	@Pointcut(value = "execution(* com.rvj.app.foodorder.controllers.SessionController.login(..))")
	public void loginMethod() {}
	
	@Pointcut(value = "execution(* com.rvj.app.foodorder.controllers.UserRegistrationController.register(..))")
	public void registerMethod() {}
	
	@Before(value = "(postMapping() || getMapping()) && (!loginMethod()) && (!registerMethod())" )
	public void print(JoinPoint jp) {
		if(jp.getArgs().length > 1) {
			if(Objects.nonNull(jp.getArgs()) && jp.getArgs()[0] instanceof BaseRequest) {
				BaseRequest request = (BaseRequest) jp.getArgs()[0];
				Principal principal = SecurityContextHolder.getContext().getAuthentication();
				String username = null;
				if(principal instanceof UsernamePasswordAuthenticationToken) {
					CustomUserDetails customUserDetails = (CustomUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
					username = customUserDetails.getUsername();
				}
				request.setUserName(username);
			}
		}
	}
}
