package com.lx.blog.repository.dao.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.blog.repository.dao.ArticleStatsDao;
import com.lx.blog.repository.dao.impl.mapper.ArticleStatsMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author LX
 * @date 2025/12/03
 * @description 文章统计数据访问实现
 */
@Repository
@RequiredArgsConstructor
public class ArticleStatsDaoImpl extends ServiceImpl<ArticleStatsMapper, ArticleStats> implements ArticleStatsDao {

    /**
     * 增加文章阅读量
     *
     * @param articleId 文章ID
     * @param delta 增加量
     */
    @Override
    public void incViews(String articleId, long delta) {
        baseMapper.update(null, new LambdaUpdateWrapper<ArticleStats>()
                .eq(ArticleStats::getArticleId, articleId)
                .setSql("views = views + " + Math.max(delta, 0)));
    }
}

