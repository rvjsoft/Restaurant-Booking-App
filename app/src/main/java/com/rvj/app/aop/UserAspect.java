package com.rvj.app.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import com.rvj.app.foodorder.models.BaseRequest;

@Aspect
@Configuration
public class UserAspect {

	@Pointcut(value = "@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postMapping() {}
	
	@Pointcut(value = "@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void getMapping() {}
	
	@Pointcut(value = "execution(* com.rvj.app.foodorder.controllers.SessionController.login(..))")
	public void loginMethod() {}
	
	@Before(value = "(postMapping() || getMapping()) && (!loginMethod())" )
	public void print(JoinPoint jp) {
		if(jp.getArgs().length > 1) {
			if(jp.getArgs()[0] instanceof BaseRequest) {
				BaseRequest request = (BaseRequest) jp.getArgs()[0];
			}
		}
	}
}
