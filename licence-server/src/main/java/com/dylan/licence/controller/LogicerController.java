package com.dylan.licence.controller;

import com.dylan.blog.entity.Article;
import com.dylan.blog.service.ArticleService;
import com.dylan.blog.vo.ArticleVO;
import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.licence.config.HomeDataLoader;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Classname LogicerController
 * @Description LogicerController
 * @Date 8/28/2023 6:33 PM
 */
@RestController
@RequestMapping("logicer")
public class LogicerController {

    @Resource
    private HomeDataLoader homeDataLoader;

    @DubboReference(version = "1.0.0")
    private ArticleService articleService;

    /**
     * 分页获取用户组
     * @return
     */
    @GetMapping("get-home-data")
    public HttpResult getPagedGroup() {
        Map<String, Object> homeDataMap = new HashMap<>();
        List<ArticleVO> articleVOS = articleService.getArticleList();
        String defaultTitle = "LOGICER";
        homeDataMap.put("title", Objects.isNull(homeDataLoader.getTitle()) ? defaultTitle : homeDataLoader.getTitle());
        homeDataMap.put("articles", articleVOS);
        return DataResult.success().data(homeDataMap).build();
    }

}
