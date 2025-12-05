package com.lx.blog.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.blog.repository.dao.impl.mapper.entity.Role;

import java.util.List;

/**
 * @author 李旭
 * @date 2025/11/14
 * @description 角色数据访问层
 */
public interface RoleDao extends IService<Role> {

    /**
     * 根据角色编码查询角色
     * @param code 角色唯一编码
     * @return 角色信息，未找到返回 null
     */
    Role getByCode(String code);

    /**
     * 查询全部角色
     * @return 角色列表
     */
    List<Role> listAll();
}