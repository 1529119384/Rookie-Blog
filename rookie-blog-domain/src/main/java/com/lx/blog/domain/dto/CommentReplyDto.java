package com.lx.blog.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author LX
 * @date 2025/12/04
 * @description 评论回复DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentReplyDto {

    private String articleId;
    private Long commentId;
    private Long replyToReplyId;
    private String userId;
    private String replyToUserId;
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime replyAt;

    // 预留：用户信息
    private String username;
    private String avatar;
    private String replyToUsername;
}
