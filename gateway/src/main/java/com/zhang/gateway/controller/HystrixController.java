package com.zhang.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Copyright 深圳金雅福控股集团有限公司
 * @Author: zhangfanjun
 * @Date 2021/11/17
 * @Version: 1.0
 */
@RequestMapping("/hystrix")
@RestController
public class HystrixController {

    @RequestMapping("/defaultError")
    public Object defaultHystrix(){
        return "请求熔断";
    }
}
