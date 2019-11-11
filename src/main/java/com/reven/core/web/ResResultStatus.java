package com.reven.core.web;

/**
 * @ClassName: ResResultStatus
 * @Description: 响应码枚举，参考HTTP状态码的语义
 * @author reven
 * @date 2018年8月28日
 */
public enum ResResultStatus {
    /**
     * 成功
     */
    SUCCESS(200),
    /**
     * 失败
     */
    FAIL(400),
    /**
     * 接口不存在
     */
    NOT_FOUND(404),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500),

    /**
     * 用户未登录
     */
    NOT_LOGIN(401),

    /**
     * 没有权限
     */
    NO_RIGHT(403);

    public int status;

    ResResultStatus(int status) {
        this.status = status;
    }
}
