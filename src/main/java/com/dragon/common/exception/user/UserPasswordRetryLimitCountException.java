package com.dragon.common.exception.user;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2019/6/17 16:27
 * @description： 用户错误记数异常类
 * @modified By：
 * @version: 1.0.0
 */
public class UserPasswordRetryLimitCountException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitCountException(int retryLimitCount) {
        super("user.password.retry.limit.count", new Object[]{retryLimitCount});
    }
}
