package com.lx.blog.service.auth.biz.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.lx.blog.common.api.IpParseApi;
import com.lx.blog.common.exception.NotFoundException;
import com.lx.blog.common.response.Result;
import com.lx.blog.common.utils.I18nUtils;
import com.lx.blog.domain.vo.UserVo;
import com.lx.blog.repository.dao.UserDao;
import com.lx.blog.repository.dao.impl.mapper.entity.User;
import com.lx.blog.service.auth.biz.UserProfileBizService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @author LX
 * @date 2025/12/3
 * @description 用户个人信息业务服务实现类
 */
@Service
@RequiredArgsConstructor
public class UserProfileBizServiceImpl implements UserProfileBizService {

    @NotNull private final UserDao userDao;
    @NotNull private final IpParseApi ipParseApi;

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户信息
     */
    @Override
    public Result<UserVo> getProfile() {
        String id = StpUtil.getTokenInfo().getLoginId().toString();
        User user = userDao.getById(id);
        if (user == null) {
            String msg = I18nUtils.t("user.notfound");
            throw new NotFoundException(msg);
        }
        UserVo userVo = UserVo.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .lastLogin(user.getLastLoginAt())
                .avatarUrl(user.getAvatarUrl())
                .emailVerified(Integer.valueOf(1).equals(user.getEmailVerified()))
                .build();
        try {
            String ipAddress = ipParseApi.parseIpAddress(user.getLastLoginIp()).getRegion();
            String unknown = I18nUtils.t("common.unknown");
            userVo.setIpAddress(ipAddress.isEmpty() ? unknown : ipAddress);
        } catch (IOException e) {
            String unknown = I18nUtils.t("common.unknown");
            userVo.setIpAddress(unknown);
        }
        return Result.ok(userVo);
    }

     /**
      * 根据用户ID获取用户名
      *
      * @param userId 用户ID
      * @return 用户名
      */
    @Override
    public Result<Map<String, String>> getUsernameById(String userId) {
        User user = userDao.getById(userId);
        if (user == null) {
            throw new NotFoundException(I18nUtils.t("user.notfound"));
        }
        return Result.ok(Map.of("username", user.getUsername()));
    }
}
