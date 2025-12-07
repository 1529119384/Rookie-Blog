package com.lx.blog.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author LX
 * @date 2025/12/05
 * @description 用户文章统计VO
 */
@Data
@Builder
public class UserArticleStatsVo {
    private Long articleCount;
    private Long viewCount;
    private Long likeCount;
}
