package com.xxxx.springsecuritydemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SpringsecuritydemoApplicationTests {

	@Test
	public void contextLoads() {
		PasswordEncoder pe = new BCryptPasswordEncoder();
		String encode = pe.encode("123");
		System.out.println(encode);
		boolean matches = pe.matches("1234", encode);
		System.out.println("========================");
		System.out.println(matches);
	}

}
