package me.jiangcai.user.notice.service;

import me.jiangcai.user.notice.BusinessOwner;
import me.jiangcai.user.notice.NoticeChannel;
import me.jiangcai.user.notice.User;
import me.jiangcai.user.notice.UserNoticeService;
import me.jiangcai.user.notice.UserNoticeType;
import me.jiangcai.user.notice.differentiation.UserNoticeDifferentiationService;
import me.jiangcai.user.notice.differentiation.entity.UserNoticeTypeDifferentiation;
import me.jiangcai.user.notice.singleton.SingletonBusinessOwner;
import me.jiangcai.user.notice.supplier.UserNoticeSendSupplier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CJ
 */
@Service
public class UserNoticeServiceImpl implements UserNoticeService {

    private static final Log log = LogFactory.getLog(UserNoticeServiceImpl.class);
    private final Map<String, UserNoticeType> noticeTypeMap = new HashMap<>();
    private final Set<UserNoticeSendSupplier> suppliers = new HashSet<>();

    @Override
    public void registerNoticeType(UserNoticeType type) {
        noticeTypeMap.put(type.id(), type);
        log.info("UserNoticeType:" + type + " has registered.");
    }

    @Override
    public void registerSendSupplier(UserNoticeSendSupplier supplier) {
        suppliers.add(supplier);
        log.info("UserNoticeSendSupplier:" + supplier + " has registered.");
    }

    @Override
    public boolean sendMessage(BusinessOwner from, User to, Set<? extends NoticeChannel> channels, String typeId
            , Object... parameters) {
        UserNoticeType type = noticeTypeMap.get(typeId);
        if (type == null)
            throw new IllegalArgumentException("not supported UserNoticeType:" + typeId);
        return sendMessage(from, to, channels, type, parameters);
    }

    @Override
    public boolean sendMessage(BusinessOwner from2, User to, Set<? extends NoticeChannel> channels, UserNoticeType type
            , Object... parameters) {

        // 校验消息参数是否符合规格
        // 可以为null 但必须属于该类型
        Class<?>[] types = type.expectedParameterTypes();
        if (parameters.length != types.length)
            throw new IllegalArgumentException(type.title() + "支持" + types.length + "个参数，而非" + parameters.length);
        for (int i = 0; i < types.length; i++) {
            Object object = parameters[i];
            if (object != null && !types[i].isAssignableFrom(object.getClass())) {
                throw new IllegalArgumentException("参数" + object + "需要是" + types[i]);
            }
        }

        BusinessOwner from = from(from2);

        // 寻找可用渠道
        HashSet<NoticeChannel> working = new HashSet<>();
        // 1，有供应商支持
        working.addAll(suppliers.stream()
                .map(UserNoticeSendSupplier::supportedChannel)
                .flatMap(Collection::stream)
                // 2，宿主和客户都支持
                .filter(channel -> from.supportNoticeChannel(channel) && to.supportNoticeChannel(channel))
                // 如果要求了类型
                .filter(channel -> CollectionUtils.isEmpty(channels) || channels.contains(channel))
                .collect(Collectors.toSet())
        );

        if (log.isDebugEnabled()) {
            StringBuilder stringBuilder = new StringBuilder("send UserNotice:").append(type.toString())
                    .append(" via channels:");
            working.forEach(channel -> stringBuilder.append(channel).append(","));
            log.debug(stringBuilder.toString());
        }

        // 获取差异化服务
        if (userNoticeDifferentiationService == null && type.allowDifferentiation()) {
            try {
                userNoticeDifferentiationService = applicationContext.getBean(UserNoticeDifferentiationService.class);
            } catch (Exception ignored) {
            }
        }

        // 获取最合适的差异化配置
        UserNoticeTypeDifferentiation differentiation;
        if (!type.allowDifferentiation()) {
            differentiation = null;
        } else if (userNoticeDifferentiationService == null) {
            log.debug("Running in no-differentiation model");
            differentiation = null;
        } else {
            differentiation = userNoticeDifferentiationService.bestDifferentiation(from, type, null);
        }

        // 组装消息
        // 发送
        boolean success = false;
        for (NoticeChannel channel : working) {
            // 一个渠道可以有多个供应商可以处理，那么就让它们处理吧
            //noinspection OptionalGetWithoutIsPresent
            Map<String, Object> fromCredential = from.channelCredential(channel);
            Map<String, Object> toCredential = to.channelCredential(channel);

            for (UserNoticeSendSupplier sendSupplier : suppliers.stream()
                    .filter(sendSupplier -> sendSupplier.supportedChannel().contains(channel)).collect(Collectors.toSet())) {
                try {
                    sendSupplier.sendUserNotice(differentiation, channel, fromCredential, toCredential, type, parameters);
                    success = true;
                } catch (Exception ex) {
                    log.info("NoticeException", ex);
                }
            }
        }

        return success;
    }

    private BusinessOwner globalBusinessOwner;
    @Autowired
    private ApplicationContext applicationContext;
    private UserNoticeDifferentiationService userNoticeDifferentiationService;

    private BusinessOwner from(BusinessOwner from) {
        if (from != null)
            return from;
        if (globalBusinessOwner == null)
            globalBusinessOwner = applicationContext.getBean(SingletonBusinessOwner.class).globalBusinessOwner();
        return globalBusinessOwner;
    }
}
