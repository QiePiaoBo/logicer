package com.dylan.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Article)实体类
 *
 * @author Dylan
 * @since 2020-06-14 20:24:19
 */
@Data
@TableName("article")
public class Article implements Serializable {
    private static final long serialVersionUID = -66199659751436277L;
    /**
    * 文章主键
    */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
    * 文章名
    */
    private String fileName;
    /**
    * 文章二级标题
    */
    private String subTitle;
    /**
    * 文章内容梗概
    */
    private String description;
    /**
    * 文章类型
    */
    private String fileType;
    /**
    * 文章路径
    */
    private String filePath;
    /**
    * 作者id
    */
    private Integer userId;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    *  是否禁止访问(管理员)
    */
    private Integer isDel;
    /**
    * 是否进行展示(作者)
    */
    private Integer isLock;
}