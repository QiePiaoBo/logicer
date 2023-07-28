package com.dylan.framework.settings;

/**
 * size设置枚举类
 * @author Dylan 
 */
public enum Size {
    /**
     * 文件上传上限 50M
     */
    UPLOAD_FILE_MAX_SIZE (52428800);

    private long size;

    Size(long size){
        this.size = size;
    }

    public long getSize(){
        return size;
    }
}
