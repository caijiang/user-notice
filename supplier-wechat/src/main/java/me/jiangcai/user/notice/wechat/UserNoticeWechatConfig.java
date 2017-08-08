package me.jiangcai.user.notice.wechat;

import me.jiangcai.user.notice.differentiation.UserNoticeDifferentiationConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 使用微信供应商则必须开通差异化
 * 同时需要进行初始化
 *
 * @author CJ
 */
@Configuration
@Import(UserNoticeDifferentiationConfig.class)
@ComponentScan("me.jiangcai.user.notice.wechat.bean")
public class UserNoticeWechatConfig {
    public static final String URLTemplateDefine = "wechat_url_template";
    public static final String TemplateIdDefine = "wechat_template_id";

    public static final String ParameterArrayDefine = "wechat_parameter_array";
}
