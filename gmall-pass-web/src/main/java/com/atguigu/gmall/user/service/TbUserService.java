package com.atguigu.gmall.user.service;

import com.atguigu.gmall.entity.TbUser;
import java.util.List;

/**
 * 用户表(TbUser)表服务接口
 *
 * @author makejava
 * @since 2020-02-13 15:06:39
 */
public interface TbUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbUser queryById(Long id);

    /**
     * 通过条件查询
     *
     * @param tbUser 实例对象
     * @return 实例对象
     */
    List<TbUser> queryAll(TbUser tbUser);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TbUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tbUser 实例对象
     * @return 实例对象
     */
    TbUser insert(TbUser tbUser);

    /**
     * 修改数据
     *
     * @param tbUser 实例对象
     * @return 实例对象
     */
    TbUser update(TbUser tbUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}