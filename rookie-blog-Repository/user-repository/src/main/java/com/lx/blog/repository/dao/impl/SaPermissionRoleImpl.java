package com.lx.blog.repository.dao.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.lx.blog.common.utils.RedisUtils;
import com.lx.blog.repository.dao.UserDao;
import com.lx.blog.repository.dao.impl.mapper.entity.Permission;
import com.lx.blog.repository.dao.impl.mapper.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 李旭
 * @date 2025/11/14
 * @description Sa-Token 权限角色接口实现类
 */
@Component
@RequiredArgsConstructor
public class SaPermissionRoleImpl implements StpInterface {

    @NotNull
    private final UserDao userDao;
    @NotNull
    private final RedisUtils redisUtils;

    /**
     * 获取指定登录账号的权限列表
     * @param loginId 登录账号ID
     * @param loginType 登录类型
     * @return 权限列表
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        String userId = String.valueOf(loginId);
        String cacheKey = "auth:perm:" + userId;
        List<String> cached = redisUtils.get(cacheKey, List.class);
        if (cached != null) return cached;
        List<Role> roles = userDao.listRolesByUserId(userId);
        List<Permission> perms = userDao.listPermissionsByUserId(userId);
        List<String> codes = perms.stream().map(Permission::getCode).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        redisUtils.set(cacheKey, codes, Duration.ofMinutes(30));
        return codes;
    }

    /**
     * 获取指定登录账号的角色列表
     * @param loginId 登录账号ID
     * @param loginType 登录类型
     * @return 角色列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String userId = String.valueOf(loginId);
        String cacheKey = "auth:role:" + userId;
        List<String> cached = redisUtils.get(cacheKey, List.class);
        if (cached != null) return cached;
        List<Role> roles = userDao.listRolesByUserId(userId);
        List<String> codes = roles.stream().map(Role::getCode).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        redisUtils.set(cacheKey, codes, Duration.ofMinutes(30));
        return codes;
    }
}