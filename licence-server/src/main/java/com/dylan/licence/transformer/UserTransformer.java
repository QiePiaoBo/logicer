package com.dylan.licence.transformer;

import com.dylan.framework.model.entity.Person;
import com.dylan.framework.model.vo.PersonVo;
import com.dylan.framework.utils.Safes;
import com.dylan.licence.entity.User;
import com.dylan.licence.model.dto.UserDTO;
import com.dylan.licence.model.vo.UserVO;

import java.util.Objects;

/**
 * @Classname UserTransformer
 * @Description User实体类转换器
 * @Date 5/6/2022 5:54 PM
 */
public class UserTransformer {

    /**
     * user -> userVo
     * @param user
     * @return
     */
    public static UserVO user2UserVo(User user){
        UserVO userVO = new UserVO();
        if (Objects.isNull(user)){
            return userVO;
        }
        userVO.setId(user.getId());
        userVO.setUserGroup(user.getUserGroup());
        userVO.setUserName(Safes.of(user.getUserName()));
        userVO.setUserPhone(Safes.of(user.getUserPhone()));
        userVO.setUserType(user.getUserType());
        return userVO;
    }

    /**
     * user -> person
     * @param user
     * @return
     */
    public static Person user2Person(User user){
        Person person = new Person();
        if (Objects.isNull(user)){
            return person;
        }
        person.setId(user.getId());
        person.setUserGroup(user.getUserGroup());
        person.setUserType(user.getUserType());
        person.setUserPassword(Safes.of(user.getUserPassword()));
        person.setUserName(Safes.of(user.getUserName()));
        person.setUserPhone(Safes.of(user.getUserPhone()));
        return person;
    }

    /**
     * userDTO -> user
     * @param userDTO
     * @return
     */
    public static User userDTO2User(UserDTO userDTO){
        User user = new User();
        if (Objects.isNull(userDTO)){
            return user;
        }
        user.setUserName(userDTO.getUserName());
        user.setUserPhone(userDTO.getUserPhone());
        user.setUserGroup(userDTO.getUserGroup());
        user.setId(userDTO.getId());
        user.setUserType(userDTO.getUserType());
        return user;
    }

    /**
     * userDTO -> userVO
     * @param userDTO
     * @return
     */
    public static UserVO userDTO2UserVO(UserDTO userDTO){
        UserVO userVO = new UserVO();
        if (Objects.isNull(userDTO)){
            return userVO;
        }
        userVO.setId(userDTO.getId());
        userVO.setUserGroup(userDTO.getUserGroup());
        userVO.setUserName(Safes.of(userDTO.getUserName()));
        userVO.setUserPhone(Safes.of(userDTO.getUserPhone()));
        userVO.setUserType(userDTO.getUserType());
        return userVO;
    }

    /**
     * person -> personVO
     * @param person
     * @return
     */
    public static PersonVo person2PersonVO(Person person){
        PersonVo personVo = new PersonVo();
        if (Objects.isNull(person)){
            return personVo;
        }
        personVo.setId(person.getId());
        personVo.setUserGroup(person.getUserGroup());
        personVo.setUserName(Safes.of(person.getUserName()));
        personVo.setUserPhone(Safes.of(person.getUserPhone()));
        personVo.setUserType(person.getUserType());
        return personVo;
    }

    /**
     * PersonVo -> UserVo
     * @param personVo
     * @return
     */
    public static UserVO personVO2UserVO(PersonVo personVo){
        UserVO userVO = new UserVO();
        if (Objects.isNull(personVo)){
            return userVO;
        }
        userVO.setId(personVo.getId());
        userVO.setUserGroup(personVo.getUserGroup());
        userVO.setUserPhone(Safes.of(personVo.getUserPhone()));
        userVO.setUserName(Safes.of(personVo.getUserName()));
        userVO.setUserType(personVo.getUserType());
        return userVO;
    }

}
