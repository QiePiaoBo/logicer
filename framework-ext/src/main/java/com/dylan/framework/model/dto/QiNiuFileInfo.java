package com.dylan.framework.model.dto;

/**
 * @Classname QiNiuFileInfo
 * @Description QiNiuFileInfo
 * @Date 9/19/2022 5:01 PM
 */
public class QiNiuFileInfo {

    private String key;

    private Long fsize;

    private String hash;

    private String bucket;

    public QiNiuFileInfo() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getFsize() {
        return fsize;
    }

    public void setFsize(Long fsize) {
        this.fsize = fsize;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"key\":").append(key == null ? "" : "\"")
                .append(key).append(key == null ? "" : "\"");
        sb.append(",\"fsize\":")
                .append(fsize);
        sb.append(",\"hash\":").append(hash == null ? "" : "\"")
                .append(hash).append(hash == null ? "" : "\"");
        sb.append(",\"bucket\":").append(bucket == null ? "" : "\"")
                .append(bucket).append(bucket == null ? "" : "\"");
        sb.append('}');
        return sb.toString();
    }
}
