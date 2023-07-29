package com.dylan.blog.controller;


import com.dylan.blog.entity.Article;
import com.dylan.blog.dto.ArticleDto;
import com.dylan.blog.service.ArticleFileService;
import com.dylan.blog.service.ArticleService;
import com.dylan.framework.annos.AdminPermission;
import com.dylan.framework.model.info.Message;
import com.dylan.framework.model.info.Status;
import com.dylan.framework.model.result.DataResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * (Article)表控制层
 *
 * @author Dylan
 * @since 2020-06-14 20:24:19
 */
@RestController
@RequestMapping("article")
public class ArticleController {
    /**
     * 文章管理服务
     */
    @Resource
    private ArticleService articleService;
    /**
     * 文件上传服务
     */
    @Resource
    ArticleFileService articleFileService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @RequestMapping("select")
    public DataResult selectOne(@RequestParam Integer id) {
        return DataResult.success().data(articleService.queryById(id)).build();
    }

    /**
     * 获取所有的文章数据
     * @param article
     * @return
     */
    @RequestMapping("all")
    public DataResult getArticles(@RequestBody Article article){
        return articleService.queryRight(article);
    }

    /**
     * 更新文章
     * @param article
     * @return
     */
    @AdminPermission
    @RequestMapping("update")
    public DataResult updateArticle(@RequestBody Article article){
        return articleService.update(article);
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @AdminPermission
    @RequestMapping("delete")
    public DataResult deleteById(@RequestParam Integer id){
        return articleService.deleteById(id);
    }

    /**
     * 文件上传
     * @param articleDto
     * @return
     * @throws IOException
     */
    @AdminPermission
    @RequestMapping("upload")
    public DataResult uploadFile(@ModelAttribute ArticleDto articleDto) throws IOException {
        if (articleDto.getFile()==null){
            return DataResult.getBuilder(Status.FILE_NEED.getStatus(), Message.FILE_NEED.getMsg()).build();
        }
        DataResult dataResult = null;
        if (null == articleDto.getSendPlace() || "qiniu".equals(articleDto.getSendPlace())){
            dataResult = articleFileService.uploadFile(articleDto, "qiniu");
        }else {
            dataResult = articleFileService.uploadFile(articleDto, articleDto.getSendPlace());
        }
        return dataResult;
    }
}