package com.zhang.oauther2.config;

import com.zhang.oauther2.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 开启AuthorizationServer
 */
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * token保存方式
     */
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    /**
     * MyWebSecurityConfig中配置PasswordEncoder的bean
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * MyWebSecurityConfig中配置AuthenticationManager的bean
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailService myUserDetailService;

    /**
     * 默认token不允许获取，默认不允许用户表单登录，允许token的校验
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //默认token不允许获取
                .tokenKeyAccess("permitAll()")
                //默认不允许客户端表单登录
                .allowFormAuthenticationForClients()
                //获取token的时候需要验证信息
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 添加两个客户端
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("app-one")
                .secret(passwordEncoder.encode("123"))
                .authorizedGrantTypes("implicit", "refresh_token", "password", "authorization_code", "client_credentials")
                .authorities("admin", "user")
                .scopes("read", "write", "all")
                .accessTokenValiditySeconds(60000)
                .refreshTokenValiditySeconds(120000)
                .redirectUris("http://mrbird.cc")
                .and()
                .withClient("client_id")
                .secret(passwordEncoder.encode("123"))
                .authorizedGrantTypes("implicit", "refresh_token", "password", "authorization_code", "client_credentials")
                .authorities("admin", "user")
                .scopes("read", "write", "all")
                .accessTokenValiditySeconds(60000)
                .refreshTokenValiditySeconds(120000)
                .redirectUris("http://mrbird.cc");
    }

    /**
     * 替换两个
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                //设置token的保存方式
                .tokenStore(tokenStore)
                //生成token的工具
                .accessTokenConverter(jwtAccessTokenConverter)
                //注入bean
                .authenticationManager(authenticationManager)
                //刷新token需要校验用户
                .userDetailsService(myUserDetailService);
    }
}
