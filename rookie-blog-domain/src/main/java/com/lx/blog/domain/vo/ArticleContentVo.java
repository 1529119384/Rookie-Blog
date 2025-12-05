package com.lx.blog.domain.vo;

import lombok.Data;

/**
 * @author 李旭
 * @date 2025/12/05
 * @description 文章内容VO
 */
@Data
public class ArticleContentVo {
    private String articleId;
    private String contentMd;
    private String contentHtml;
    private String wordsCount;
}
