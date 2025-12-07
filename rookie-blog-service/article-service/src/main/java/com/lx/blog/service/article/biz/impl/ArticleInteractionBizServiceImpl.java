package com.lx.blog.service.article.biz.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.lx.blog.common.response.Result;
import com.lx.blog.common.utils.BeanCopyUtils;
import com.lx.blog.domain.dto.ArticleViewDto;
import com.lx.blog.repository.dao.ArticleFavoriteDao;
import com.lx.blog.repository.dao.ArticleLikeDao;
import com.lx.blog.repository.dao.ArticleStatsDao;
import com.lx.blog.repository.dao.ArticleLogDao;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleLog;
import com.lx.blog.service.article.biz.ArticleInteractionBizService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author LX
 * @date 2025/12/03
 * @description 文章互动业务服务实现类
 */
@Service
@RequiredArgsConstructor
public class ArticleInteractionBizServiceImpl implements ArticleInteractionBizService {

    @NotNull private final ArticleLogDao viewDao;
    @NotNull private final ArticleStatsDao statsDao;
    @NotNull private final ArticleLikeDao likeDao;
    @NotNull private final ArticleFavoriteDao favoriteDao;

    /**
     * 记录文章阅读（插入阅读明细并增加阅读统计）
     *
     * @param dto 阅读明细DTO
     * @return 是否成功
     */
    @Override
    public Result<Object> appendView(ArticleViewDto dto) {
        ArticleLog view = BeanCopyUtils.copyProperties(dto, ArticleLog.class);
        view.setViewAt(LocalDateTime.now());
        viewDao.appendView(view);
        statsDao.incViews(view.getArticleId(), 1);
        return Result.ok();
    }

    /**
     * 点赞文章
     *
     * @param articleId 文章ID
     * @return 结果
     */
    @Override
    public Result<Object> like(String articleId) {
        String userId = StpUtil.getLoginIdAsString();
        likeDao.like(articleId, userId);
        return Result.ok();
    }

    /**
     * 取消点赞文章
     *
     * @param articleId 文章ID
     * @return 结果
     */
    @Override
    public Result<Object> unlike(String articleId) {
        String userId = StpUtil.getLoginIdAsString();
        likeDao.unlike(articleId, userId);
        return Result.ok();
    }

    /**
     * 收藏文章
     *
     * @param articleId 文章ID
     * @return 结果
     */
    @Override
    public Result<Object> favorite(String articleId) {
        String userId = StpUtil.getLoginIdAsString();
        favoriteDao.favorite(articleId, userId);
        return Result.ok();
    }

    /**
     * 取消收藏文章
     *
     * @param articleId 文章ID
     * @return 结果
     */
    @Override
    public Result<Object> unfavorite(String articleId) {
        String userId = StpUtil.getLoginIdAsString();
        favoriteDao.unfavorite(articleId, userId);
        return Result.ok();
    }

     /**
      * 检查用户是否点赞文章
      *
      * @param articleId 文章ID
      * @return 是否点赞
      */
    @Override
    public Result<Boolean> isLiked(String articleId) {
        String userId = StpUtil.getLoginIdAsString();
        return Result.ok(likeDao.hasLiked(articleId, userId));
    }

    /**
     * 检查用户是否收藏文章
     *
     * @param articleId 文章ID
     * @return 是否收藏
     */
    @Override
    public Result<Boolean> isFavorited(String articleId) {
        String userId = StpUtil.getLoginIdAsString();
        return Result.ok(favoriteDao.hasFavorited(articleId, userId));
    }

    /**
     * 统计文章点赞数量
     *
     * @param articleId 文章ID
     * @return 点赞数量
     */
    @Override
    public Result<Long> likeCount(String articleId) {
        return Result.ok(likeDao.likeCount(articleId));
    }

     /**
      * 统计文章收藏数量
      *
      * @param articleId 文章ID
      * @return 收藏数量
      */
    @Override
    public Result<Long> favoriteCount(String articleId) {
        return Result.ok(favoriteDao.favoriteCount(articleId));
    }

}
