package com.lx.blog.repository.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.CommentReply;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LX
 * @date 2025/12/03
 * @description 评论回复映射器
 */
@Mapper
public interface CommentReplyMapper extends BaseMapper<CommentReply> {}

