package com.lx.blog.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.blog.repository.dao.impl.mapper.entity.UserSetting;
import java.util.Optional;

/**
 * @author 李旭
 * @date 2025/11/14
 * @description 用户设置映射器接口
 */
public interface UserSettingDao extends IService<UserSetting> {

    /**
     * 根据用户ID查询设置
     * @param userId 用户ID
     * @return 用户设置，可为空
     */
    Optional<UserSetting> getByUserId(String userId);

    /**
     * 按用户ID写入或更新设置（幂等）
     * @param setting 用户设置实体
     */
    void upsertSetting(UserSetting setting);
}
