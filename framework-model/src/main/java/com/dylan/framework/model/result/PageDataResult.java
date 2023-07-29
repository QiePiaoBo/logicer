package com.dylan.framework.model.result;


import com.dylan.framework.model.info.Message;
import com.dylan.framework.model.info.Status;

/**
 * @author Dylan
 * @description 数据相关的result
 */
public class PageDataResult extends HttpResult {

    private final long page;

    private final long size;

    private final long total;

    /**
     * 成功
     * @return
     */
    public static Builder success(){
        return getBuilder(Status.SUCCESS.getStatus(), Message.SUCCESS.getMsg());
    }

    /**
     * 成功  自定义原因
     * @return
     */
    public static Builder success(Long status, String msg){
        return getBuilder(status, msg);
    }

    /**
     * 失败
     * @return
     */
    public static Builder failed(){
        return getBuilder(Status.ERROR_BASE.getStatus(), Message.ERROR.getMsg());
    }

    /**
     * 失败 自定义原因
     * @return
     */
    public static Builder failed(Long status, String msg){
        return getBuilder(status, msg);
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
        return getBuilder(Status.SUCCESS.getStatus(), Message.SUCCESS.getMsg());
    }

    private PageDataResult(Builder builder){
        setStatus(builder.status);
        setMessage(builder.message);
        setData(builder.data);
        page = builder.page;
        size = builder.size;
        total = builder.total;
    }

    public long getPage() {
        return page;
    }

    public long getSize() {
        return size;
    }

    public long getTotal() {
        return total;
    }

    public static class Builder {

        private final long status;

        private final String message;

        private Object data;

        private long page;

        private long size;

        private long total;

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

        public Builder page(long page){
            this.page = page;
            return this;
        }

        public Builder size(long size){
            this.size = size;
            return this;
        }

        public Builder total(long total){
            this.total = total;
            return this;
        }

        public PageDataResult build(){
            return new PageDataResult(this);
        }
    }

}
