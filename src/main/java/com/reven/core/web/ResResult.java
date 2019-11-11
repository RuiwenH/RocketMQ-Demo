package com.reven.core.web;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName: ResResult
 * @Description: 统一的响应结果
 * @author huangruiwen
 * @date 2018年7月25日
 */
public class ResResult {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private int status;
    private String message;
    private Object data;

    public static ResResult success() {
        return new ResResult().setStatus(ResResultStatus.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static ResResult success(String message) {
        return new ResResult().setStatus(ResResultStatus.SUCCESS).setMessage(message);
    }

    public static ResResult success(Object data) {
        return new ResResult().setStatus(ResResultStatus.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE).setData(data);
    }

    public static ResResult success(String message, Object data) {
        return new ResResult().setStatus(ResResultStatus.SUCCESS).setMessage(message).setData(data);
    }

    public static ResResult fail(String message) {
        return new ResResult().setStatus(ResResultStatus.FAIL).setMessage(message);
    }

    public static ResResult fail(String message, Object data) {
        return new ResResult().setStatus(ResResultStatus.FAIL).setMessage(message).setData(data);
    }

    public static ResResult fail(Object data) {
        return new ResResult().setStatus(ResResultStatus.FAIL).setMessage(DEFAULT_SUCCESS_MESSAGE).setData(data);
    }

    public ResResult setStatus(ResResultStatus resultCode) {
        this.status = resultCode.status;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ResResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResResult setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
