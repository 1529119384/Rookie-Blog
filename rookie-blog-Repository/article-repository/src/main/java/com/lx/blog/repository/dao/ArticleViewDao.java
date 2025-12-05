package com.lx.blog.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.blog.repository.dao.impl.mapper.entity.Article;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleView;

import java.util.List;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 文章阅读数据访问接口
 */
public interface ArticleViewDao extends IService<ArticleView> {

    /**
     * 记录阅读
     *
     * @param view 阅读实体
     */
    void appendView(ArticleView view);

    /**
     * 查询用户阅读历史文章列表
     * @param userId 用户ID
     * @return 文章ID列表
     */
    List<String> listHistoryArticleIds(String userId);
}

