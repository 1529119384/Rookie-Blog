package com.lx.blog.repository.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.blog.repository.dao.UserLogDao;
import com.lx.blog.repository.dao.impl.mapper.UserLogMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.UserLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author LX
 * @date 2025/12/7
 * @description 用户操作日志数据访问接口实现类
 */
@Repository
public class UserLogDaoImpl extends ServiceImpl<UserLogMapper, UserLog> implements UserLogDao {
}
