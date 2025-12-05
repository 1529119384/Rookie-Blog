package com.lx.blog.repository.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 登录日志映射器
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {}

