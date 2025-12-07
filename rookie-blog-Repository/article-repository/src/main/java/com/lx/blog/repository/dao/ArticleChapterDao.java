package com.lx.blog.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleChapter;

import java.util.List;

/**
 * @author LX
 * @date 2025/12/03
 * @description 章节数据访问接口
 */
public interface ArticleChapterDao extends IService<ArticleChapter> {

    /**
     * 按文章查询所有章节
     *
     * @param articleId 文章ID
     * @return 章节列表
     */
    List<ArticleChapter> listByArticle(String articleId);

    /**
     * 根据锚点查询章节
     *
     * @param articleId 文章ID
     * @param anchor 锚点
     * @return 章节
     */
    ArticleChapter getByAnchor(String articleId, String anchor);
}

