package me.jiangcai.user.notice.demo;

import me.jiangcai.user.notice.NoticeChannel;
import me.jiangcai.user.notice.User;
import me.jiangcai.user.notice.email.EmailSendSupplier;
import me.jiangcai.user.notice.email.UserNoticeEmailConfig;
import me.jiangcai.user.notice.wechat.WechatNoticeChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CJ
 */
public class SimpleUser implements User {
    @Override
    public boolean supportNoticeChannel(NoticeChannel channel) {
        if (channel == WechatNoticeChannel.templateMessage)
            return true;
        if (channel == UserNoticeEmailConfig.EMAIL)
            return true;
        //
        return false;
    }

    @Override
    public Map<String, Object> channelCredential(NoticeChannel channel) {
        if (channel == WechatNoticeChannel.templateMessage) {
            Map<String, Object> map = new HashMap<>();
            map.put(WechatNoticeChannel.OpenIdCredentialTo, "oiKvNt0neOAB8ddS0OzM_7QXQDZw");
            return map;
        }
        if (channel == UserNoticeEmailConfig.EMAIL) {
            Map<String, Object> map = new HashMap<>();
            map.put(EmailSendSupplier.EmailCredentialTo, "jiangcai@huobanplus.com");
            map.put(EmailSendSupplier.EmailNameCredentialTo, "好人？");
            return map;
        }
        return null;
    }
}
