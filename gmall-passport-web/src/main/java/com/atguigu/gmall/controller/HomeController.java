package com.atguigu.gmall.controller;

import com.atguigu.gmall.bean.ReturnT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dalaoban
 * @create 2020-02-18-18:16
 */
@Controller
@RequestMapping("/passport")
@Api(value="用户controller",tags={"用户操作接口"})
public class HomeController {
    @ApiOperation(value = "登陆页面",notes = "访问登陆页面")
    @ApiImplicitParam(name = "hello",value = "参数hello",required = false,dataTypeClass = String.class,paramType = "json",readOnly = false)
    @PostMapping("/index")
    @ResponseBody
    public ReturnT index(@RequestBody String hello){
        return new ReturnT("我是谁");
    }

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "测试成功";
    }
}
