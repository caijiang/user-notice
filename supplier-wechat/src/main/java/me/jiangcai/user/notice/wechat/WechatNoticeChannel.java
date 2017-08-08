package me.jiangcai.user.notice.wechat;

import me.jiangcai.user.notice.NoticeChannel;

/**
 * @author CJ
 */
public enum WechatNoticeChannel implements NoticeChannel {
    /**
     * 模板消息
     */
    templateMessage,
    /**
     * 客服消息？尚未实现
     */
    message;

    /**
     * 证书中包含{@link me.jiangcai.wx.model.PublicAccount}的值
     */
    public static String PublicAccountCredentialFrom = "wechat_public_account";
    /**
     * 证书中包含openId的值
     */
    public static String OpenIdCredentialTo = "wechat_openId";
}
