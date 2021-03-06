package com.jayfan.store.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/").addPathPatterns("/**").excludePathPatterns("/login").excludePathPatterns("/reg");
		
		//super.addInterceptors(registry);
	}
	
	

}
