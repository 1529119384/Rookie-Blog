package com.lx.blog.repository.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 文章-标签关联映射器
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {}

