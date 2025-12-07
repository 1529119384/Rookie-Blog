package com.lx.blog.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleRevision;

/**
 * @author LX
 * @date 2025/12/03
 * @description 文章修订数据访问接口
 */
public interface ArticleRevisionDao extends IService<ArticleRevision> {

    /**
     * 查询最新修订
     *
     * @param articleId 文章ID
     * @return 最新修订
     */
    ArticleRevision latest(String articleId);
}

