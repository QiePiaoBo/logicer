package com.dylan.licence.controller;

import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname LogicerController
 * @Description LogicerController
 * @Date 8/28/2023 6:33 PM
 */
@RestController
@RequestMapping("logicer")
public class LogicerController {

    @Value("${logicer.home.data.title: 学海无涯}")
    private String homeDataTitle;

    @Value("#{'${logicer.home.data.favors:}'.split(',')}")
    private List<String> favors;

    @Value("#{${logicer.home.data.proverbs:{key1:'default1', key2:'default2'}}}")
    private Map<String, String> proverbs;

    /**
     * 分页获取用户组
     * @return
     */
    @GetMapping("get-home-data")
    public HttpResult getPagedGroup() throws JsonProcessingException {
        Map<String, String> homeDataMap = new HashMap<>();
        homeDataMap.put("title", "学海无涯");
        homeDataMap.put("favors", "[]");
        homeDataMap.put("proverbs", new ObjectMapper().writeValueAsString(proverbs));
        return DataResult.success().data(homeDataMap).build();
    }

}
