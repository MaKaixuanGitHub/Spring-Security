package com.xxxx.jjwtdemo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class JjwtdemoApplicationTests {

	/**
	 * 创建token
	 */
	@Test
	public void testCreateToken(){
		//创建JwtBuilder对象
		JwtBuilder jwtBuilder = Jwts.builder()
				//声明的标识{"jti":"8888"}
				.setId("8888")
				//主体，用户{"sub":"Rose"}
				.setSubject("Rose")
				//创建日期{"ita":"xxxxxx"}
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256,"xxxx");
		//获取jwt的token
		String token = jwtBuilder.compact();
		System.out.println(token);


		System.out.println("==================================");
		String[] split = token.split("\\.");
		System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
		System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
		//无法解密
		System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
	}


	/**
	 * 解析token
	 */
	@Test
	public void testParseToken(){
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODg4Iiwic3ViIjoiUm9zZSIsImlhdCI6MTU4MzkxMTMyNX0" +
				".XYm4DQj2nF_URFW4TRWDLVXni64FkYWvKMSMmtefEK4";
		//解析token获取负载中声明的对象
		Claims claims = Jwts.parser()
				.setSigningKey("xxxx")
				.parseClaimsJws(token)
				.getBody();
		System.out.println("id:"+claims.getId());
		System.out.println("subject:"+claims.getSubject());
		System.out.println("issuedAt:"+claims.getIssuedAt());
	}




	/**
	 * 创建token（失效时间）
	 */
	@Test
	public void testCreateTokenHasExp(){
		//当前系统时间
		long now = System.currentTimeMillis();
		//过期时间，1分钟
		long exp = now + 60 * 1000;
		//创建JwtBuilder对象
		JwtBuilder jwtBuilder = Jwts.builder()
				//声明的标识{"jti":"8888"}
				.setId("8888")
				//主体，用户{"sub":"Rose"}
				.setSubject("Rose")
				//创建日期{"ita":"xxxxxx"}
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256,"xxxx")
				//设置过期时间
				.setExpiration(new Date(exp));
		//获取jwt的token
		String token = jwtBuilder.compact();
		System.out.println(token);


		System.out.println("==================================");
		String[] split = token.split("\\.");
		System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
		System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
		//无法解密
		System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
	}


	/**
	 * 解析token(失效时间)
	 */
	@Test
	public void testParseTokenHasExp(){
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODg4Iiwic3ViIjoiUm9zZSIsImlhdCI6MTU4MzkxMjY1MCwiZXhwIjoxNTgzOTEyNzEwfQ" +
				".yE5cI80UVHvkexWOp7Rq5fHLVNwfBV_PYuF_fRNgJGU";
		//解析token获取负载中声明的对象
		Claims claims = Jwts.parser()
				.setSigningKey("xxxx")
				.parseClaimsJws(token)
				.getBody();
		System.out.println("id:"+claims.getId());
		System.out.println("subject:"+claims.getSubject());
		System.out.println("issuedAt:"+claims.getIssuedAt());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("签发时间："+simpleDateFormat.format(claims.getIssuedAt()));
		System.out.println("过期时间："+simpleDateFormat.format(claims.getExpiration()));
		System.out.println("当前时间："+simpleDateFormat.format(new Date()));
	}






	/**
	 * 创建token(自定义申明)
	 */
	@Test
	public void testCreateTokenByClaims(){
		//创建JwtBuilder对象
		JwtBuilder jwtBuilder = Jwts.builder()
				//声明的标识{"jti":"8888"}
				.setId("8888")
				//主体，用户{"sub":"Rose"}
				.setSubject("Rose")
				//创建日期{"ita":"xxxxxx"}
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256,"xxxx")
				//自定义申明
				.claim("roles","admin")
				.claim("logo","xxx.jpg");
				//直接传入map
				// .addClaims(map)
		//获取jwt的token
		String token = jwtBuilder.compact();
		System.out.println(token);


		System.out.println("==================================");
		String[] split = token.split("\\.");
		System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
		System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
		//无法解密
		System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
	}


	/**
	 * 解析token(自定义申明)
	 */
	@Test
	public void testParseTokenByClaims(){
		String token = "eyJhbGciOiJIUzI1NiJ9" +
				".eyJqdGkiOiI4ODg4Iiwic3ViIjoiUm9zZSIsImlhdCI6MTU4MzkxMzA5NSwicm9sZXMiOiJhZG1pbiIsImxvZ28iOiJ4eHguanBnIn0.t_n-3yVkRU7pXYfCJI9gWhfFd_2JBQJgPF4vpsTTlfg";
		//解析token获取负载中声明的对象
		Claims claims = Jwts.parser()
				.setSigningKey("xxxx")
				.parseClaimsJws(token)
				.getBody();
		System.out.println("id:"+claims.getId());
		System.out.println("subject:"+claims.getSubject());
		System.out.println("issuedAt:"+claims.getIssuedAt());
		System.out.println("roles:"+claims.get("roles"));
		System.out.println("logo:"+claims.get("logo"));
	}

}
