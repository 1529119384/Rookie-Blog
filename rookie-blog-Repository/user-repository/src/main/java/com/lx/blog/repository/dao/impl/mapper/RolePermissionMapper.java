package com.lx.blog.repository.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LX
 * @date 2025/11/14
 * @description 角色权限Mapper接口
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
}