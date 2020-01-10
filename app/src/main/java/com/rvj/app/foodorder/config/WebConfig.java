package com.rvj.app.foodorder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.rvj.app.foodorder.converters.FoodCategorySpringConverter;
import com.rvj.app.foodorder.converters.FoodTypeSpringConverter;
import com.rvj.app.foodorder.converters.OrderStatusSpringConverter;
import com.rvj.app.foodorder.converters.PartOfDaySpringConverter;
import com.rvj.app.foodorder.converters.StatusSpringConverter;
import com.rvj.app.foodorder.interceptors.AutenticationInterceptor;

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
		registry.addInterceptor(new AutenticationInterceptor()).excludePathPatterns("/login");
	}
}
