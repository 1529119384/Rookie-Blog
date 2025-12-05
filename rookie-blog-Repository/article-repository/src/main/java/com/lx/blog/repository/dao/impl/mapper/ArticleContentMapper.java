package com.lx.blog.repository.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleContent;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 李旭
 * @date 2025/12/04
 * @description 文章内容映射器
 */
@Mapper
public interface ArticleContentMapper extends BaseMapper<ArticleContent> {}
