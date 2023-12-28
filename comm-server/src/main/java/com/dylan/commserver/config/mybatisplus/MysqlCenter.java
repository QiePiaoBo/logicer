package com.dylan.commserver.config.mybatisplus;

/**
 * @author Dylan
 * @Date : 2022/3/27 - 17:09
 * @Description :
 * @Function :
 */
public class MysqlCenter {

    private String url;

    private String driverClassName;

    private String userName;

    private String password;

    private Integer idleTimeout;

    private String connectionTestQuery;

    public MysqlCenter(String url, String driverClassName, String userName, String password) {
        this.url = url;
        this.driverClassName = driverClassName;
        this.userName = userName;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Integer idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public String getConnectionTestQuery() {
        return connectionTestQuery;
    }

    public void setConnectionTestQuery(String connectionTestQuery) {
        this.connectionTestQuery = connectionTestQuery;
    }
}
