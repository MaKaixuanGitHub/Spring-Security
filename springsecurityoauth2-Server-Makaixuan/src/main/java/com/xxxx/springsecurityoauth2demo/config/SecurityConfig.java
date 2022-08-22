package com.xxxx.springsecurityoauth2demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security配置类
 * @author makx
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("SecurityConfig========>configure start");
		http.authorizeRequests()
				.antMatchers("/oauth/**","/login/**", "logout/**").permitAll()
				.anyRequest().authenticated()   // 其他地址的访问均需验证权限
				.and()
				.formLogin()
				.loginPage("/login")
				.and()
				.logout().logoutSuccessUrl("/");

		// 关闭csrf跨域攻击
		http.csrf().disable();

		System.out.println("SecurityConfig========>configure end");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/assets/**");
	}

	/**
	 * 定义认证管理器对象，这个对象负责完成用户信息的认证，
	 * 即判定用户身份信息的合法性，在基于oauth2协议完成认
	 * 证时，需要此对象，所以这里讲此对象拿出来交给spring管理
	 * @return
	 * @throws Exception Exception
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	/***
	 * 当你将此对象注入容器时，就会自动将密码进行bc的比对校验。
	 * 如果输入的明文密码与数据库中的加密密码不匹配则报错。
	 * 切数据库中必须存储为bc加密的密码
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}