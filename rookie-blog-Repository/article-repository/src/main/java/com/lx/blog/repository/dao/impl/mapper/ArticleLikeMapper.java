package com.lx.blog.repository.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleLike;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LX
 * @date 2025/12/03
 * @description 文章点赞映射器
 */
@Mapper
public interface ArticleLikeMapper extends BaseMapper<ArticleLike> {}

