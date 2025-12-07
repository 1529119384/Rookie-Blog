package com.lx.blog.repository.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleStats;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LX
 * @date 2025/12/03
 * @description 文章统计映射器
 */
@Mapper
public interface ArticleStatsMapper extends BaseMapper<ArticleStats> {}

