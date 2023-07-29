package com.dylan.file.controller;


import com.dylan.file.service.QiNiuService;
import com.dylan.framework.model.result.DataResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Classname QiNiuController
 * @Description QiNiuController
 * @Date 9/15/2022 5:21 PM
 */
@RestController
@RequestMapping("sync")
public class SyncQiNiuController {

    @Resource
    private QiNiuService qiNiuService;

    @RequestMapping("qiniu")
    public DataResult upload2Qiniu(){
        qiNiuService.getQiNiuLog();
        return DataResult.getBuilder().build();
    }

}
