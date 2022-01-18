package com.zhang.oauther2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @Copyright 深圳金雅福控股集团有限公司
 * @Author: zhangfanjun
 * @Date 2021/11/17
 * @Version: 1.0
 */
@Configuration
public class TokenStoreConfig {
    @Bean
    public TokenStore myTokenStore() {
        //采用jwt保存，实际上并没有保存，jwt是去中心化加密，不需要保存的，校验通过即可
        JwtTokenStore jwtTokenStore = new JwtTokenStore(getJwtAccessTokenConverter());
        return jwtTokenStore;
    }

    /**
     * 设置加盐
     * @author zfj
     * @date 2022/1/13
     */
    @Bean
    public JwtAccessTokenConverter getJwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("sign");
        return jwtAccessTokenConverter;
    }
}
