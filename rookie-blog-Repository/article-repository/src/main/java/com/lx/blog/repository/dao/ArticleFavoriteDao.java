package com.lx.blog.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleFavorite;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 文章收藏数据访问接口
 */
public interface ArticleFavoriteDao extends IService<ArticleFavorite> {

    /**
     * 检查用户是否收藏了文章
     *
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 是否收藏
     */
    boolean hasFavorited(String articleId, String userId);

    /**
     * 收藏文章
     *
     * @param articleId 文章ID
     * @param userId 用户ID
     */
    void favorite(String articleId, String userId);

     /**
      * 取消收藏文章
      *
      * @param articleId 文章ID
      * @param userId 用户ID
      */
    void unfavorite(String articleId, String userId);

     /**
      * 统计收藏数量
      *
      * @param articleId 文章ID
      * @return 收藏数量
      */
    long favoriteCount(String articleId);
}

