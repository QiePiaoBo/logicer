package com.dylan.framework.session;


import com.dylan.framework.constant.CommonConstant;
import com.dylan.framework.model.entity.Person;
import com.dylan.framework.model.vo.PersonVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * 保存和获取当前用户
 * @author Dylan
 */
@Service
public class UserContext {

    /**
     * 获取session
     * @return
     */
    public static HttpSession getSession(){
        // SpringMVC获取session的方式通过RequestContextHolder
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getSession();
    }

    /**
     * 设置当前用户到session中
     * @param currentUser
     */
    public static void putCurrentUser(Person currentUser){
        getSession().setMaxInactiveInterval(30*60);
        getSession().setAttribute(CommonConstant.SESSION_HEADER, currentUser);
    }

    /**
     * 删除当前的人的session
     */
    public static void deleteCurrentUser(){
        PersonVo currentUser = getCurrentUser();
        if (Objects.nonNull(currentUser)){
            getSession().removeAttribute(CommonConstant.SESSION_HEADER);
        }
    }
    /**
     * 获取当前用户
     * @return
     */
    public static PersonVo getCurrentUser(){
        Object o = getSession().getAttribute(CommonConstant.SESSION_HEADER);
        PersonVo personVo = new PersonVo();
        if (null == o){
            return null;
        }
        BeanUtils.copyProperties((Person)o, personVo);
        return personVo;
    }

}
