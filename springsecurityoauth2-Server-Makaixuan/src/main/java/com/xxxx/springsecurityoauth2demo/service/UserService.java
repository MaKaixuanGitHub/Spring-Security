package com.xxxx.springsecurityoauth2demo.service;

import com.xxxx.springsecurityoauth2demo.dao.UserDao;
import com.xxxx.springsecurityoauth2demo.pojo.SysPermission;
import com.xxxx.springsecurityoauth2demo.pojo.SysRole;
import com.xxxx.springsecurityoauth2demo.pojo.SysUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser sysUser = userDao.selectByName(username);
		System.out.println("sysUser=================>" + sysUser);
		if (null == sysUser) {
			throw new UsernameNotFoundException(username);
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
        for (SysRole role : sysUser.getRoleList()) {
			System.out.println("角色拥有权限role.getPermissionList()============> " + role.getPermissionList());
            for (SysPermission permission : role.getPermissionList()) {
                authorities.add(new SimpleGrantedAuthority(permission.getCode()));
            }
        }
		System.out.println("UserService================>authorities:  "+authorities);
		return new User(sysUser.getUsername(), sysUser.getPassword(), authorities);
	}
}