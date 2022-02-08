//package com.zhang.gateway.filter.global;
//
//import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import reactor.core.publisher.Mono;
//
///**
// * @Copyright 深圳金雅福控股集团有限公司
// * @Author: zhangfanjun
// * @Date 2021/11/17
// * @Version: 1.0
// */
//@Configuration
//public class KeyResolverFilter {
//
//    @Bean
//    public KeyResolver ipKeyResolver(){
//        return exchange-> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
//    }
//}
