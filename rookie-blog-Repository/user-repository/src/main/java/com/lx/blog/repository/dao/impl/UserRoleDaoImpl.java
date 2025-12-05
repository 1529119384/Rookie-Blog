package com.lx.blog.repository.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.blog.repository.dao.UserRoleDao;
import com.lx.blog.repository.dao.impl.mapper.UserRoleMapper;
import com.lx.blog.repository.dao.impl.mapper.RoleMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.UserRole;
import com.lx.blog.repository.dao.impl.mapper.entity.Role;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author 李旭
 * @date 2025/11/14
 * @description 用户角色映射器实现类
 */
@Repository
public class UserRoleDaoImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleDao {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 绑定角色到用户（幂等）
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    @Override
    public void assignRole(String userId, Long roleId) {
        long count = this.count(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId).eq(UserRole::getRoleId, roleId));
        if (count == 0) {
            UserRole ur = UserRole.builder().userId(userId).roleId(roleId).createdAt(LocalDateTime.now()).build();
            this.save(ur);
        }
    }

    /**
     * 解绑用户角色（幂等）
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    @Override
    public void revokeRole(String userId, Long roleId) {
        this.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId).eq(UserRole::getRoleId, roleId));
    }

    /**
     * 查询用户的角色列表
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<Role> listRoles(String userId) {
        List<UserRole> urs = this.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        List<Long> roleIds = urs.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        if (roleIds.isEmpty()) return List.of();
        return roleMapper.selectBatchIds(roleIds);
    }
}
