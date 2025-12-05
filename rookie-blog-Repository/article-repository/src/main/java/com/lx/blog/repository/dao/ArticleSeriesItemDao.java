package com.lx.blog.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleSeriesItem;

import java.util.List;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 文章系列项数据访问接口
 */
public interface ArticleSeriesItemDao extends IService<ArticleSeriesItem> {

    /**
     * 根据系列ID查询项列表
     *
     * @param seriesId 系列ID
     * @return 项列表
     */
    List<ArticleSeriesItem> listItems(Long seriesId);
}

