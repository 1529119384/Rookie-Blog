package com.lx.blog.repository.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.blog.repository.dao.impl.mapper.UserSettingMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.UserSetting;
import com.lx.blog.repository.dao.UserSettingDao;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import java.util.Optional;
import java.time.LocalDateTime;

/**
 * @author 李旭
 * @date 2025/11/14
 * @description 用户设置映射器实现类
 */
@Repository
public class UserSettingDaoImpl extends ServiceImpl<UserSettingMapper, UserSetting> implements UserSettingDao {

    /**
     * 根据用户ID查询设置
     *
     * @param userId 用户ID
     * @return 用户设置，可为空
     */
    @Override
    public Optional<UserSetting> getByUserId(String userId) {
        return Optional.ofNullable(baseMapper.selectOne(new LambdaQueryWrapper<UserSetting>().eq(UserSetting::getUserId, userId)));
    }

    /**
     * 按用户ID写入或更新设置（幂等）
     *
     * @param setting 用户设置实体
     */
    @Override
    public void upsertSetting(UserSetting setting) {
        UserSetting exists = baseMapper.selectOne(new LambdaQueryWrapper<UserSetting>().eq(UserSetting::getUserId, setting.getUserId()));
        if (exists == null) {
            if (setting.getCreatedAt() == null) setting.setCreatedAt(LocalDateTime.now());
            if (setting.getUpdatedAt() == null) setting.setUpdatedAt(LocalDateTime.now());
            baseMapper.insert(setting);
        } else {
            baseMapper.update(null, new LambdaUpdateWrapper<UserSetting>()
                    .eq(UserSetting::getUserId, setting.getUserId())
                    .set(setting.getTheme() != null, UserSetting::getTheme, setting.getTheme())
                    .set(setting.getNotifyEnabled() != null, UserSetting::getNotifyEnabled, setting.getNotifyEnabled())
                    .set(setting.getLanguage() != null, UserSetting::getLanguage, setting.getLanguage())
                    .set(setting.getTimezone() != null, UserSetting::getTimezone, setting.getTimezone())
                    .set(UserSetting::getUpdatedAt, LocalDateTime.now()));
        }
    }
}
