package com.reven.core;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static ApiResult genSuccessResult() {
        return new ApiResult()
                .setCode(ApiResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static ApiResult genSuccessResult(Object data) {
        return new ApiResult()
                .setCode(ApiResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }
    public static ApiResult genSuccessResult(String message,Object data) {
    	return new ApiResult()
    			.setCode(ApiResultCode.SUCCESS)
    			.setMessage(message)
    			.setData(data);
    }

    public static ApiResult genFailResult(String message) {
        return new ApiResult()
                .setCode(ApiResultCode.FAIL)
                .setMessage(message);
    }
    public static ApiResult genSuccessResult(String message) {
    	return new ApiResult()
    			.setCode(ApiResultCode.SUCCESS)
    			.setMessage(message);
    }
    public static ApiResult genFailResult(String message,Object data) {
    	return new ApiResult()
    			.setCode(ApiResultCode.FAIL)
    			.setMessage(message)
    			.setData(data);
    }
    public static ApiResult genFailResult(Object data) {
        return new ApiResult()
                .setCode(ApiResultCode.FAIL)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }
}
