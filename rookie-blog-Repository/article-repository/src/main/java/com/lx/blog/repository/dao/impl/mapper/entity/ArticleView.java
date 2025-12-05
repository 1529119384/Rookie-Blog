package com.lx.blog.repository.dao.impl.mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 文章阅读明细实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("article_view")
public class ArticleView {

    /**
     * 阅读明细ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章ID
     */
    @TableField("article_id")
    private String articleId;

    /**
     * 用户ID（匿名为NULL）
     */
    @TableField("user_id")
    private String userId;

    /**
     * IP地址
     */
    @TableField("ip")
    private String ip;

    /**
     * User-Agent
     */
    @TableField("ua")
    private String ua;

    /**
     * 来源页面
     */
    @TableField("referer")
    private String referer;

    /**
     * 阅读时间
     */
    @TableField("view_at")
    private LocalDateTime viewAt;
}

