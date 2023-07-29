package com.dylan.licence.controller;


import com.dylan.framework.model.page.MyPage;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.licence.model.dto.AccessDTO;
import com.dylan.licence.service.IAccessService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Dylan
 * @Description AccessController
 * @Date : 2022/5/9 - 23:52
 */
@RestController
@RequestMapping("access")
public class AccessController {

    @Resource
    private IAccessService accessService;

    @GetMapping("test")
    public String getTest() {
        return "Hello World.";
    }

    /**
     * 分页获取access
     * @param page
     * @param limit
     * @return
     */
    @GetMapping
    public HttpResult getPagedAccess(@Param("page") Integer page, @Param("limit") Integer limit){
        MyPage myPage = new MyPage(page, limit);
        return accessService.getPagedAccess(myPage);
    }

    @PostMapping
    public HttpResult createAccess(@RequestBody AccessDTO accessDTO){
        return accessService.createAccess(accessDTO);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id:\\d+}")
    public HttpResult getById(@PathVariable Integer id){
        return accessService.getById(id);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id:\\d+}")
    public HttpResult deleteById(@PathVariable Integer id){
        return accessService.deleteById(id);
    }

    /**
     * 根据id更新
     * @param accessDTO
     * @return
     */
    @PatchMapping
    public HttpResult updateById(@RequestBody AccessDTO accessDTO){
        return accessService.updateById(accessDTO);
    }
}
