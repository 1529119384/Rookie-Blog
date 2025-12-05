package com.lx.blog.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleSeries;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 文章系列数据访问接口
 */
public interface ArticleSeriesDao extends IService<ArticleSeries> {

    /**
     * 根据唯一编码查询系列
     *
     * @param code 唯一编码
     * @return 文章系列
     */
    ArticleSeries getByCode(String code);
}

