package com.dylan.blog.entity.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 输入参数
 * @author Dylan
 */
@Data
public class ArticleDto {
    /**
     * 文件本体
     */
    private MultipartFile file;
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
     * 是否进行展示(作者)
     */
    private Integer isLock;

    /**
     * 文件发送到哪去
     */
    private String sendPlace;

    /**
     * 作者id
     */
    private int authorId;
    /**
     * 创建时间
     */
    private String createTime;
}
