package com.dylan.framework.model.exception;


import com.dylan.framework.model.info.Message;
import com.dylan.framework.model.info.Status;

/**
 * @author Dylan
 * @Date : Created in 9:53 2021/3/12
 * @Description : 基础异常类
 * @Function :
 */
public class MyException extends RuntimeException{

    /**
     * 设置默认错误信息
     */
    private String errorMsg = Message.ERROR.getMsg();

    /**
     * 设置默认错误码
     */
    private final long errorCode = Status.ERROR_BASE.getStatus();

    public MyException() {
    }

    /**
     * 允许自定义异常内容
     * @param message
     */
    public MyException(String message) {
        super(message);
        this.errorMsg = message;
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
        this.errorMsg = message;
    }

    public MyException(Throwable cause) {
        super(cause);
    }

    public MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorMsg = message;
    }

    public String getErrorMsg(){
        return errorMsg;
    }

    public Long getErrorCode(){
        return errorCode;
    }
}
