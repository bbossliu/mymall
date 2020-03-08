package com.atguigu.gmall.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.ReturnT;
import com.atguigu.gmall.bean.TbUser;
import com.atguigu.gmall.cache.service.ICacheService;
import com.atguigu.gmall.user.service.TbUserService;
import com.atguigu.gmall.util.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * @author dalaoban
 * @create 2020-02-19-19:04
 */
@Controller
public class LoginController {
    @Reference
    ICacheService cacheService;

    @Reference
    TbUserService tbUserService;

    //检查token是否有效
    @ResponseBody
    @PostMapping("checkToken")
    public ReturnT<TbUser> checkToken(@RequestParam String token){
        if(StringUtils.isNotEmpty(token)){
            String str = cacheService.getString(token);
            if(StringUtils.isNotEmpty(str)){
                TbUser tbUser = JSON.parseObject(str, TbUser.class);
                return new ReturnT<TbUser>(tbUser);
            }
        }
        return new ReturnT<TbUser>(ReturnT.FAIL_CODE,"token校验失败！");
    }

    //去登录界面
    @GetMapping("index")
    public String index( @RequestParam(required = false) String ReturnUrl, ModelMap map) {
        map.put("ReturnUrl", ReturnUrl);
        return "index";
    }

    @PostMapping("login")
    public String login(TbUser tbUser, ModelMap model ,@RequestParam(required = false)String ReturnUrl, HttpServletRequest request,
                HttpServletResponse response, RedirectAttributes redirectAttributes){
        //调用查询数据库接口查询用户名和密码是否正确
        List<TbUser> tbUsers = tbUserService.queryAll(tbUser);
        //判断是否为空，不为空则将用户信息存入redis中
        if(tbUsers !=null && tbUsers.size()!=0){
            TbUser tbSysUser= tbUsers.get(0);
            //生成uuid
            String uuid = UUID.randomUUID().toString();
            //将用户信息存入redis中
            cacheService.setString(uuid, JSON.toJSONString(tbSysUser),60*60*24);

            CookieUtil.setCookie(request,response,"token",uuid,60 * 60 * 24,true);

            if(StringUtils.isNotEmpty(ReturnUrl)){
                return "redirect:"+ReturnUrl;
            }else {
                // 将登录信息传到登录页
                model.addAttribute("tbSysUser", tbSysUser);
            }

        }else {
           redirectAttributes.addFlashAttribute("msg", "用户名或密码错误！");
        }

        return "redirect:/index";
    }

    /**
     * 注销
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String url, Model model) {
        try {
            CookieUtil.deleteCookie(request, response, "token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotEmpty(url)) {
            return "redirect:/login?url=" + url;
        } else {
            return "redirect:/login";
        }
    }
}
