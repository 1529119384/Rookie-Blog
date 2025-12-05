package com.lx.blog.repository.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.blog.repository.dao.ArticleRevisionDao;
import com.lx.blog.repository.dao.impl.mapper.ArticleRevisionMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleRevision;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 文章修订数据访问实现
 */
@Repository
@RequiredArgsConstructor
public class ArticleRevisionDaoImpl extends ServiceImpl<ArticleRevisionMapper, ArticleRevision> implements ArticleRevisionDao {

    /**
     * 根据文章ID查询最新修订
     *
     * @param articleId 文章ID
     * @return 最新修订实体
     */
    @Override
    public ArticleRevision latest(String articleId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<ArticleRevision>()
                .eq(ArticleRevision::getArticleId, articleId)
                .orderByDesc(ArticleRevision::getSavedAt)
                .last("LIMIT 1"));
    }
}

