package com.lx.blog.test;

import com.lx.blog.common.utils.BCryptUtils;
import org.junit.jupiter.api.Test;

/**
 * @author LX
 * @date 2025/11/15
 * @description
 */
public class LoginTest {

    /**
     * 测试登录
     */
    @Test
    public void testLogin() {
        String password = BCryptUtils.encode("LIxu2002");
        System.out.println(password);
    }

}
