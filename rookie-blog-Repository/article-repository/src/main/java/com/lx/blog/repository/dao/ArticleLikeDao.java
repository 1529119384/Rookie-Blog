package com.lx.blog.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleLike;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 文章点赞数据访问接口
 */
public interface ArticleLikeDao extends IService<ArticleLike> {

    /**
     * 是否已点赞
     *
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 是否已点赞
     */
    boolean hasLiked(String articleId, String userId);

    /**
     * 点赞
     *
     * @param articleId 文章ID
     * @param userId 用户ID
     */
    void like(String articleId, String userId);

    /**
     * 取消点赞
     *
     * @param articleId 文章ID
     * @param userId 用户ID
     */
    void unlike(String articleId, String userId);

    /**
     * 统计点赞数量
     *
     * @param articleId 文章ID
     * @return 点赞数量
     */
    long likeCount(String articleId);
}

