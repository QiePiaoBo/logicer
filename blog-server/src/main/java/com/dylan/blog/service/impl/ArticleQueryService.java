package com.dylan.blog.service.impl;

import com.dylan.blog.entity.Article;
import com.dylan.blog.entity.dto.ArticleDto;
import com.dylan.blog.mapper.ArticleMapper;
import com.dylan.framework.model.info.Message;
import com.dylan.framework.model.info.Status;
import com.dylan.framework.model.result.DataResult;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Dylan
 * @Date : Created in 16:42 2020/10/14
 * @Description :
 * @Function :
 */
@Service
public class ArticleQueryService {
    private static final MyLogger logger = MyLoggerFactory.getLogger(ArticleQueryService.class);

    @Autowired
    ArticleMapper articleMapper;

    public DataResult queryArticleTime(@RequestBody ArticleDto articleDto){
        DataResult dataResult;
        Set<String> countSet = new HashSet<>();
        List<Date> queryResult;
        int authorId = articleDto.getAuthorId();
        // 获取这个作者的所有文章的发布时间并对时间进行格式化 最终去重后返回
        queryResult = articleMapper.queryWithCreateTime(authorId);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (Date date : queryResult){
            String dateString = formatter.format(date);
            countSet.add(dateString);
        }
        List<String> countResult = new ArrayList<>(countSet);
        dataResult = DataResult.getBuilder(Status.SUCCESS.getStatus(), Message.SUCCESS.getMsg())
                .data(countResult)
                .build();
        return dataResult;
    }
    public DataResult queryArticleByTime(@RequestBody ArticleDto articleDto){
        DataResult dataResult;
        if ("0".equals(String.valueOf(articleDto.getAuthorId())) || null == articleDto.getCreateTime()){
            dataResult = DataResult.getBuilder(Status.PARAM_NEED.getStatus(), Message.PARAM_NEED.getMsg())
                    .build();
            return dataResult;
        }
        List<Article> queryResult = articleMapper.queryArticlesInOneDay(articleDto.getCreateTime(), articleDto.getAuthorId());
        dataResult = DataResult.getBuilder(Status.SUCCESS.getStatus(), Message.SUCCESS.getMsg())
                .data(queryResult)
                .build();
        return dataResult;
    }

}
