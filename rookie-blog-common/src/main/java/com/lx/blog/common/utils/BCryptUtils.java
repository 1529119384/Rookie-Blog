package com.lx.blog.common.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author 李旭
 * @date 2025/11/13
 * @description 加密工具类
 */
public class BCryptUtils {

    // 加密
    public static String encode(String str) {
        return BCrypt.hashpw(str, BCrypt.gensalt());
    }

    /**
     * 校验明文密码与加密后密文是否匹配
     * @param raw 明文密码
     * @param hashed 已存密文
     * @return 是否匹配
     */
    public static boolean matches(String raw, String hashed) {
        return BCrypt.checkpw(raw, hashed);
    }
}
