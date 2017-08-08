package me.jiangcai.user.notice.email;

import me.jiangcai.user.notice.NoticeChannel;
import me.jiangcai.user.notice.UserNoticeConfig;
import me.jiangcai.user.notice.channel.AbstractNoticeChannel;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 渠道为{@link #EMAIL}
 *
 * @author CJ
 */
@Configuration
@Import(UserNoticeConfig.class)
@ComponentScan("me.jiangcai.user.notice.email.bean")
public class UserNoticeEmailConfig {
    public static final NoticeChannel EMAIL = new AbstractNoticeChannel() {
    };
}
