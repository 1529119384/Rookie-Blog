package com.lx.blog.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.blog.repository.dao.impl.mapper.entity.ModerationLog;

import java.util.List;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 审核日志数据访问接口
 */
public interface ModerationLogDao extends IService<ModerationLog> {

    /**
     * 根据文章ID查询审核日志列表
     *
     * @param articleId 文章ID
     * @return 审核日志列表
     */
    List<ModerationLog> listByArticle(String articleId);
}

