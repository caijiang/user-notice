package me.jiangcai.user.notice.differentiation;

import me.jiangcai.user.notice.BusinessOwner;
import me.jiangcai.user.notice.UserNoticeType;
import me.jiangcai.user.notice.differentiation.entity.UserNoticeTypeDifferentiation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

/**
 * @author CJ
 */
public interface UserNoticeDifferentiationService {

    /**
     * 更新若指定键值未存在
     *
     * @param type  通知类型
     * @param key   键值
     * @param value 预设置的值
     */
    @Transactional
    void updateIfAbsent(UserNoticeType type, String key, Object value);

    /**
     * @param from   宿主
     * @param type   类型
     * @param locale 可选的语言，null表示无所谓
     * @return 最合适的差异化配置
     */
    @Transactional(readOnly = true)
    UserNoticeTypeDifferentiation bestDifferentiation(BusinessOwner from, UserNoticeType type, Locale locale);
}
