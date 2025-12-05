package com.lx.blog.app;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 李旭
 * @date 2025/11/12
 * @description
 */
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.lx.blog"
})
@MapperScan(basePackages = {
        "com.lx.blog.repository.dao.impl.mapper"
})
public class RookieBlogApplication {
    public static void main(String[] args) {
        ConfigurableEnvironment env = SpringApplication.run(RookieBlogApplication.class, args).getEnvironment();

        String property = env.getProperty("spring.application.name");
        String hostAddress;  // 获取主机地址
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            hostAddress = "127.0.0.1";
            log.warn("无法获取主机IP,使用默认地址: 127.0.0.1");
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");

        log.info("""
		
		,-.----.                          ,-.                              ,---,.  ,--,                      \s
		\\    /  \\                     ,--/ /|  ,--,                      ,'  .'  ,--.'|                      \s
		;   :    \\   ,---.    ,---. ,--. :/ |,--.'|                    ,---.' .' |  | :     ,---.            \s
		|   | .\\ :  '   ,'\\  '   ,'\\:  : ' / |  |,                     |   |  |: :  : '    '   ,'\\  ,----._,.\s
		.   : |: | /   /   |/   /   |  '  /  `--'_      ,---.          :   :  :  |  ' |   /   /   |/   /  ' /\s
		|   |  \\ :.   ; ,. .   ; ,. '  |  :  ,' ,'|    /     \\         :   |    ;'  | |  .   ; ,. |   :     |\s
		|   : .  /'   | |: '   | |: |  |   \\ '  | |   /    /  |        |   :     |  | :  '   | |: |   | .\\  .\s
		;   | |  \\'   | .; '   | .; '  : |. \\|  | :  .    ' / |        |   |   . '  : |__'   | .; .   ; ';  |\s
		|   | ;\\  |   :    |   :    |  | ' \\ '  : |__'   ;   /|        '   :  '; |  | '.'|   :    '   .   . |\s
		:   ' | \\.'\\   \\  / \\   \\  /'  : |--'|  | '.''   |  / |        |   |  | ;;  :    ;\\   \\  / `---`-'| |\s
		:   : :-'   `----'   `----' ;  |,'   ;  :    |   :    |        |   :   / |  ,   /  `----'  .'__/\\_: |\s
		|   |.'                     '--'     |  ,   / \\   \\  /         |   | ,'   ---`-'           |   :    :\s
		`---'                                 ---`-'   `----'          `----'                       \\   \\  / \s
		                                                                                             `--`-'  \s
		Rookie Blog                                                                                    v1.0.0
		\r-----------------------------------------------------------------------------------------------------
		Application '{}' is running Success!
		接口文档访问地址:
		   本地访问地址:   http://127.0.0.1:{}{}/doc
		   在线访问地址:   http://{}:{}{}/doc
		配置文件:   {}
		-----------------------------------------------------------------------------------------------------""",
                property,
                serverPort,
                contextPath,
                hostAddress,
                serverPort,
                contextPath,
                env.getActiveProfiles());
    }

}
