package com.lx.blog.service.auth.biz.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.temp.SaTempUtil;
import com.lx.blog.common.constants.ResultCode;
import com.lx.blog.common.exception.BaseException;
import com.lx.blog.common.exception.NotFoundException;
import com.lx.blog.common.response.Result;
import com.lx.blog.common.utils.I18nUtils;
import com.lx.blog.repository.dao.UserDao;
import com.lx.blog.repository.dao.impl.mapper.entity.User;
import com.lx.blog.service.auth.biz.UserEmailBizService;
import com.lx.blog.service.email.EmailService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author 李旭
 * @date 2025/12/3
 * @description 邮箱业务服务实现类
 */
@Service
@RequiredArgsConstructor
public class UserUserEmailBizServiceImpl implements UserEmailBizService {

    @NotNull private final UserDao userDao;
    @NotNull private final EmailService emailService;
    @Value("${app.host:http://localhost:8080}")
    private String appHost;

    /**
     * 是否已完成邮箱验证
     * @return 是否已验证
     */
    @Override
    public Result<Boolean> isEmailVerified() {
        String userId = StpUtil.getLoginIdAsString();
        User user = userDao.getById(userId);
        return Result.ok(user != null && Integer.valueOf(1).equals(user.getEmailVerified()));
    }

    /**
     * 申请邮箱验证（发送验证邮件）
     * @return 申请结果
     */
    @Override
    public Result<Object> requestEmailVerification() {
        String userId = StpUtil.getLoginIdAsString();
        User user = userDao.getById(userId);
        if (user == null) {
            String msg = I18nUtils.t("user.notfound");
            throw new NotFoundException(msg);
        }
        if (Integer.valueOf(1).equals(user.getEmailVerified())) {
            return Result.ok();
        }
        String token = SaTempUtil.createToken(userId, 24 * 60 * 60);
        String verifyUrl = appHost + "/api/user/email/verification/confirm?token=" + token;
        emailService.sendVerificationEmail(user.getId(), user.getEmail(), verifyUrl);
        return Result.ok();
    }

    /**
     * 确认邮箱验证
     * @param token 临时验证令牌
     * @return 验证是否成功
     */
    @Override
    public Result<Object> confirmEmailToken(String token) {
        Object val = SaTempUtil.parseToken(token);
        if (val == null) {
            String msg = I18nUtils.t("token.invalid");
            throw new BaseException(ResultCode.BAD_REQUEST, msg);
        }
        String userId = String.valueOf(val);
        User user = userDao.getById(userId);
        if (user == null) {
            throw new NotFoundException(I18nUtils.t("user.notfound"));
        }
        if (Integer.valueOf(1).equals(user.getEmailVerified())) {
            return Result.ok();
        }
        user.setEmailVerified(1);
        user.setEmailVerifiedAt(java.time.LocalDateTime.now());
        userDao.updateById(user);
        // 令牌一次性使用：清除令牌
        SaTempUtil.deleteToken(token);
        String msg = I18nUtils.t("email.verify.success");
        return Result.ok(msg);
    }
}
