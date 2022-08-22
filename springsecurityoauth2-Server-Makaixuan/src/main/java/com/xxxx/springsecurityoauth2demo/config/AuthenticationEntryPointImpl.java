package com.xxxx.springsecurityoauth2demo.config;

import com.xxxx.springsecurityoauth2demo.pojo.SysResult;
import com.xxxx.springsecurityoauth2demo.util.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component//授权失败处理器
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        SysResult result = new SysResult().setStatus(401).setMsg("用户认证失败请查询登录");
        String json = result.toString();
        System.out.println("json===========>" + json);
        //处理异常
        WebUtils.renderString(response,json);
    }
}
