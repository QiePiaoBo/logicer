package com.dylan.licence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dylan.framework.model.entity.Person;
import com.dylan.framework.model.info.Message;
import com.dylan.framework.model.info.Status;
import com.dylan.framework.model.page.MyPage;
import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.framework.model.result.PageDataResult;
import com.dylan.framework.model.vo.PersonVo;
import com.dylan.framework.session.UserContext;
import com.dylan.framework.utils.PasswordService;
import com.dylan.framework.utils.Safes;
import com.dylan.licence.entity.User;
import com.dylan.licence.enumcenter.GroupEnum;
import com.dylan.licence.mapper.UserMapper;
import com.dylan.licence.model.dto.UserDTO;
import com.dylan.licence.model.vo.UserVO;
import com.dylan.licence.service.IUserService;
import com.dylan.licence.transformer.UserTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  用户服务实现类
 * </p>
 * @author Dylan
 * @since 2020-05-24
 */
@Slf4j
@Service
@RefreshScope
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private PasswordService passwordService;

    /**
     * 获取用户列表
     * @param page
     * @return
     */
    @Override
    public HttpResult selectUserList(MyPage page) {
        List<User> userList = userMapper.selectUserList(page);
        List<UserVO> userVOList = new ArrayList<>();
        Safes.of(userList).forEach(u -> {
            userVOList.add(UserTransformer.user2UserVo(u));
        });
        log.info("MyPage : {}", page);
        return PageDataResult
                .success()
                .data(userVOList)
                .page(page.getPageNo())
                .size(page.getPageSize())
                .total(userMapper.selectUserTotal())
                .build();
    }

    /**
     * 根据id username phone mail获取用户
     * @param userDTO
     * @return
     */
    @Override
    public HttpResult selectOne(UserDTO userDTO) {
        DataResult dataResult;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (userDTO.getId()!=null && userDTO.getId()!=0){
            queryWrapper.eq("id", userDTO.getId());
        }
        if (userDTO.getUserName()!=null&&userDTO.getUserName().length()>0){
            queryWrapper.eq("user_name", userDTO.getUserName());
        }
        if (userDTO.getUserPhone()!=null&&userDTO.getUserPhone().length()>0){
            queryWrapper.eq("user_phone", userDTO.getUserPhone());
        }
        User user = userMapper.selectOne(queryWrapper);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        if (userVO.getId()!=null && userVO.getId() > 0){
            dataResult = DataResult.getBuilder(Status.SUCCESS.getStatus(), Message.SUCCESS.getMsg()).data(userVO).build();
        }else {
            return null;
        }
        return dataResult;
    }

    /**
     * 添加用户
     * @param userDTO
     * @return
     */
    @Override
    public HttpResult addUser(UserDTO userDTO){
        DataResult dataResult;
        // 设置默认用户组
        userDTO.setUserGroup(GroupEnum.USER_GROUP.getGroupId());
        // 若未传入密码，则设置默认密码为123456
        if (userDTO.getUserPassword()==null || userDTO.getUserPassword().length()==0){
            userDTO.setUserPassword(passwordService.createPassword("123456"));
        }
        if (userDTO.getUserName()==null || userDTO.getUserName().length()==0){
            dataResult = DataResult.getBuilder(Status.INSERT_ERROR.getStatus(), Message.INSERT_ERROR.getMsg()).build();
            return dataResult;
        }
        if (userDTO.getUserPhone()==null || userDTO.getUserPhone().length()==0){
            dataResult = DataResult.getBuilder(Status.INSERT_ERROR.getStatus(), Message.INSERT_ERROR.getMsg()).build();
            return dataResult;
        }
        // 判断用户是否已经存在
        if (userMapper.selectCount(
                Wrappers.query(new User())
                        .eq("user_name", userDTO.getUserName())
                        .eq("user_phone", userDTO.getUserPhone())) > 0){
            return DataResult.getBuilder(Status.USER_EXISTS.getStatus(), Message.USER_EXISTS.getMsg()).data(userDTO).build();
        }
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        int addResult = userMapper.insert(user);
        if (addResult > 0){
            return selectOne(userDTO);
        }else {
            return DataResult.getBuilder(Status.INSERT_ERROR.getStatus(), Message.INSERT_ERROR.getMsg()).build();
        }
    }

    /**
     * 删除一个用户
     * @param userDTO
     * @return
     */
    @Override
    public HttpResult deleteOne(UserDTO userDTO){
        DataResult dataResult;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (userDTO.getId()!=null && userDTO.getId()>0){
            queryWrapper.eq("id", userDTO.getId());
        }
        if (userDTO.getUserName()!=null && userDTO.getUserName().length()>0){
            queryWrapper.eq("user_name",userDTO.getUserName());
        }
        List<User> list = userMapper.selectList(queryWrapper);
        if (list == null || list.size() != 1){
            dataResult = DataResult.getBuilder(Status.QUERY_ERROR.getStatus(), Message.QUERY_ERROR.getMsg()).build();
        }else {
            User user = list.get(0);
            user.setDelFlag(1);
            int update = userMapper.update(user, queryWrapper);
            if (update == 0){
                dataResult = DataResult.getBuilder(Status.UPDATE_ERROR.getStatus(),Message.UPDATE_ERROR.getMsg()).build();
            }
            dataResult = DataResult.getBuilder().data(UserTransformer.user2UserVo(user)).build();
        }
        return dataResult;
    }

    /**
     * 修改用户
     * @param userDTO
     * @return
     */
    @Override
    public HttpResult exchange(UserDTO userDTO){
        DataResult dataResult;
        if (Objects.isNull(userDTO.getId())){
            return DataResult.getBuilder(Status.PARAM_NEED.getStatus(), Message.PARAM_NEED.getMsg()).build();
        }
        // 密码加密
        if (userDTO.getUserPassword()!=null && userDTO.getUserPassword().length()>0){
            userDTO.setUserPassword(passwordService.createPassword(userDTO.getUserPassword()));
        }
        int update = userMapper.updateById(UserTransformer.userDTO2User(userDTO));
        if (update>0){
            User completeUser = userMapper.selectById(userDTO.getId());
            dataResult = DataResult.getBuilder().data(UserTransformer.user2UserVo(completeUser)).build();
        }else {
            dataResult = DataResult.getBuilder(Status.UPDATE_ERROR.getStatus(), Message.UPDATE_ERROR.getMsg()).data(userDTO).build();
        }
        return dataResult;
    }

    /**
     * 登录
     * @param userDTO
     * @return
     */
    @Override
    public HttpResult login(UserDTO userDTO) {
        String queryConditionName = "user_name";
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (null == userMapper.selectOne(queryWrapper.eq(queryConditionName, userDTO.getUserName()))){
            return DataResult.getBuilder(Status.USER_NOT_FOUND.getStatus(), Message.USER_NOT_FOUND.getMsg()).build();
        }
        User user = userMapper.selectOne(queryWrapper.eq(queryConditionName, userDTO.getUserName()));
        if (!passwordService.authenticatePassword(user.getUserPassword(), userDTO.getUserPassword())){
            return DataResult.getBuilder(Status.ERROR_PASSWORD.getStatus(), Message.ERROR_PASSWORD.getMsg()).build();
        }
        // 验证通过，将当前用户存入session中
        Person p = UserTransformer.user2Person(user);
        UserContext.putCurrentUser(p);
        return DataResult.getBuilder(Status.SUCCESS.getStatus(), Message.WELCOME_TO_LOGIN.getMsg())
                .data(UserTransformer.user2UserVo(user))
                .build();
    }

    /**
     * 登出
     * @param
     * @return
     */
    @Override
    public HttpResult logout() {
        PersonVo currentUser = UserContext.getCurrentUser();
        if (Objects.isNull(currentUser)){
            return DataResult.fail().build();
        }
        UserVO userVO = UserTransformer.personVO2UserVO(currentUser);
        // 验证通过，将当前用户从session中删除
        UserContext.deleteCurrentUser();
        return new DataResult
                .Builder(Status.SUCCESS.getStatus(), Message.BYE_BYE.getMsg())
                .data(userVO)
                .build();
    }

    /**
     * 获取当前的用户
     * @return
     */
    @Override
    public HttpResult getCurrentUser(){
        PersonVo person = new PersonVo();
        try {
            person = UserContext.getCurrentUser();
        }catch (IllegalArgumentException e){
            e.fillInStackTrace();
        }
        return DataResult.getBuilder().data(person).build();
    }

    @Override
    public HttpResult getUserNameId(List<String> userNames) {
        if (CollectionUtils.isEmpty(userNames)){
            return DataResult.fail().data("Error, empty param found").build();
        }
        return DataResult.getBuilder().data(userMapper.getUserNameId(userNames)).build();
    }
}
