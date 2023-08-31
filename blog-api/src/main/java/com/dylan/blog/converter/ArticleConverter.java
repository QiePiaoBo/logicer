package com.dylan.blog.converter;

import com.dylan.blog.entity.Article;
import com.dylan.blog.vo.ArticleVO;

/**
 * @Classname ArticleConverter
 * @Description ArticleConverter
 * @Date 8/31/2023 6:08 PM
 */
public class ArticleConverter {

    public static ArticleVO getArticleVO(Article article) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setId(article.getId());
        articleVO.setDescription(article.getDescription());
        articleVO.setTitle(article.getTitle());
        articleVO.setSubTitle(article.getSubTitle());
        articleVO.setCreateTime(article.getCreateTime());
        return articleVO;
    }


}
