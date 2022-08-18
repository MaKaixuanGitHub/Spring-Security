package com.xxxx.springsecurityoauth2demo.service;

import com.xxxx.springsecurityoauth2demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义登录逻辑
 * @author zhoubin
 * @since 1.0.0
 */
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("UserService =========> username:" + username);
		if (username == null) {
			throw new UsernameNotFoundException("username is null!" + username);
		}

		String password = passwordEncoder.encode("123456");
		System.out.println("password=====>" + password);

		List<GrantedAuthority> authorities = new ArrayList<>();
		return new User(username, password, authorities);
	}
}