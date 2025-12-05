package com.lx.blog.repository.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.blog.repository.dao.ArticleViewDao;
import com.lx.blog.repository.dao.impl.mapper.ArticleViewMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 文章阅读明细数据访问实现
 */
@Repository
@RequiredArgsConstructor
public class ArticleViewDaoImpl extends ServiceImpl<ArticleViewMapper, ArticleView> implements ArticleViewDao {

    /**
     * 追加文章阅读明细
     *
     * @param view 文章阅读明细
     */
    @Override
    public void appendView(ArticleView view) {
        baseMapper.insert(view);
    }

    /**
     * 查询用户阅读历史文章列表
     * @param userId 用户ID
     * @return 文章ID列表
     */
    @Override
    public List<String> listHistoryArticleIds(String userId) {
        // 简单实现：查询该用户所有的阅读记录，去重，按时间倒序
        // 注意：数据量大时应优化，例如只查最近100条，或者使用DISTINCT查询
        return baseMapper.selectList(new LambdaQueryWrapper<ArticleView>()
                .select(ArticleView::getArticleId, ArticleView::getViewAt) // 优化查询字段
                .eq(ArticleView::getUserId, userId)
                .orderByDesc(ArticleView::getViewAt))
                .stream()
                .map(ArticleView::getArticleId)
                .distinct() // 内存去重
                .collect(Collectors.toList());
    }
}

