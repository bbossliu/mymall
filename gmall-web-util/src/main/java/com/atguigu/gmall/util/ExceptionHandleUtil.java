package com.atguigu.gmall.util;

import com.alibaba.dubbo.common.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dalaoban
 * @create 2020-03-14-23:10
 */
public class ExceptionHandleUtil {

    //判断请求是否为异步请求
    public static boolean checkAsyncRequest(HttpServletRequest request){
        //获取相应的请求头
        String accept = request.getHeader("Accept");
        String xRequested = request.getHeader("X-Requested-With");

        if((!StringUtils.isEmpty(accept) && accept.contains("application/json"))
                || (!StringUtils.isEmpty(xRequested) && xRequested.contains("XMLHttpRequest"))){
            return true;
        }
        return false;
    }
}
