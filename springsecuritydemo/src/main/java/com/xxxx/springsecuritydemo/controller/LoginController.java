package com.xxxx.springsecuritydemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录Controller
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Controller
public class LoginController {

	// /**
	//  * 登录
	//  * @return
	//  */
	// @RequestMapping("login")
	// public String login(){
	// 	System.out.println("执行登录方法");
	// 	return "redirect:main.html";
	// }



	/**
	 * 页面跳转
	 * @return
	 */
	// @Secured("ROLE_abc")
	//PreAuthorize的表达式允许ROLE_开头，也可以不以ROLE_开头，配置类不允许ROLE_开头
	@PreAuthorize("hasRole('ROLE_abc')")
	@RequestMapping("toMain")
	public String toMain(){
		return "redirect:main.html";
	}

	/**
	 * 页面跳转
	 * @return
	 */
	@RequestMapping("toError")
	public String toError(){
		return "redirect:error.html";
	}


	/**
	 * 页面跳转
	 * @return
	 */
	@RequestMapping("demo")
	public String demo(){
		return "demo";
	}


	/**
	 * 页面跳转
	 * @return
	 */
	@RequestMapping("showLogin")
	public String showLogin(){
		return "login";
	}

}