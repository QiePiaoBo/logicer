package com.dylan.licence.controller;

import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.licence.config.HomeDataLoader;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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

    /**
     * 分页获取用户组
     * @return
     */
    @GetMapping("get-home-data")
    public HttpResult getPagedGroup() {
        Map<String, Object> homeDataMap = new HashMap<>();
        String defaultTitle = "LOGICER";
        List<String> defaultFavors = Arrays.asList("P1","P2","P3","P4","P5","P6");
        Map<String,String> defaultProverbs = Stream.of(new String[][] {
                { "first", "天" },
                { "second", "地" },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
        homeDataMap.put("title", Objects.isNull(homeDataLoader.getTitle()) ? defaultTitle : homeDataLoader.getTitle());
        homeDataMap.put("favors", Objects.isNull(homeDataLoader.getFavors()) ? defaultFavors : homeDataLoader.getFavors());
        homeDataMap.put("proverbs", Objects.isNull(homeDataLoader.getProverbs()) ? defaultProverbs : homeDataLoader.getProverbs());
        return DataResult.success().data(homeDataMap).build();
    }

}
