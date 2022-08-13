package com.xxxx.springsecuritydemo.config;

import com.xxxx.springsecuritydemo.handle.MyAccessDeniedHandler;
import com.xxxx.springsecuritydemo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * SpringSecurity配置类
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyAccessDeniedHandler myAccessDeniedHandler;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private PersistentTokenRepository persistentTokenRepository;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//表单提交
		http.formLogin()
				.usernameParameter("username123")
				.passwordParameter("password123")
				//当发现/login时认为是登录，必须和表单提交的地址一样，去执行UserDetailsServiceImpl
				.loginProcessingUrl("/login")
				//自定义登录页面
				.loginPage("/showLogin")
				//登录成功后跳转页面，Post请求
				.successForwardUrl("/toMain")
				//登录成功后处理器，不能和successForwardUrl共存
				// .successHandler(new MyAuthenticationSuccessHandler("/main.html"))
				//登录失败后跳转页面，Post请求
				.failureForwardUrl("/toError");
				//登录失败后处理器，不能和failureForwardUrl共存
				// .failureHandler(new MyAuthenticationFailureHandler("/error.html"));


		//授权认证
		http.authorizeRequests()
				//error.html不需要被认证
				// .antMatchers("/error.html").permitAll()
				.antMatchers("/error.html").access("permitAll()")
				//login.html不需要被认证
				// .antMatchers("/login.html").permitAll()
				.antMatchers("/showLogin").access("permitAll()")
				.antMatchers("/js/**","/css/**","/images/**").permitAll()
				// .antMatchers("/**/*.png").permitAll()
				//正则表达式匹配
				// .regexMatchers(".+[.]png").permitAll()
				// .regexMatchers(HttpMethod.GET,"/demo").permitAll()
				//mvc匹配servletPath为特有方法，其他2种匹配方式没有
				// .mvcMatchers("/demo").servletPath("/xxxx").permitAll()
				//和mvc匹配等效
				// .antMatchers("/xxxx/demo").permitAll()
				//权限判断
				// .antMatchers("/main1.html").hasAuthority("admiN")
				// .antMatchers("/main1.html").hasAnyAuthority("admin","admiN")
				//角色判断
				// .antMatchers("/main1.html").hasRole("abC")
				// .antMatchers("/main1.html").access("hasRole('abc')")
				// .antMatchers("/main1.html").hasAnyRole("abC,abc")
				//IP地址判断
				// .antMatchers("/main1.html").hasIpAddress("127.0.0.1")
				//所有请求都必须被认证，必须登录之后被访问
				.anyRequest().authenticated();
				//access自定义方法
				// .anyRequest().access("@myServiceImpl.hasPermission(request,authentication)");

		//关闭csrf防护
		// http.csrf().disable();

		//异常处理
		// http.exceptionHandling()
		// 		.accessDeniedHandler(myAccessDeniedHandler);

		//记住我
		http.rememberMe()
				//失效时间，单位秒
				.tokenValiditySeconds(60)
				// .rememberMeParameter()
				//自定义登录逻辑
				.userDetailsService(userDetailsService)
				//持久层对象
				.tokenRepository(persistentTokenRepository);

		//退出登录
		http.logout()
				.logoutUrl("/logout")
				//退出登录跳转页面
				.logoutSuccessUrl("/login.html");

	}

	@Bean
	public PasswordEncoder getPw(){
		return new BCryptPasswordEncoder();
	}


	@Bean
	public PersistentTokenRepository getPersistentTokenRepository(){
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		//自动建表，第一次启动时候需要，第二次启动注释掉
		// jdbcTokenRepository.setCreateTableOnStartup(true);
		return jdbcTokenRepository;
	}

}