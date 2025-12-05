package com.lx.blog.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleStats;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 文章统计数据访问接口
 */
public interface ArticleStatsDao extends IService<ArticleStats> {

    /**
     * 增加阅读量
     *
     * @param articleId 文章ID
     * @param delta 增量
     */
    void incViews(String articleId, long delta);
}

