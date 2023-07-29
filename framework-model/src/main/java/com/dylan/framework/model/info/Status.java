package com.dylan.framework.model.info;

/**
 * 状态码枚举类
 * @author Dylan
 */
public enum  Status {
    /**
     * 成功
     */
    SUCCESS (100000),
    /**
     * 文件过大
     */
    OUTOF_SIZE_ERROR(100001),
    /**
     * 入库失败
     */
    INSERT_ERROR (100002),
    /**
     * 查询失败
     */
    QUERY_ERROR(100003),
    /**
     * 更新失败
     */
    UPDATE_ERROR (100004),
    /**
     * 权限不足
     */
    PERMISSION_ERROR (100005),
    /**
     * 删除失败
     */
    DELETE_ERROR(100006),
    /**
     * 上传失败
     */
    UPLOAD_ERROR(100007),
    /**
     * 文件为空
     */
    FILE_NEED(100008),
    /**
     * 参数缺失
     */
    PARAM_NEED(100009),
    /**
     * 用户名不存在
     */
    USER_NOT_FOUND(100010),
    /**
     * 密码错误
     */
    ERROR_PASSWORD(100011),

    /**
     * 未确定错误，基础错误
     */
    ERROR_BASE(100012),
    /**
     * 用户已经存在
     */
    USER_EXISTS(100013),

    /**
     * 未登录
     */
    NOT_LOGIN(100014)
    ;

    private long status;

    Status(Integer statusNum){
        this.status = statusNum;
    }

    public long getStatus(){
        return status;
    }
}
