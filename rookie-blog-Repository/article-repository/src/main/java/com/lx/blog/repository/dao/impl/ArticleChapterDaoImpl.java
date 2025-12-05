package com.lx.blog.repository.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.blog.repository.dao.ArticleChapterDao;
import com.lx.blog.repository.dao.impl.mapper.ArticleChapterMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleChapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 章节数据访问实现
 */
@Repository
@RequiredArgsConstructor
public class ArticleChapterDaoImpl extends ServiceImpl<ArticleChapterMapper, ArticleChapter> implements ArticleChapterDao {

    /**
     * 根据文章ID查询章节列表
     *
     * @param articleId 文章ID
     * @return 章节列表
     */
    @Override
    public List<ArticleChapter> listByArticle(String articleId) {
        return baseMapper.selectList(new LambdaQueryWrapper<ArticleChapter>()
                .eq(ArticleChapter::getArticleId, articleId)
                .orderByAsc(ArticleChapter::getChapterOrder));
    }

    /**
     * 根据文章ID和章节锚点查询章节
     *
     * @param articleId 文章ID
     * @param anchor    章节锚点
     * @return 章节实体
     */
    @Override
    public ArticleChapter getByAnchor(String articleId, String anchor) {
        return baseMapper.selectOne(new LambdaQueryWrapper<ArticleChapter>()
                .eq(ArticleChapter::getArticleId, articleId)
                .eq(ArticleChapter::getAnchor, anchor));
    }
}

