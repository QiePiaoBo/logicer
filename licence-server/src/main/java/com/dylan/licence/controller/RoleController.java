package com.dylan.licence.controller;


import com.dylan.framework.model.page.MyPage;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.licence.model.dto.RoleDTO;
import com.dylan.licence.service.RoleAccessService;
import com.dylan.licence.service.RoleService;
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
 * @Classname RoleController
 * @Description RoleController
 * @Date 5/9/2022 11:37 AM
 */
@RequestMapping("role")
@RestController
public class RoleController {

    @Resource
    private RoleService roleService;
    @Resource
    private RoleAccessService roleAccessService;

    /**
     * 分页获取角色
     * @param page
     * @param limit
     * @return
     */
    @GetMapping
    public HttpResult getRoleList(@Param("page") Integer page, @Param("limit") Integer limit){
        MyPage myPage = new MyPage(page, limit);
        return roleService.selectRoleList(myPage);
    }

    /**
     * 创建角色
     * @param roleDTO
     * @return
     */
    @PostMapping
    public HttpResult create(@RequestBody RoleDTO roleDTO){
        return roleService.create(roleDTO);
    }

    /**
     * 根据id获取角色
     */
    @GetMapping("/{id:\\d+}")
    public HttpResult getById(@PathVariable Integer id){
        return roleService.getById(id);
    }

    /**
     * 根据id删除角色
     */
    @DeleteMapping("/{id:\\d+}")
    public HttpResult deleteById(@PathVariable Integer id){
        return roleService.deleteById(id);
    }

    /**
     * 根据id修改角色
     */
    @PatchMapping
    public HttpResult updateById(@RequestBody RoleDTO roleDTO){
        return roleService.updateById(roleDTO);
    }

    @GetMapping("get-accesses")
    public HttpResult getAccesses4Role(@Param("id") Integer id){
        return roleAccessService.getAccesses4Role(id);
    }

}
