package com.atguigu.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.PmsBaseAttrInfo;
import com.atguigu.gmall.bean.PmsBaseAttrValue;
import com.atguigu.gmall.bean.PmsBaseSaleAttr;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.mapper.PmsBaseAttrInfoMapper;
import com.atguigu.gmall.mapper.PmsBaseAttrValueMapper;
import com.atguigu.gmall.mapper.PmsProductSaleAttrMapper;
import com.atguigu.gmall.mapper.pmsBaseSaleAttrMapper;
import com.atguigu.gmall.service.manage.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author dalaoban
 * @create 2020-03-01-16:50
 */
@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;

    @Autowired
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    @Autowired
    pmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;


    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        PmsBaseAttrInfo pmsBaseAttrInfo = new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.select(pmsBaseAttrInfo);
       List<String> list= new ArrayList<>();
        if(!CollectionUtils.isEmpty(pmsBaseAttrInfos)){
            pmsBaseAttrInfos.stream()
                            .forEach((pmsBaseAttrInfo1)->{
                            list.add(pmsBaseAttrInfo1.getId());
            });
        pmsBaseAttrInfos = pmsBaseAttrInfoMapper.pmsBaseAttrinfoSelectList(list);
        }
        return pmsBaseAttrInfos;
    }

    @Override
    public List<PmsBaseAttrValue> attrValueList(String attrId) {
        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);
        List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
        return pmsBaseAttrValues;
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = pmsBaseSaleAttrMapper.selectAll();
        return pmsBaseSaleAttrs;
    }

    @Override
    public void saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        String id = pmsBaseAttrInfo.getId();
        if(StringUtils.isEmpty(id)){
            //id为空，保存操作
            PmsBaseAttrInfo pmsBaseAttrInfo1 = new PmsBaseAttrInfo();
            pmsBaseAttrInfo1.setCatalog3Id("1");
            pmsBaseAttrInfo1.setAttrName("CPU");

            PmsBaseAttrValue pmsBaseAttrValue1 = new PmsBaseAttrValue();
            pmsBaseAttrValue1.setValueName("麒麟980");

            PmsBaseAttrValue pmsBaseAttrValue2 = new PmsBaseAttrValue();
            pmsBaseAttrValue2.setValueName("A12");

            PmsBaseAttrValue pmsBaseAttrValue3 = new PmsBaseAttrValue();
            pmsBaseAttrValue3.setValueName("骁龙855");

            List<PmsBaseAttrValue> PmsBaseAttrValueList = new ArrayList<>();
            PmsBaseAttrValueList.add(pmsBaseAttrValue1);
            PmsBaseAttrValueList.add(pmsBaseAttrValue2);
            PmsBaseAttrValueList.add(pmsBaseAttrValue3);

            pmsBaseAttrInfo1.setPmsBaseAttrValueList(PmsBaseAttrValueList);
            pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo1);//insertSelective只插入有值的

            List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrInfo1.getPmsBaseAttrValueList();
            for (PmsBaseAttrValue baseAttrValue : pmsBaseAttrValues) {
                baseAttrValue.setAttrId(pmsBaseAttrInfo1.getId());
                pmsBaseAttrValueMapper.insertSelective(baseAttrValue);
            }
        }else{
            //id不是空，修改操作
            //修改属性
            pmsBaseAttrInfo.setAttrName("手机边框");

            Example example = new Example(PmsBaseAttrInfo.class);
            example.createCriteria().andEqualTo("id",pmsBaseAttrInfo.getId());
            pmsBaseAttrInfoMapper.updateByExampleSelective(pmsBaseAttrInfo,example);

            //修改属性值
            PmsBaseAttrValue pmsBaseAttrValue1 = new PmsBaseAttrValue();
            pmsBaseAttrValue1.setValueName("0.4cm");

            PmsBaseAttrValue pmsBaseAttrValue2 = new PmsBaseAttrValue();
            pmsBaseAttrValue2.setValueName("0.5cm");

            List<PmsBaseAttrValue> PmsBaseAttrValueList = new ArrayList<>();
            PmsBaseAttrValueList.add(pmsBaseAttrValue1);
            PmsBaseAttrValueList.add(pmsBaseAttrValue2);
            pmsBaseAttrInfo.setPmsBaseAttrValueList(PmsBaseAttrValueList);

            //按照属性id删除所有属性值
            PmsBaseAttrValue pmsBaseAttrValueDel = new PmsBaseAttrValue();
            pmsBaseAttrValueDel.setAttrId(pmsBaseAttrInfo.getId());
            pmsBaseAttrValueMapper.delete(pmsBaseAttrValueDel);
            //插入属性值
            List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrInfo.getPmsBaseAttrValueList();
            for (PmsBaseAttrValue pmsBaseAttrValue : pmsBaseAttrValues) {
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);
            }
        }
    }

    //searcch时使用
    @Override
    public List<PmsBaseAttrInfo> getAttrValueListByValueId(Set<String> valueIdSet) {
        return null;
    }


}
