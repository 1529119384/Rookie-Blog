package com.lx.blog.repository.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LX
 * @date 2025/11/13
 * @description 用户Mapper接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
