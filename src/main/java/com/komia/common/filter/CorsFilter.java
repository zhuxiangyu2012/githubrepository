package com.komia.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8000");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
		response.setHeader("Access-Control-Max-Age", "3600");  
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");  
		response.setHeader("Access-Control-Allow-Credentials", "true");  
        chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
	
}
