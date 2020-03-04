package com.atguigu.gmall.controller;

import com.atguigu.gmall.entity.TbPermission;
import com.atguigu.gmall.user.service.TbPermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 权限表(TbPermission)表控制层
 *
 * @author makejava
 * @since 2020-02-13 15:46:38
 */
@RestController
@RequestMapping("tbPermission")
public class TbPermissionController {
    /**
     * 服务对象
     */
    @Resource
    private TbPermissionService tbPermissionService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public TbPermission selectOne(Long id) {
        return this.tbPermissionService.queryById(id);
    }

}