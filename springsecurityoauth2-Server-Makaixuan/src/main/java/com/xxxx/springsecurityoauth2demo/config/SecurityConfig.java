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
//		http.csrf().disable()
//				.authorizeRequests()
//				.antMatchers("/oauth/**","/login/**","logout/**")
//				.permitAll()
//				.anyRequest()
//				.authenticated()
//				.and()
//				.formLogin()
//				.permitAll();
		System.out.println("SecurityConfig========>configure start");
		http.authorizeRequests()
				.antMatchers("/oauth/**","/login/**", "logout/**").permitAll()
				.anyRequest().authenticated()   // 其他地址的访问均需验证权限
				.and()
				.formLogin()
				.loginPage("/login")
				.and()
				.logout().logoutSuccessUrl("/");

		http.csrf().disable();
		System.out.println("SecurityConfig========>configure end");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/assets/**");
	}

//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
//	}


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

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}