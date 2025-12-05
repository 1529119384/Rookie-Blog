package com.lx.blog.domain.vo;

import lombok.Data;

/**
 * @author 李旭
 * @date 2025/12/05
 * @description 文章章节VO
 */
@Data
public class ArticleChapterVo {
    private String id;
    private String articleId;
    private String title;
    private Integer sort;
    private String parentId;
}
