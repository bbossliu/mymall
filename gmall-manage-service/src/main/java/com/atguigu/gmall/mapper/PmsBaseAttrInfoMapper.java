package com.atguigu.gmall.mapper;

import com.atguigu.gmall.bean.PmsBaseAttrInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author dalaoban
 * @create 2020-03-01-16:51
 */
public interface PmsBaseAttrInfoMapper extends Mapper<PmsBaseAttrInfo> {


   List<PmsBaseAttrInfo> pmsBaseAttrinfoSelectList(List<String> pid);
}
