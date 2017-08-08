package me.jiangcai.user.notice.simple.supplier;

import me.jiangcai.user.notice.NoticeChannel;
import me.jiangcai.user.notice.UserNoticeType;
import me.jiangcai.user.notice.differentiation.entity.UserNoticeTypeDifferentiation;
import me.jiangcai.user.notice.supplier.UserNoticeSendSupplier;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author CJ
 */
public class SimpleSuppiler implements UserNoticeSendSupplier {
    @Override
    public Set<? extends NoticeChannel> supportedChannel() {
        return null;
    }

    @Override
    public void sendUserNotice(UserNoticeTypeDifferentiation differentiation, NoticeChannel channel, Map<String, Object> fromCredential, Map<String, Object> toCredential
            , UserNoticeType type, Object[] parameters) throws Exception {
        // 如果是发送短信 我们称之为文本类型
//        String text = type.toText(Locale.CHINESE, parameters);
        // 如果是发送微信模板消息的话那东西就多了
        // 用户需支持openId
        // 服务器需支持me.jiangcai.wx.model.PublicAccount
        // 这个消息的 模板id  没有必要自动创建模板id
        // 这个消息的 模板参数列表
    }
}
