package com.lx.blog.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 李旭
 * @date 2025/12/04
 * @description 评论列表响应DTO
 */
@Data
public class CommentDto {

    private Long id;
    private String articleId;
    private String userId;
    private String content;
    private Integer floor;
    private Integer isPinned;
    private Integer hasReply;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime commentAt;

    // 预留：用户信息（需关联查询或填充）
    private String username;
    private String avatar;

    // 回复列表（第一页回复或部分回复，可选）
    private List<CommentReplyDto> replies;
}
