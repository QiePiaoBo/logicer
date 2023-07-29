package com.dylan.blog.service;


import com.dylan.blog.dto.ArticleDto;
import com.dylan.framework.model.result.DataResult;

/**
 * 文件类接口
 * @author Dylan
 */
public interface ArticleFileService {

    /**
     * 文件上传
     * @param articleDto
     * @param uploadWhere
     * @return
     */
    DataResult uploadFile(ArticleDto articleDto, String uploadWhere);

}
