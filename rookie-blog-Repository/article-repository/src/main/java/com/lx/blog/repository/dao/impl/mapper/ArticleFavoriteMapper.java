package com.lx.blog.repository.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleFavorite;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 文章收藏映射器
 */
@Mapper
public interface ArticleFavoriteMapper extends BaseMapper<ArticleFavorite> {}

