package com.xxxx.springsecurityoauth2demo.dao;


import com.xxxx.springsecurityoauth2demo.pojo.SysPermission;
import com.xxxx.springsecurityoauth2demo.pojo.SysRole;
import com.xxxx.springsecurityoauth2demo.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class UserDao {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private SysRole admin = new SysRole("ADMIN", "管理员");
    private SysRole developer = new SysRole("DEVELOPER", "开发者");

    {
        SysPermission p1 = new SysPermission();
        p1.setCode("memberExport");
        p1.setName("会员列表导出");
        p1.setUrl("/member/export");

        SysPermission p2 = new SysPermission();
        p2.setCode("BookList");
        p2.setName("图书列表");
        p2.setUrl("/book/list");

        admin.setPermissionList(Arrays.asList(p1, p2));
        developer.setPermissionList(Arrays.asList(p1));
    }

    /**
     * 用户登录验证
     * @param username username
     * @return username
     */
    public SysUser selectByName(String username) {
        System.out.println("从数据库中查询用户");
        System.out.println("username=================> " + username);
        if ("zhangsan".equals(username)) {
            String password = passwordEncoder.encode("123456");
            SysUser sysUser = new SysUser("zhangsan", password);
            sysUser.setRoleList(Arrays.asList(admin, developer));
            return sysUser;
        } else if ("lisi".equals(username)) {
            String password = passwordEncoder.encode("123456");
            SysUser sysUser = new SysUser("lisi", password);
            sysUser.setRoleList(Arrays.asList(developer));
            return sysUser;
        }
        return null;
    }

}
