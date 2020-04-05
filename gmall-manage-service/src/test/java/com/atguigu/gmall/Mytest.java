package com.atguigu.gmall;

import com.atguigu.gmall.bean.PmsBaseAttrInfo;
import com.atguigu.gmall.bean.PmsSkuImage;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.service.manage.AttrService;
import com.atguigu.gmall.service.manage.SkuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dalaoban
 * @create 2020-03-08-8:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Mytest {

    @Resource
    AttrService attrService;

    @Resource
    SkuService skuService;

    @Test
    public void test1(){
//        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
//        pmsSkuInfo.setId("105");
//        List<PmsSkuInfo> pmsSkuInfos = skuService.selectSkuInfoAndSkuImgBySkuId(pmsSkuInfo);
//        System.out.println(pmsSkuInfos);

//        List<String> list = new ArrayList<>();
//        list.add("12");
//        list.add("25");
//        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.pmsBaseAttrinfoSelectList(list);
//        pmsBaseAttrInfos.stream()
//                        .forEach(System.out::println);
    }
}
