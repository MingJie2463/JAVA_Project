package com.jayfan.store.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		if(request.getSession().getAttribute("customer") == null) {
			response.sendRedirect("/login");
			return false;
		}
		//return super.preHandle(request, response, handler);
		return true;
	}



}
