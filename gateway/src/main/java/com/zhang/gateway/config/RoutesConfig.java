//package com.zhang.gateway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Copyright 深圳金雅福控股集团有限公司
// * @Author: zhangfanjun
// * @Date 2021/11/17
// * @Version: 1.0
// */
//@Configuration
//public class RoutesConfig {
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        RouteLocator routeLocator = builder.routes().route(x ->
//            x.path("/server/**").uri("http://localhost:9900")).build();
//        return routeLocator;
//    }
//}
