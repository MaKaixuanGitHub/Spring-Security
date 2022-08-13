package com.xxxx.springsecuritydemo.handle;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhoubin
 * @since 1.0.0
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		//设置响应状态码
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setHeader("Content-Type","application/json;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.write("{\"status\":\"error\",\"msg\":\"权限不足，请联系管理员\"}");
		writer.flush();
		writer.close();
	}
}