package com.lx.blog.repository.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.blog.repository.dao.RoleDao;
import com.lx.blog.repository.dao.impl.mapper.RoleMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LX
 * @date 2025/11/14
 * @description 角色数据访问实现
 */
@Repository
public class RoleDaoImpl extends ServiceImpl<RoleMapper, Role> implements RoleDao {

    /**
     * 根据角色编码查询角色
     *
     * @param code 角色唯一编码
     * @return 匹配的角色实体，未找到返回 null
     */
    @Override
    public Role getByCode(String code) {
        return baseMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getCode, code));
    }

    /**
     * 查询全部角色
     *
     * @return 角色列表
     */
    @Override
    public List<Role> listAll() {
        return baseMapper.selectList(new LambdaQueryWrapper<>());
    }
}