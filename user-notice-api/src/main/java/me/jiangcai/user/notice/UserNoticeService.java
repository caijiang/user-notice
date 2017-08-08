package me.jiangcai.user.notice;

import me.jiangcai.user.notice.supplier.UserNoticeSendSupplier;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 用户通知通常是作为一个非核心业务存在，所以及时发送失败也不会抛出异常阻断核心流程
 *
 * @author CJ
 */
public interface UserNoticeService {

    /**
     * @param type 注册通知类型
     */
    void registerNoticeType(UserNoticeType type);

    /**
     * @param supplier 注册发送供应商
     */
    void registerSendSupplier(UserNoticeSendSupplier supplier);

    /**
     * 发送通知给目标用户
     *
     * @param from       业务主，如果提供了{@link me.jiangcai.user.notice.singleton.SingletonBusinessOwner}则可选
     * @param to         目标用户
     * @param channels   可选的目标渠道，如果为空系统自动帮助选择
     * @param typeId     类型id {@link UserNoticeType#id()}
     * @param parameters 消息参数
     * @return 是否成功的集合
     */
    default Map<User, Boolean> sendMessage(BusinessOwner from, Collection<? extends User> to
            , Set<? extends NoticeChannel> channels, String typeId, Object... parameters) {
        HashMap<User, Boolean> results = new HashMap<>();
        to.forEach(user
                -> results.put(user, sendMessage(from, user, channels, typeId, parameters)));
        return results;
    }

    /**
     * 发送通知给目标用户
     *
     * @param from       业务主，如果提供了{@link me.jiangcai.user.notice.singleton.SingletonBusinessOwner}则可选
     * @param to         目标用户
     * @param channels   可选的目标渠道，如果为空系统自动帮助选择
     * @param typeId     类型id {@link UserNoticeType#id()}
     * @param parameters 消息参数
     * @return true 如果成功发送的话
     */
    boolean sendMessage(BusinessOwner from, User to, Set<? extends NoticeChannel> channels
            , String typeId, Object... parameters);

    default Map<User, Boolean> sendMessage(BusinessOwner from, Collection<? extends User> to
            , Set<? extends NoticeChannel> channels, UserNoticeType type, Object... parameters) {
        HashMap<User, Boolean> results = new HashMap<>();
        to.forEach(user
                -> results.put(user, sendMessage(from, user, channels, type, parameters)));
        return results;
    }

    boolean sendMessage(BusinessOwner from, User to, Set<? extends NoticeChannel> channels
            , UserNoticeType type, Object... parameters);

}
