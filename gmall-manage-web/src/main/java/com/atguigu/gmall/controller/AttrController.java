package com.atguigu.gmall.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsBaseAttrInfo;
import com.atguigu.gmall.bean.PmsBaseAttrValue;
import com.atguigu.gmall.service.manage.AttrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dalaoban
 * @create 2020-03-01-16:56
 */
//@CrossOrigin跨域问题,前端服务器访问后端服务器 ，实现前后端分离
@Controller
@CrossOrigin
public class AttrController {

    @Reference
    AttrService attrService;

    @GetMapping("attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> attrInfoList(@RequestParam("catalog3Id") String catalog3Id){
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.attrInfoList(catalog3Id);
        return pmsBaseAttrInfos;
    }

    @PostMapping("getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(@RequestParam("attrId") String attrId){
        List<PmsBaseAttrValue> pmsBaseAttrValues = attrService.attrValueList(attrId);
        return pmsBaseAttrValues;
    }

    @PostMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        pmsBaseAttrInfo.setId("44");
        attrService.saveAttrInfo(pmsBaseAttrInfo);
        return "save success";
    }
}
