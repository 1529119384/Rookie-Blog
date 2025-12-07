package com.lx.blog.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleAttachment;

import java.util.List;

/**
 * @author LX
 * @date 2025/12/03
 * @description 文章附件数据访问接口
 */
public interface ArticleAttachmentDao extends IService<ArticleAttachment> {

    /**
     * 根据文章ID查询附件列表
     *
     * @param articleId 文章ID
     * @return 附件列表
     */
    List<ArticleAttachment> listByArticle(String articleId);
}

