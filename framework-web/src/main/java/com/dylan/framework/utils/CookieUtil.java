package com.dylan.framework.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    /**
     * @Description: 生成指定键值的cookie
     * @Param: [request, response, name, value, maxAge]
     * @Return:
     * @Author: liutao
     * @Create: 2022/7/9 21:46
     **/
    public void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * @Description: 通过设置的键值获取对应的Cookie
     * @Param: [request, name]
     * @Return: javax.servlet.http.Cookie
     * @Author: liutao
     * @Create: 2022/7/9 21:40
     **/
    public Cookie getCookie(HttpServletRequest request, String name) {
        String cookieValue = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * @Description: 根据键值获取存入cookie的值
     * @Param: [request, name]
     * @Return: java.lang.String
     * @Author: liuato
     * @Create: 2022/7/9 20:14
     **/
    public String getCookieValue(HttpServletRequest request, String cookieName) {
        String cookieValue = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    cookieValue = cookie.getValue();
                }
            }
        }
        return cookieValue;
    }

    /**
     * @Description: 根据键值移除cookie通过设置有效时间为“0”
     * @Param: [request, name]
     * @Return: java.lang.String
     * @Author: liuato
     * @Create: 2022/7/9 20:14
     **/
    public boolean removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie cookie = getCookie(request, name);
        if (cookie != null) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return true;
        }
        return false;
    }
}