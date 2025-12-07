package com.lx.blog.common.exception.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.lx.blog.common.constants.ResultCode;
import com.lx.blog.common.exception.BaseException;
import com.lx.blog.common.exception.ForbiddenException;
import com.lx.blog.common.exception.NotFoundException;
import com.lx.blog.common.exception.UnAuthorizedException;
import com.lx.blog.common.response.Result;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ConstraintViolation;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import com.lx.blog.common.utils.I18nUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author LX
 * @date 2025/11/13
 * @description 全局异常捕获
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {}

    /**
     * 捕获基础异常
     * @param e 基础异常
     * @return 异常信息
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Result<Object>> handleBaseException(BaseException e) {
        log.error("handleBaseException: {}", e.getMessage(),e);
        return ResponseEntity
                .status(e.getErrcode())
                .body(Result.error(e.getErrcode(), e.getMessage()));
    }

    /**
     * 捕获资源未找到异常
     * @param e 资源未找到异常
     * @return 异常信息
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Result<Object>> handleNotFoundException(NotFoundException e) {
        log.error("handleNotFoundException: {}", e.getMessage(),e);
        return ResponseEntity
                .status(e.getErrcode())
                .body(Result.error(e.getErrcode(), e.getMessage()));
    }

    /**
     * 捕获未授权异常
     * @param e 未认证异常
     * @return 异常信息
     */
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<Result<Object>> handleUnAuthorizedException(UnAuthorizedException e) {
        log.error("handleUnAuthorizedException: {}", e.getMessage(),e);
        return ResponseEntity
                .status(e.getErrcode())
                .body(Result.error(e.getErrcode(), e.getMessage()));
    }

    /**
     * 捕获禁止访问异常
     * @param e 禁止访问异常
     * @return 异常信息
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Result<Object>> handleForbiddenException(ForbiddenException e) {
        log.error("handleForbiddenException: {}", e.getMessage(),e);
        return ResponseEntity
                .status(e.getErrcode())
                .body(Result.error(e.getErrcode(), e.getMessage()));
    }

    /**
     * 捕获未登录异常
     * @param e 未登录异常
     * @return 异常信息
     */
    @ExceptionHandler(NotLoginException.class)
    public ResponseEntity<Result<Object>> handleNotLoginException(NotLoginException e) {
        log.error("handleNotLoginException: {}", e.getMessage());
        String msg = I18nUtils.from(ResultCode.UNAUTHORIZED);
        return ResponseEntity
                .status(ResultCode.UNAUTHORIZED.getErrCode())
                .body(Result.error(ResultCode.UNAUTHORIZED.getErrCode(), msg));
    }

    /**
     * Validation参数校验错误异常
     * @param e 参数校验错误异常
     * @return 异常信息
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Result<Object>> handleException(Exception e) {
        log.error("handleException: {}", e.getMessage(),e);
        if (e instanceof ConstraintViolationException cve) {
            String msg = cve.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity
                    .status(ResultCode.BAD_REQUEST.getErrCode())
                    .body(Result.error(ResultCode.BAD_REQUEST.getErrCode(), msg));
        }
        return ResponseEntity
                .status(ResultCode.INTERNAL_SERVER_ERROR.getErrCode())
                .body(Result.error(ResultCode.INTERNAL_SERVER_ERROR.getErrCode(), e.getMessage()));
    }
}
