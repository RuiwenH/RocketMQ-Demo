package com.reven.core;

import com.alibaba.fastjson.JSON;

/**
 * 统一API响应结果封装
 */
public class ApiResult {
    private int code;
    private String message;
    private Object data;

    public ApiResult setCode(ApiResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ApiResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ApiResult setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
