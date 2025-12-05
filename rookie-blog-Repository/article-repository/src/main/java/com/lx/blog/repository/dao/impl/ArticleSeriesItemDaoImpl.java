package com.lx.blog.repository.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.blog.repository.dao.ArticleSeriesItemDao;
import com.lx.blog.repository.dao.impl.mapper.ArticleSeriesItemMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleSeriesItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 文章系列项数据访问实现
 */
@Repository
@RequiredArgsConstructor
public class ArticleSeriesItemDaoImpl extends ServiceImpl<ArticleSeriesItemMapper, ArticleSeriesItem> implements ArticleSeriesItemDao {

    /**
     * 根据系列ID查询项列表
     *
     * @param seriesId 系列ID
     * @return 项列表
     */
    @Override
    public List<ArticleSeriesItem> listItems(Long seriesId) {
        return baseMapper.selectList(new LambdaQueryWrapper<ArticleSeriesItem>()
                .eq(ArticleSeriesItem::getSeriesId, seriesId)
                .orderByAsc(ArticleSeriesItem::getSortOrder));
    }
}

