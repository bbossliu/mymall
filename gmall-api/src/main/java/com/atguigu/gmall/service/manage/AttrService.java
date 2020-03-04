package com.atguigu.gmall.service.manage;

import com.atguigu.gmall.bean.PmsBaseAttrInfo;
import com.atguigu.gmall.bean.PmsBaseAttrValue;

import java.util.List;
import java.util.Set;

/**
 * @author dalaoban
 * @create 2020-03-01-16:49
 */
public interface AttrService {

    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    List<PmsBaseAttrValue> attrValueList(String attrId);

    void saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrInfo> getAttrValueListByValueId(Set<String> valueIdSet);
}
