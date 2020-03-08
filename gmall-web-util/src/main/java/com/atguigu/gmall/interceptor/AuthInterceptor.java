package com.atguigu.gmall.interceptor;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.annotations.LoginRequired;
import com.atguigu.gmall.bean.ReturnT;
import com.atguigu.gmall.util.CookieUtil;
import com.atguigu.gmall.util.http.HttpclientUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dalaoban
 * @create 2020-02-11-16:14
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod){
            //拦截代码
            //判断请求是否有@LoginRequired注解，有代表该方法必须拦截
            /*handler获取请求中的方法*/
            HandlerMethod hm = (HandlerMethod) handler;

            /*通过反射获取该方法上面的LoginRequired注解*/
            LoginRequired methodAnnotation = hm.getMethodAnnotation(LoginRequired.class);

            //如果没有标注需要登录的注解则表示可以放行
            if(methodAnnotation==null){
                return true;
            }

            String token = CookieUtil.getCookieValue(request, "token", true);
            if(StringUtils.isNotEmpty(token)){
                //token不为空则需要根据token，去redis中取值，即验证该token是否有效
                //调用Redis中的服务，检验token,并取值
                Map<String,String> map=new HashMap<>(1);
                map.put("token",token);
                String s = HttpclientUtil.doPost("http://localhost:8085/checkToken", map);
                ReturnT returnT = JSON.parseObject(s, ReturnT.class);
                if(returnT!=null && returnT.getCode()!=500){
                    return true;
                }
            }
            //token为空则证明，token过期或者没有登录则需要去登录界面进行登录
            /*重定向到登录界面*/
            /* passport.gmall.com*/
            /*用户从哪个页面被拦截，获取该页面的全地址 ：StringBuffer requestURL = request.getRequestURL();
             * 用户登录成功后可以返回该页面*/
            StringBuffer requestURL = request.getRequestURL();
            response.sendRedirect("http://localhost:8085/index?ReturnUrl=" + requestURL);
            return false;
        }
        return true;
    }


}
