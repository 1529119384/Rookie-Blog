package com.lx.blog.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.blog.repository.dao.impl.mapper.entity.UserRole;
import com.lx.blog.repository.dao.impl.mapper.entity.Role;

import java.util.List;

/**
 * @author 李旭
 * @date 2025/11/14
 * @description 用户角色映射器接口
 */
public interface UserRoleDao extends IService<UserRole> {

    /**
     * 绑定角色到用户（幂等）
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    void assignRole(String userId, Long roleId);

    /**
     * 解绑用户角色（幂等）
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    void revokeRole(String userId, Long roleId);

    /**
     * 查询用户的角色列表
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> listRoles(String userId);
}
