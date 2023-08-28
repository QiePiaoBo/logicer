package com.dylan.licence.controller;

import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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

    @Autowired
    private Environment environment;

    @Value("${logicer.home.data.title: 学海无涯}")
    private String homeDataTitle;

    @Value("#{'${logicer.home.data.favors: Java,React,Harmony,Esp32,TCP,Debian CentOS Ubuntu}'.split(',')}")
    private List<String> favors;

    @Value("#{${logicer.home.data.proverbs: {first:'枕月绾袖临风 扣舷独饮千钟', second:'不见韶华白首 浮生一梦从容'}}}")
    private Map<String, String> proverbs;

    /**
     * 分页获取用户组
     * @return
     */
    @GetMapping("get-home-data")
    public HttpResult getPagedGroup() {
        Map<String, Object> homeDataMap = new HashMap<>();
        homeDataMap.put("title", homeDataTitle);
        homeDataMap.put("favors", favors);
        homeDataMap.put("proverbs", proverbs);
        return DataResult.success().data(homeDataMap).build();
    }

}
