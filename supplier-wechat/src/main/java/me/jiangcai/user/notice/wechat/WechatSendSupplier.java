package me.jiangcai.user.notice.wechat;

import me.jiangcai.user.notice.UserNoticeType;
import me.jiangcai.user.notice.supplier.UserNoticeSendSupplier;
import me.jiangcai.wx.model.message.TemplateMessageStyle;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @author CJ
 */
public interface WechatSendSupplier extends UserNoticeSendSupplier {

    /**
     * 注册模板消息
     *
     * @param type         消息类型
     * @param defaultStyle 模板消息基础样式
     * @param urlTemplate  URL模板；可以为null表示不支持url
     */
    @Transactional
    void registerTemplateMessage(UserNoticeType type, TemplateMessageStyle defaultStyle, String urlTemplate);

    @PostConstruct
    void init();
}
