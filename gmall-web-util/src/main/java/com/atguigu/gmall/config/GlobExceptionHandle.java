package com.atguigu.gmall.config;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.ReturnT;
import com.atguigu.gmall.util.ExceptionHandleUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author dalaoban
 * @create 2020-03-14-23:09
 */
@ControllerAdvice
public class GlobExceptionHandle {

    @ExceptionHandler(Exception.class)
    public ModelAndView catchException(Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //将异常信息存入数据库

        //异步则返回错误消息
        if(ExceptionHandleUtil.checkAsyncRequest(request)){

            PrintWriter writer = response.getWriter();
            ReturnT returnT = new ReturnT(ReturnT.FAIL_CODE, "系统异常！");
            String exJson = JSON.toJSONString(returnT);
            writer.write(exJson);
        }

        //同步则返回页面
        ModelAndView modelAndView = new ModelAndView();
        exception.printStackTrace();
        modelAndView.addObject("exception", exception);
        modelAndView.setViewName("error");
        return modelAndView;
    }

}
