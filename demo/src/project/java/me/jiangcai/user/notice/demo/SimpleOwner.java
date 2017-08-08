package me.jiangcai.user.notice.demo;

import me.jiangcai.user.notice.BusinessOwner;
import me.jiangcai.user.notice.NoticeChannel;
import me.jiangcai.user.notice.email.EmailSendSupplier;
import me.jiangcai.user.notice.email.UserNoticeEmailConfig;
import me.jiangcai.user.notice.wechat.DebugPublicAccount;
import me.jiangcai.user.notice.wechat.WechatNoticeChannel;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author CJ
 */
public class SimpleOwner implements BusinessOwner {

    private DebugPublicAccount debugPublicAccount = new DebugPublicAccount();

    @Override
    public String id() {
        return null;
    }

    @Override
    public boolean supportNoticeChannel(NoticeChannel channel) {
        if (channel == WechatNoticeChannel.templateMessage)
            return true;
        if (channel == UserNoticeEmailConfig.EMAIL)
            return true;
        // others
        return false;
    }

    @Override
    public Map<String, Object> channelCredential(NoticeChannel channel) {
        if (channel == WechatNoticeChannel.templateMessage) {
//            CollectionUtils.map 我们的公众号
            Map<String, Object> map = new HashMap<>();
            map.put(WechatNoticeChannel.PublicAccountCredentialFrom, debugPublicAccount);
            return map;
        }
        if (channel == UserNoticeEmailConfig.EMAIL) {
            Map<String, Object> map = new HashMap<>();
            map.put(EmailSendSupplier.ConsumerCredentialFrom, new Consumer<Email>() {
                @Override
                public void accept(Email email) {
                    email.setHostName("smtp.163.com");
                    email.setSmtpPort(994);
//                    final String requiredProperty = environment.getRequiredProperty("huotao.huotu.email.password");
                    String requiredProperty = "iamhuotu";
                    email.setAuthenticator(new DefaultAuthenticator("huotu_hangzhou"
                            , requiredProperty));
                    email.setSSLOnConnect(true);
                    try {
                        email.setFrom("huotu_hangzhou@163.com");
                    } catch (EmailException e) {
                        e.printStackTrace();
                    }
                }
            });
            return map;
        }
        // others
        return null;
    }
}
