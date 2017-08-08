package me.jiangcai.user.notice.supplier;

import me.jiangcai.user.notice.NoticeChannel;
import me.jiangcai.user.notice.UserNoticeType;
import me.jiangcai.user.notice.differentiation.entity.UserNoticeTypeDifferentiation;

import java.util.Map;
import java.util.Set;

/**
 * 通知发送解决方案的供应商
 *
 * @author CJ
 */
public interface UserNoticeSendSupplier {

    /**
     * @return 支持的通知通道
     */
    Set<? extends NoticeChannel> supportedChannel();

    /**
     * 发送消息
     *
     * @param differentiation 差异化配置可能为null
     * @param channel         通道
     * @param fromCredential  发送者证书
     * @param toCredential    接收者证书
     * @param type            消息类型
     * @param parameters      消息参数
     * @throws Exception 支持所有可能的异常
     */
    void sendUserNotice(UserNoticeTypeDifferentiation differentiation, NoticeChannel channel, Map<String, Object> fromCredential, Map<String, Object> toCredential
            , UserNoticeType type
            , Object[] parameters) throws Exception;
}
