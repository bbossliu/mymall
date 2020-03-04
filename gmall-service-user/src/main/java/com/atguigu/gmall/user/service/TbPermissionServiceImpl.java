package com.atguigu.gmall.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.TbPermission;
import com.atguigu.gmall.user.mapper.TbPermissionDao;
import com.atguigu.gmall.user.service.TbPermissionService;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

/**
 * 权限表(TbPermission)表服务实现类
 *
 * @author makejava
 * @since 2020-02-13 15:46:38
 */
@Service
public class TbPermissionServiceImpl implements TbPermissionService {
    @Autowired
    private TbPermissionDao tbPermissionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TbPermission queryById(Long id) {
        return this.tbPermissionDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<TbPermission> queryAllByLimit(int offset, int limit) {
        return this.tbPermissionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tbPermission 实例对象
     * @return 实例对象
     */
    @Override
    public TbPermission insert(TbPermission tbPermission) {
        this.tbPermissionDao.insert(tbPermission);
        return tbPermission;
    }

    /**
     * 修改数据
     *
     * @param tbPermission 实例对象
     * @return 实例对象
     */
    @Override
    public TbPermission update(TbPermission tbPermission) {
        this.tbPermissionDao.update(tbPermission);
        return this.queryById(tbPermission.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.tbPermissionDao.deleteById(id) > 0;
    }

    @Override
    public List<TbPermission> permissionByUserId(Long uid) {
        return tbPermissionDao.permissionByUserId(uid);
    }
}