package com.dylan.framework.model.result;


import java.io.Serializable;

/**
 * @author Dylan
 * @Date : Created in 10:51 2021/3/12
 * @Description : http相关的result
 * @Function :
 */
public class HttpResult implements Serializable {

    /**
     * 返回码
     */
    private long status;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回具体数据
     */
    private Object data;

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public HttpResult(long status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public HttpResult() {
    }

    @Override
    public String toString() {
        return "{" +
                "\"status\" : " + status +
                ", \"message\" : \"" + message + "\"" +
                ", \"data\" : " + data +
                '}';
    }
}
