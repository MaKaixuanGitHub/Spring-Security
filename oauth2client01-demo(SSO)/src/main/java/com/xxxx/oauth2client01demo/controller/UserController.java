package com.xxxx.oauth2client01demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoubin
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

	/**
	 * 获取当前用户信息
	 * @param authentication
	 * @return
	 */
	@RequestMapping("getCurrentUser")
	public Object getCurrentUser(Authentication authentication){
		return authentication;
	}

}