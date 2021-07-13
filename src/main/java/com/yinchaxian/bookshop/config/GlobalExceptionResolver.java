package com.yinchaxian.bookshop.config;

import com.yinchaxian.bookshop.http.ErrorMessage;
import com.yinchaxian.bookshop.http.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: zhang
 * @date: 2021/7/13 9:09
 * @description:
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionResolver {
    /**
     * 处理shiro框架异常
     * @param e shiro异常
     * @return 异常信息
     */
    @ExceptionHandler(ShiroException.class)
    public Result doHandleShiroException(ShiroException e) {
        log.error(e.getMessage());
        if (e instanceof UnknownAccountException) {
            return Result.error(ErrorMessage.loginError);
        } else if (e instanceof LockedAccountException) {
            return Result.error(ErrorMessage.accountError);
        } else if (e instanceof IncorrectCredentialsException) {
            return Result.error(ErrorMessage.loginError);
        } else if (e instanceof AuthorizationException) {
            return Result.error(ErrorMessage.authError);
        } else {
            return Result.error(ErrorMessage.unknownError);
        }
    }

    /**
     * 定义全局异常处理
     * @param e 运行时异常
     * @return 异常信息
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Result topException(RuntimeException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return Result.error(e.getMessage());
    }
}
