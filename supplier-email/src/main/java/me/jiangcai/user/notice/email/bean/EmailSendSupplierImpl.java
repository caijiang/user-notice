package me.jiangcai.user.notice.email.bean;

import me.jiangcai.user.notice.NoticeChannel;
import me.jiangcai.user.notice.UserNoticeService;
import me.jiangcai.user.notice.UserNoticeType;
import me.jiangcai.user.notice.differentiation.entity.UserNoticeTypeDifferentiation;
import me.jiangcai.user.notice.email.EmailSendSupplier;
import me.jiangcai.user.notice.email.UserNoticeEmailConfig;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author CJ
 */
@Service
public class EmailSendSupplierImpl implements EmailSendSupplier {

    @Override
    public Set<? extends NoticeChannel> supportedChannel() {
        return Collections.singleton(UserNoticeEmailConfig.EMAIL);
    }

    @Override
    public void sendUserNotice(UserNoticeTypeDifferentiation differentiation, NoticeChannel channel
            , Map<String, Object> fromCredential, Map<String, Object> toCredential, UserNoticeType type
            , Object[] parameters) throws Exception {
        HtmlEmail email = new HtmlEmail();

        email.setCharset("UTF-8");

        //noinspection unchecked
        Consumer<Email> consumer = (Consumer<Email>) fromCredential.get(ConsumerCredentialFrom);
        consumer.accept(email);

        final String name = (String) toCredential.get(EmailNameCredentialTo);
        if (StringUtils.isEmpty(name))
            email.addTo((String) toCredential.get(EmailCredentialTo));
        else
            email.addTo((String) toCredential.get(EmailCredentialTo), name);

        email.setHtmlMsg(type.toHTML(differentiation, null, parameters));
        email.setSubject(type.title());
        email.send();
    }

    @Autowired
    private UserNoticeService userNoticeService;

    @PostConstruct
    @Override
    public void init() {
        userNoticeService.registerSendSupplier(this);
    }
}
