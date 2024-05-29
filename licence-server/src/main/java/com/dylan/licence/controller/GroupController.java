package com.dylan.licence.controller;

import com.dylan.framework.model.page.MyPage;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.licence.model.dto.GroupDTO;
import com.dylan.licence.service.GroupService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.Resource;

/**
 * @Classname GroupController
 * @Description GroupController
 * @Date 5/10/2022 4:31 PM
 */
@RestController
@RequestMapping("group")
public class GroupController {

    @Resource
    private GroupService groupService;

    /**
     * 分页获取用户组
     * @param page
     * @param limit
     * @return
     */
    @GetMapping
    public HttpResult getPagedGroup(@Param("page") Integer page, @Param("limit") Integer limit){
        MyPage myPage = new MyPage(page, limit);
        return groupService.getPagedGroup(myPage);
    }

    /**
     * 创建用户组
     * @param groupDTO
     * @return
     */
    @PostMapping
    public HttpResult createGroup(@RequestBody GroupDTO groupDTO){
        return groupService.createGroup(groupDTO);
    }

    /**
     * 根据id获取用户组
     * @param id
     * @return
     */
    @GetMapping("/{id:\\d+}")
    public HttpResult getById(@PathVariable Integer id){
        return groupService.getById(id);
    }

    /**
     * 根据id删除用户组
     * @param id
     * @return
     */
    @DeleteMapping("/{id:\\d+}")
    public HttpResult deleteById(@PathVariable Integer id){
        return groupService.deleteById(id);
    }

    /**
     * 根据id更新用户组信息
     * @param groupDTO
     * @return
     */
    @PatchMapping
    public HttpResult updateById(@RequestBody GroupDTO groupDTO){
        return groupService.updateById(groupDTO);
    }
}


