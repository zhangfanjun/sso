package com.zhang.gateway.filter.global;

import lombok.Data;

import java.util.Objects;

/**
 * @Copyright 深圳金雅福控股集团有限公司
 * @Author: zhangfanjun
 * @Date 2021/11/17
 * @Version: 1.0
 */
@Data
public class CommonResponse {
    private String code;
    private String message;
    private Object other;

    public CommonResponse(String code, String message, Object other) {
        this.code = code;
        this.message = message;
        this.other = other;
    }
}
