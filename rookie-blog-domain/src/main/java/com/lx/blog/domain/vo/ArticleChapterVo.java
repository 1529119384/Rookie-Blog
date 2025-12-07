package com.lx.blog.domain.vo;

import lombok.Data;


/**
 * @author LX
 * @date 2025/12/05
 * @description 文章章节VO
 */
@Data
public class ArticleChapterVo {
    private String id;
    private String articleId;
    private Integer chapterOrder;
    private Integer level;
    private String title;
    private String anchor;
    private String parentId;
    private String path;
}
