package me.jiangcai.user.notice;

/**
 * 业务主体
 *
 * @author CJ
 */
public interface BusinessOwner extends ChannelCredentialHolder {
    /**
     *
     * @return 字符串形式的业务主体识别符，长度不可超过36
     */
    String id();
}
