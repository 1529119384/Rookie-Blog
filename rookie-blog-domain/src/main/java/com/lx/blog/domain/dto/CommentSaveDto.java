package com.lx.blog.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author 李旭
 * @date 2025/12/05
 * @description 发表评论DTO
 */
@Data
public class CommentSaveDto {
    @NotBlank(message = "文章ID不能为空")
    private String articleId;
    
    @NotBlank(message = "评论内容不能为空")
    private String content;
}
