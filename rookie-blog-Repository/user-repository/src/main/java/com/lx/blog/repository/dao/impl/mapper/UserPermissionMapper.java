package com.lx.blog.repository.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.UserPermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LX
 * @date 2025/11/14
 * @description 用户权限映射器
 */
@Mapper
public interface UserPermissionMapper extends BaseMapper<UserPermission> {
}
