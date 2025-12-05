package com.lx.blog.repository.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.UserSetting;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 李旭
 * @date 2025/11/14
 * @description 用户设置Mapper
 */
@Mapper
public interface UserSettingMapper extends BaseMapper<UserSetting> {
}
