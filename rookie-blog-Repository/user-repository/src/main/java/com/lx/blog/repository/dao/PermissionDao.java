package com.lx.blog.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.blog.repository.dao.impl.mapper.entity.Permission;

import java.util.List;

/**
 * @author 李旭
 * @date 2025/11/14
 * @description 权限数据访问层
 */
public interface PermissionDao extends IService<Permission> {

    /**
     * 根据权限编码查询权限
     * @param code 权限唯一编码
     * @return 权限信息，未找到返回 null
     */
    Permission getByCode(String code);

    /**
     * 查询全部权限
     * @return 权限列表
     */
    List<Permission> listAll();
}