package com.atguigu.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsBaseCatalog1;
import com.atguigu.gmall.bean.PmsBaseCatalog2;
import com.atguigu.gmall.bean.PmsBaseCatalog3;
import com.atguigu.gmall.service.manage.CataloagService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dalaoban
 * @create 2020-03-01-15:31
 */
@Controller
@CrossOrigin //跨域注解
//@RequestMapping("manage")
public class CatalogController {
    @Reference
    CataloagService cataloagService;

    @PostMapping("getCatalog1")
    @ResponseBody
    public List<PmsBaseCatalog1> getCatalog1(){
        List<PmsBaseCatalog1> pmsBaseCatalog1s = cataloagService.GetCatalog1();
        return pmsBaseCatalog1s;
    }

    @PostMapping("getCatalog2")
    @ResponseBody
    public List<PmsBaseCatalog2> getCatalog2(@RequestParam("catalog1Id") String catalog1Id){
        List<PmsBaseCatalog2> pmsBaseCatalog2s = cataloagService.GetCatalog2(catalog1Id);
        return pmsBaseCatalog2s;
    }

    @PostMapping("getCatalog3")
    @ResponseBody
    public List<PmsBaseCatalog3> getCatalog3(@RequestParam("catalog2Id") String catalog2Id){
        List<PmsBaseCatalog3> pmsBaseCatalog3s = cataloagService.GetCatalog3(catalog2Id);
        return pmsBaseCatalog3s;
    }
}
