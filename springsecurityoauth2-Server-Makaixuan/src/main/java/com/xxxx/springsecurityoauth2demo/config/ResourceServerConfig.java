package com.xxxx.springsecurityoauth2demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源服务器配置
 *
 * @author makx
 * @since 1.0.0
 */
@Configuration
@EnableResourceServer
//启动方法上的权限控制,需要授权才可访问的方法上添加@PreAuthorize等相关注解
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Autowired
	private AccessDeniedHandlerImpl accessDeniedHandler;

	@Autowired
	private AuthenticationEntryPointImpl authenticationEntryPoint;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// 放行相关请求
		http.authorizeRequests()
				.anyRequest()
				.authenticated()
				.and()
				.requestMatchers()
				.antMatchers("/user/**");

		// 配置异常处理器
		http.exceptionHandling()
				// 配置认证失败及授权失败处理器
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler);
	}
}