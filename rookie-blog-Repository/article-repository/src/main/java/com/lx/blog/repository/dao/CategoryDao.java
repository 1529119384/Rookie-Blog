package com.lx.blog.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.blog.repository.dao.impl.mapper.entity.Category;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 分类数据访问接口
 */
public interface CategoryDao extends IService<Category> {

    /**
     * 根据编码查询分类
     *
     * @param code 分类唯一编码
     * @return 分类实体
     */
    Category getByCode(String code);
}

