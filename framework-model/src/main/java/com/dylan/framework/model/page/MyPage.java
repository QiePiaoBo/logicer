package com.dylan.framework.model.page;



import com.dylan.framework.model.exception.MyException;

import java.util.Objects;

/**
 * @author Dylan
 * @Date : Created in 14:35 2021/3/9
 * @Description : 分页类，根据传入参数计算开始条数 提供各种参数
 * @Function :
 */
public class MyPage {
    private Integer pageNo;

    private Integer pageSize;

    private Integer startNo;

    public MyPage() {
    }

    public MyPage(Integer pageNo, Integer pageSize) {
        this.pageNo = Objects.nonNull(pageNo) && pageNo > 0 ? pageNo : 1;
        this.pageSize = Objects.nonNull(pageSize) && pageSize > 0 ? pageSize : 3;
        this.startNo = (getPageNo() - 1) * getPageSize();
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo > 0 ? pageNo : 1;
        if (Objects.nonNull(pageSize)){
            this.startNo = (getPageNo() - 1) * getPageSize();
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize > 0 ? pageSize : 3;
        if (Objects.nonNull(pageNo)){
            this.startNo = (getPageNo() - 1) * getPageSize();
        }
    }

    public Integer getStartNo() {
        return startNo;
    }

    public void setStartNo(Integer startNo) {
        this.startNo = startNo;
    }

    /**
     * myPage对象完整
     * @return
     */
    public void checkValid(){
        if (!(Objects.nonNull(getPageNo()) && Objects.nonNull(getPageSize()) && Objects.nonNull(getStartNo()))){
            throw new MyException("Invalid myPage object : " + this);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer
                .append("PageNo = ")
                .append(getPageNo())
                .append(", ")
                .append("PageSize = ")
                .append(getPageSize())
                .append(", ")
                .append("StartNo = ")
                .append(getStartNo())
                .append(", ");
        return new String(stringBuffer);
    }
}