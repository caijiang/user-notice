package me.jiangcai.user.notice;

import java.util.Map;

/**
 * 通知通道的证书持有者
 *
 * @author CJ
 */
public interface ChannelCredentialHolder {
    /**
     * @param channel 特定通知通道
     * @return 是否支持该通知通道
     */
    boolean supportNoticeChannel(NoticeChannel channel);

    /**
     * @param channel 具体通道
     * @return 支持该通道的证书
     */
    Map<String, Object> channelCredential(NoticeChannel channel);
}
