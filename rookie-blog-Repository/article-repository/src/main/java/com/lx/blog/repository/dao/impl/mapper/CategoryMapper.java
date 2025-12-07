package com.lx.blog.repository.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LX
 * @date 2025/12/03
 * @description 分类映射器
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {}

