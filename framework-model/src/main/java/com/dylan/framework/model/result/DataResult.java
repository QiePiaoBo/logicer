package com.dylan.framework.model.result;


import com.dylan.framework.model.info.Message;
import com.dylan.framework.model.info.Status;

/**
 * @author Dylan
 * @description 数据相关的result
 */
public class DataResult extends HttpResult{
    /**
     * 成功
     * @return
     */
    public static Builder success(){
        return DataResult.getBuilder(Status.SUCCESS.getStatus(), Message.SUCCESS.getMsg());
    }

    /**
     * 成功  自定义原因
     * @return
     */
    public static Builder success(Long status, String msg){
        return DataResult.getBuilder(status, msg);
    }

    /**
     * 失败
     * @return
     */
    public static Builder fail(){
        return DataResult.getBuilder(Status.ERROR_BASE.getStatus(), Message.ERROR.getMsg());
    }

    /**
     * 失败 自定义原因
     * @return
     */
    public static Builder fail(Long status, String msg){
        return DataResult.getBuilder(status, msg);
    }

    /**
     * 获取Builder
     * @return
     */
    public static Builder getBuilder(Long status, String msg){
        return new Builder(status, msg);
    }

    /**
     * 获取Builder
     * @return
     */
    public static Builder getBuilder(){
        return DataResult.getBuilder(Status.SUCCESS.getStatus(), Message.SUCCESS.getMsg());
    }

    DataResult(Builder builder){
        setStatus(builder.status);
        setMessage(builder.message);
        setData(builder.data);
    }

    public static class Builder {

        private final long status;

        private final String message;

        private Object data;

        public Builder(long status, String message){
            this.status = status;
            this.message = message;
        }

        public Builder() {
            this.status = Status.SUCCESS.getStatus();
            this.message = Message.SUCCESS.getMsg();
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public DataResult build(){
            return new DataResult(this);
        }
    }

}
