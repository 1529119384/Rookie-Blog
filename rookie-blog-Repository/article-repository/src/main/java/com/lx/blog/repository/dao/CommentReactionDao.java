package com.lx.blog.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.blog.repository.dao.impl.mapper.entity.CommentReaction;

import java.util.List;
import java.util.Set;

/**
 * @author LX
 * @date 2025/12/03
 * @description 评论反应数据访问接口
 */
public interface CommentReactionDao extends IService<CommentReaction> {

    /**
     * 对评论进行反应
     *
     * @param commentId 评论ID
     * @param userId 用户ID
     * @param type 反应类型（like/dislike）
     */
    void reactToComment(Long commentId, String userId, String type);

    /**
     * 对回复进行反应
     *
     * @param replyId 回复ID
     * @param userId 用户ID
     * @param type 反应类型（like/dislike）
     */
    void reactToReply(Long replyId, String userId, String type);

    /**
     * 取消对评论或回复的反应
     *
     * @param commentId 评论ID
     * @param replyId 回复ID
     * @param userId 用户ID
     */
    void cancelReaction(Long commentId, Long replyId, String userId);

    /**
     * 获取用户对指定评论列表中进行了某种反应的评论ID集合
     *
     * @param commentIds 评论ID列表
     * @param userId 用户ID
     * @param type 反应类型
     * @return 评论ID集合
     */
    Set<Long> getCommentIdsByReaction(List<Long> commentIds, String userId, String type);

    /**
     * 获取用户对指定回复列表中进行了某种反应的回复ID集合
     *
     * @param replyIds 回复ID列表
     * @param userId 用户ID
     * @param type 反应类型
     * @return 回复ID集合
     */
    Set<Long> getReplyIdsByReaction(List<Long> replyIds, String userId, String type);
}

