package com.lx.blog.repository.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.ModerationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LX
 * @date 2025/12/03
 * @description 审核日志映射器
 */
@Mapper
public interface ModerationLogMapper extends BaseMapper<ModerationLog> {}

