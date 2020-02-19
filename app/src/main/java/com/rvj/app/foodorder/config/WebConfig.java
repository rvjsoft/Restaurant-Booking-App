package com.rvj.app.foodorder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.rvj.app.foodorder.converters.FoodCategorySpringConverter;
import com.rvj.app.foodorder.converters.FoodTypeSpringConverter;
import com.rvj.app.foodorder.converters.OrderStatusSpringConverter;
import com.rvj.app.foodorder.converters.PartOfDaySpringConverter;
import com.rvj.app.foodorder.converters.StatusSpringConverter;
import com.rvj.app.foodorder.interceptors.AuthenticationInterceptor;
import com.rvj.app.foodorder.interceptors.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new FoodTypeSpringConverter());
		registry.addConverter(new FoodCategorySpringConverter());
		registry.addConverter(new OrderStatusSpringConverter());
		registry.addConverter(new StatusSpringConverter());
		registry.addConverter(new PartOfDaySpringConverter());
	}
	
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new AuthenticationInterceptor()).excludePathPatterns("/login","/register/**","/logout","/appUi","/index.html","/*.js");
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/login","/register/**","/logout");
	}
	
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedOrigins("*").allowCredentials(true).allowedMethods("GET","POST");
//	}
}
