package me.jiangcai.user.notice;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 如果是单机模式可查看{@link me.jiangcai.user.notice.singleton.SingletonBusinessOwner}
 *
 * @author CJ
 */
@Configuration
@ComponentScan("me.jiangcai.user.notice.service")
public class UserNoticeConfig {
}
