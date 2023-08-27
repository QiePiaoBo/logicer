package com.dylan.framework.interceptor;

import com.dylan.framework.annos.AdminPermission;
import com.dylan.framework.model.info.Message;
import com.dylan.framework.model.info.Status;
import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.session.UserContext;
import com.dylan.framework.utils.CookieUtil;
import com.dylan.framework.utils.PermissionChecker;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.dylan.logicer.base.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 创建拦截器，拦截所有请求
 *
 * @author Dylan
 */
@Component
public class AuthInterceptor implements HandlerInterceptor{

    private static final MyLogger logger = MyLoggerFactory.getLogger(AuthInterceptor.class);

    @Resource
    private PermissionChecker permissionChecker;

    @Autowired
    private CookieUtil cookieUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Got request, param is : {}", JsonUtil.getString(request.getParameterMap()));
        if (!(handler instanceof HandlerMethod)) {
            return true;
        } else {
            // 获取方法中的注解
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            AdminPermission authToken = method.getAnnotation(AdminPermission.class);
            // authToken为空说明无需校验 也就是方法上并没有添加AdminPermission注解
            if (authToken == null) {
                return true;
            }
            // 走到这里说明需要校验 若当前用户为空 则直接校验不通过 因为添加AdminPermission注解的接口都是必须登录的接口
            if (null == UserContext.getCurrentUser()) {
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write(DataResult
                        .fail(Status.NOT_LOGIN.getStatus(), Message.NOT_LOGIN.getMsg())
                        .build().toString());
                return false;
            }
            // 使用spring容器中的permissionChecker实例对用户权限进行校验
            if (permissionChecker.hasPermission(authToken.userType(), request.getRequestURI())){
                return true;
            }else {
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write(DataResult
                        .fail(Status.PERMISSION_ERROR.getStatus(), Message.PERMISSION_ERROR.getMsg())
                        .build().toString());
                return false;
            }
        }
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
