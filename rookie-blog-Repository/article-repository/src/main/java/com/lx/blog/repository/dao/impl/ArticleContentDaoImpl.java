package com.lx.blog.repository.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.blog.repository.dao.ArticleContentDao;
import com.lx.blog.repository.dao.impl.mapper.ArticleContentMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.ArticleContent;
import org.springframework.stereotype.Repository;

/**
 * @author LX
 * @date 2025/12/04
 * @description 文章内容数据访问实现
 */
@Repository
public class ArticleContentDaoImpl extends ServiceImpl<ArticleContentMapper, ArticleContent> implements ArticleContentDao {
}
