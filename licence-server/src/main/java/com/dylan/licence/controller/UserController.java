package com.dylan.licence.controller;

import com.dylan.framework.annos.AdminPermission;
import com.dylan.framework.model.info.Message;
import com.dylan.framework.model.info.Status;
import com.dylan.framework.model.page.MyPage;
import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.licence.model.dto.UserDTO;
import com.dylan.licence.service.IUserAccessService;
import com.dylan.licence.service.IUserRoleService;
import com.dylan.licence.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author Dylan
 * @since 2020-05-24
 * 用户管理中心
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private IUserRoleService userRoleService;

    @Resource
    private IUserAccessService userAccessService;

    /**
     * 获取所有用户
     * @param page
     * @param limit
     * @return
     */
    @AdminPermission(userType = 1)
    @GetMapping
    public HttpResult getUsers(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        if (page == null || limit == null){
            return DataResult.getBuilder(Status.PARAM_NEED.getStatus(), Message.PARAM_NEED.getMsg()).data(new ArrayList<>()).build();
        }
        MyPage myPage = new MyPage(page, limit);
        return userService.selectUserList(myPage);
    }

    /**
     * 获取一个用户
     * @param id
     * @return
     */
    @AdminPermission
    @GetMapping("/{id:\\d+}")
    public HttpResult getUserById(@PathVariable Integer id){
        return userService.selectOne(UserDTO.builder().id(id).build());
    }

    /**
     * 添加一个用户
     * @param userDTO
     * @return
     */
    @PostMapping
    public HttpResult create(@RequestBody UserDTO userDTO){
        return userService.addUser(userDTO);
    }

    /**
     * 删除一个用户
     * @param id
     * @return
     */
    @AdminPermission(userType = 1)
    @DeleteMapping("/{id:\\d+}")
    public HttpResult deleteUser(@PathVariable Integer id){
        return userService.deleteOne(UserDTO.builder().id(id).build());
    }

    /**
     * 修改一个用户
     * @param userDTO
     * @return
     */
    @AdminPermission
    @PatchMapping
    public HttpResult exchange(@RequestBody UserDTO userDTO){
        return userService.exchange(userDTO);
    }

    /**
     * 根据userId获取该用户的角色列表
     * @param id
     * @return
     */
    @GetMapping("get-roles")
    public HttpResult getRoles4User(@Param("id") Integer id){
        return userRoleService.getRoleList4User(id);
    }

    /**
     * 根据userId获取所有的权限
     * @param id
     * @return
     */
    @AdminPermission
    @GetMapping("get-accesses")
    public HttpResult getAccesses4User(@Param("id") Integer id){
        return userAccessService.getAccesses4User(id);
    }

    /**
     * 查看当前用户是否有某url的权限
     * @param url
     * @return
     */
    @AdminPermission
    @GetMapping("check-access")
    public HttpResult checkHavePermission(@Param("uri") String url){
        return userAccessService.hasPermission(url);
    }
}
