package com.lx.blog.common.exception;

import com.lx.blog.common.constants.ResultCode;
import com.lx.blog.common.utils.I18nUtils;
import lombok.Getter;

/**
 * @author 李旭
 * @date 2025/11/13
 * @description 基础异常类
 */
public class BaseException extends RuntimeException {

    /**
     * 错误码
     */
    @Getter
    private final Integer errcode;

    /**
     * 默认构造函数
     */
    public BaseException() {
        super(I18nUtils.from(ResultCode.UNKNOWN_ERROR));
        this.errcode = ResultCode.UNKNOWN_ERROR.getErrCode();
    }

     /**
      * 构造函数
      * @param resultCode 错误码
      */
    public BaseException(ResultCode resultCode) {
        super(I18nUtils.from(resultCode));
        this.errcode = resultCode.getErrCode();
    }

    /**
     * 构造函数
     * @param message 错误信息
     */
    public BaseException(String message) {
        super(message);
        this.errcode = ResultCode.UNKNOWN_ERROR.getErrCode();
    }

    /**
     * 构造函数
     * @param resultCode 错误码
     * @param errMsg 错误信息
     */
    public BaseException(ResultCode resultCode, String errMsg) {
        super(errMsg);
        this.errcode = resultCode.getErrCode();
    }

    /**
     * 构造函数
     * @param errCode 错误码
     * @param errMsg 错误信息
     */
    public BaseException(Integer errCode, String errMsg) {
        super(errMsg);
        this.errcode = errCode;
    }

    /**
     * 构造函数
     * @param message 错误信息
     * @param cause 异常原因
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.errcode = ResultCode.UNKNOWN_ERROR.getErrCode();
    }



     /**
      * 构造函数
      * @param errCode 错误码
      * @param errMsg 错误信息
      * @param cause 异常原因
      */
    public BaseException(Integer errCode, String errMsg, Throwable cause) {
        super(errMsg, cause);
        this.errcode = errCode;
    }

    /**
     * 构造函数
     * @param errCode 错误码
     * @param cause 异常原因
     */
    public BaseException(Integer errCode, Throwable cause) {
        super(cause);
        this.errcode = errCode;
    }

    /**
     * 构造函数
     * @param errCode 错误码
     * @param errMsg 错误信息
     * @param cause 异常原因
     * @param args 错误信息参数
     */
    public BaseException(Integer errCode, String errMsg, Throwable cause, Object... args) {
        super(String.format(errMsg, args), cause);
        this.errcode = errCode;
    }

    /**
     * 构造函数
     * @param resultCode 错误码
     * @param cause 异常原因
     * @param args 错误信息参数
     */
    public BaseException(ResultCode resultCode, Throwable cause, Object... args) {
        super(I18nUtils.from(resultCode, args), cause);
        this.errcode = resultCode.getErrCode();
    }

     /**
      * 构造函数
      * @param resultCode 错误码
      * @param args 错误信息参数
      */
    public BaseException(ResultCode resultCode, Object... args) {
        super(I18nUtils.from(resultCode, args));
        this.errcode = resultCode.getErrCode();
    }

    /**
     * 构造函数
     * @param errCode 错误码
     * @param errMsg 错误信息
     * @param args 错误信息参数
     */
    public BaseException(Integer errCode, String errMsg, Object... args) {
        super(String.format(errMsg, args));
        this.errcode = errCode;
    }

    /**
     * 构造函数
     * @param errCode 错误码
     * @param args 错误信息参数
     */
    public BaseException(Integer errCode, Object... args) {
        super(I18nUtils.from(ResultCode.UNKNOWN_ERROR, args));
        this.errcode = errCode;
    }

    /**
     * 构造函数
     * @param resultCode 错误码
     * @param errMsg 错误信息
     * @param cause 异常原因
     * @param args 错误信息参数
     */
    public BaseException(ResultCode resultCode, String errMsg, Throwable cause, Object... args) {
        super(String.format(errMsg, args), cause);
        this.errcode = resultCode.getErrCode();
    }

}
