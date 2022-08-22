package com.xxxx.springsecurityoauth2demo.config;

import com.xxxx.springsecurityoauth2demo.pojo.SysResult;
import com.xxxx.springsecurityoauth2demo.util.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component//授权失败处理器
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        SysResult result = new SysResult().setStatus(403).setMsg("您的权限不足");

        String json = result.toString();
        System.out.println("json===========>" + json);
        //处理异常
        WebUtils.renderString(response,json);
    }

}
