package com.lx.blog.repository.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.blog.repository.dao.PermissionDao;
import com.lx.blog.repository.dao.impl.mapper.PermissionMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LX
 * @date 2025/11/14
 * @description 权限数据访问实现
 */
@Repository
public class PermissionDaoImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionDao {

    /**
     * 根据权限编码查询权限
     *
     * @param code 权限唯一编码
     * @return 匹配的权限实体，未找到返回 null
     */
    @Override
    public Permission getByCode(String code) {
        return baseMapper.selectOne(new LambdaQueryWrapper<Permission>().eq(Permission::getCode, code));
    }

    /**
     * 查询全部权限
     *
     * @return 权限列表
     */
    @Override
    public List<Permission> listAll() {
        return baseMapper.selectList(new LambdaQueryWrapper<>());
    }
}