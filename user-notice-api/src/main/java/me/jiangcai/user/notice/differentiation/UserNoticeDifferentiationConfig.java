package me.jiangcai.user.notice.differentiation;

import me.jiangcai.user.notice.UserNoticeConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 支持差异化的配置
 * JPA需要增配
 * me.jiangcai.user.notice.differentiation.entity
 *
 * @author CJ
 */
@Configuration
@Import(UserNoticeConfig.class)
@ComponentScan("me.jiangcai.user.notice.differentiation.bean")
@EnableJpaRepositories("me.jiangcai.user.notice.differentiation.repository")
public class UserNoticeDifferentiationConfig {
}
