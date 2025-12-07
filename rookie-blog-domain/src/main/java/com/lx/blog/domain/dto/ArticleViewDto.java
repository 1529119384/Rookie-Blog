package com.lx.blog.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * @author LX
 * @date 2025/12/05
 * @description 文章阅读记录DTO
 */
@Data
@Builder
public class ArticleViewDto {
    @NotBlank
    private String articleId;
    private String ip;
    private String ua;
}
