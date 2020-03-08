package com.atguigu.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.PmsBaseCatalog1;
import com.atguigu.gmall.bean.PmsBaseCatalog2;
import com.atguigu.gmall.bean.PmsBaseCatalog3;
import com.atguigu.gmall.mapper.PmsBaseCatalog1Mapper;
import com.atguigu.gmall.mapper.PmsBaseCatalog2Mapper;
import com.atguigu.gmall.mapper.PmsBaseCatalog3Mapper;
import com.atguigu.gmall.service.manage.CataloagService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 分类菜单查询管理
 * @author dalaoban
 * @create 2020-03-01-15:21
 *
 */
@Service
public class CatalogServiceImpl implements CataloagService {

    @Autowired
    PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;

    @Autowired
    PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;

    @Autowired
    PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;

    @Override
    public List<PmsBaseCatalog1> GetCatalog1() {
        List<PmsBaseCatalog1> catalog1s = pmsBaseCatalog1Mapper.selectAll();
        return catalog1s;
    }

    @Override
    public List<PmsBaseCatalog2> GetCatalog2(String catalog1Id) {
        PmsBaseCatalog2 pmsBaseCatalog2 = new PmsBaseCatalog2();
        pmsBaseCatalog2.setCatalog1Id(catalog1Id);
        List<PmsBaseCatalog2> catalogs2 = pmsBaseCatalog2Mapper.select(pmsBaseCatalog2);
        return catalogs2;
    }

    @Override
    public List<PmsBaseCatalog3> GetCatalog3(String catalog2Id) {
        PmsBaseCatalog3 pmsBaseCatalog3 = new PmsBaseCatalog3();
        pmsBaseCatalog3.setCatalog2Id(catalog2Id);
        List<PmsBaseCatalog3> catalog3s = pmsBaseCatalog3Mapper.select(pmsBaseCatalog3);
        return catalog3s;
    }
}
