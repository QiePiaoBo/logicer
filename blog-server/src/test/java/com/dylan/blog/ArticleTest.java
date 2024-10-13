package com.dylan.blog;

import com.dylan.blog.entity.Article;
import com.dylan.blog.mapper.ArticleMapper;
import com.dylan.blog.service.ArticleService;
import com.dylan.blog.vo.ArticleVO;
import com.dylan.framework.model.result.DataResult;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jakarta.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleTest {

    private static final MyLogger logger = MyLoggerFactory.getLogger(ArticleTest.class);

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleService articleService;

    @Test
    public void testGetArticleList() {

        List<ArticleVO> articleList = articleService.getArticleList();

        logger.info("res: {}", articleList);

    }

    @Test
    public void testQueryRight() {

        Article article = new Article();
        article.setId(1);
        DataResult dataResult = articleService.queryRight(article);

        logger.info("res: {}", dataResult);

    }

    @Test
    public void testQueryAll() {
        Article article = new Article();
        article.setDelFlag(0);
        article.setIsLock(0);
        List<Article> list = articleMapper.queryAll(article);
        logger.info("res: {}", list);
    }


}
