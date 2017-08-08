package me.jiangcai.user.notice.email;

import me.jiangcai.user.notice.supplier.UserNoticeSendSupplier;

import javax.annotation.PostConstruct;

/**
 * @author CJ
 */
public interface EmailSendSupplier extends UserNoticeSendSupplier {
    /**
     * 内容为一Consumer 目标是Email
     */
    String ConsumerCredentialFrom = "email_consumer";
    /**
     * 内容为字符串，邮件地址
     */
    String EmailCredentialTo = "emailId";
    /**
     * 可选，内容为字符串，收件人名称
     */
    String EmailNameCredentialTo = "emailName";

    @PostConstruct
    void init();
}
