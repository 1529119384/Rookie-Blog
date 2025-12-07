package com.lx.blog.repository.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.blog.repository.dao.ArticleLogDao;
import com.lx.blog.repository.dao.impl.mapper.ArticleLogMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LX
 * @date 2025/12/03
 * @description 文章阅读明细数据访问实现
 */
@Repository
@RequiredArgsConstructor
public class ArticleLogDaoImpl extends ServiceImpl<ArticleLogMapper, ArticleLog> implements ArticleLogDao {

    /**
     * 追加文章阅读明细
     *
     * @param view 文章阅读明细
     */
    @Override
    public void appendView(ArticleLog view) {
        if (view == null) return;
        if (baseMapper.selectOne(new LambdaQueryWrapper<ArticleLog>()
                .eq(ArticleLog::getUserId, view.getUserId())
                .eq(ArticleLog::getArticleId, view.getArticleId())) != null) return;
        baseMapper.insert(view);
    }

    /**
     * 查询用户阅读历史文章列表
     * @param userId 用户ID
     * @return 文章ID列表
     */
    @Override
    public List<String> listHistoryArticleIds(String userId) {
        return baseMapper.selectList(new LambdaQueryWrapper<ArticleLog>()
                .select(ArticleLog::getArticleId, ArticleLog::getViewAt) // 优化查询字段
                .eq(ArticleLog::getUserId, userId)
                .orderByDesc(ArticleLog::getViewAt))
                .stream()
                .map(ArticleLog::getArticleId)
                .distinct() // 内存去重
                .collect(Collectors.toList());
    }
}

