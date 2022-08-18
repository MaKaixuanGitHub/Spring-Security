package com.xxxx.springsecurityoauth2demo.config;

import com.xxxx.springsecurityoauth2demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 授权服务器配置
 *
 * @author makx
 * @since 1.0.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
	@Autowired
	@Qualifier("jwtTokenStore")
	private TokenStore tokenStore;
	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	@Autowired
	private JwtTokenEnhancer jwtTokenEnhancer;


	/**
	 * 使用密码模式所需配置
	 * 该方法是用来配置Authorization Server endpoints的一些非安全特性的，比如token存储、token自定义、授权类型等等的
	 * 默认情况下，你不需要做任何事情，除非你需要密码授权，那么在这种情况下你需要提供一个AuthenticationManager
	 * @param endpoints endpoints
	 * @throws Exception Exception
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		//配置JWT内容增强器
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		List<TokenEnhancer> delegates = new ArrayList<>();
		delegates.add(jwtTokenEnhancer);
		delegates.add(jwtAccessTokenConverter);
		enhancerChain.setTokenEnhancers(delegates);
		endpoints.authenticationManager(authenticationManager)
				.userDetailsService(userService)
				//配置存储令牌策略
				.tokenStore(tokenStore)
				.accessTokenConverter(jwtAccessTokenConverter)
				.tokenEnhancer(enhancerChain);
	}

	/***
	 * 配置ClientDetailsService
	 * 注意，除非你在下面的configure(AuthorizationServerEndpointsConfigurer)中指定了一个AuthenticationManager，
	 * 否则密码授权方式不可用。
	 * 至少配置一个client，否则服务器将不会启动。
	 * @param clients clients
	 * @throws Exception Exception
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.jdbc(dataSource);
		clients.inMemory()
				//配置client-id
				.withClient("admin")
				//配置client-secret
				.secret(passwordEncoder.encode("112233"))
				//配置访问token的有效期
				.accessTokenValiditySeconds(3600)
				//配置刷新Token的有效期
				.refreshTokenValiditySeconds(864000)
				//配置redirect_uri,用于授权成功后跳转
				.redirectUris("http://localhost:8081/login")
				//自动授权配置
				.autoApprove(true)
				//配置申请的权限范围
				.scopes("all")
				//配置grant_type，表示授权类型
				.authorizedGrantTypes("password","refresh_token","authorization_code")
				.and()
				.withClient("makaixuan")
				.secret(passwordEncoder.encode("332211"))
				.accessTokenValiditySeconds(3600)
				.refreshTokenValiditySeconds(864000)
				.redirectUris("http://192.168.105.32:8082/login")
				.autoApprove(true)
				.scopes("all")
				.authorizedGrantTypes("password","refresh_token","authorization_code");
	}

	/***
	 * 获取秘钥需要身份认证
	 * 配置授权服务器的安全，意味着实际上是/oauth/token端点。
	 * /oauth/authorize端点也应该是安全的
	 * 默认的设置覆盖到了绝大多数需求，所以一般情况下你不需要做任何事情。
	 * @param security security
	 * @throws Exception Exception
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//获取秘钥需要身份认证，使用单点登录时必须配置
		security.tokenKeyAccess("isAuthenticated()");
	}
}