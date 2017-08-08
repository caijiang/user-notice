package me.jiangcai.user.notice;

import me.jiangcai.user.notice.differentiation.entity.UserNoticeTypeDifferentiation;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * 用户通知类型，由客户端系统提供
 *
 * @author CJ
 */
public interface UserNoticeType {

    /**
     * @return 该类型的可持久唯一识别符，长度不可超过36
     */
    String id();

    /**
     * @return 标题或者说主体
     */
    String title();

    /**
     * 这个选项会生效前提是载入了{@link me.jiangcai.user.notice.differentiation.UserNoticeDifferentiationConfig}
     *
     * @return 是否允许差异化调整该类型
     */
    boolean allowDifferentiation();

    /**
     * 默认的toText实现，在差异化中会被{@link UserNoticeTypeDifferentiation#Differentiation_Text_Template_Define}模板代替
     *
     * @param locale     语言
     * @param parameters 通知参数
     * @return 以文本方式渲染
     * @see me.jiangcai.user.notice.differentiation.entity.UserNoticeTypeDifferentiation#Differentiation_Text_Template_Define
     */
    String defaultToText(Locale locale, Object[] parameters);

    /**
     * @param differentiation 差异化
     * @param locale          语言
     * @param parameters      通知参数
     * @return 以文本方式渲染
     */
    default String toText(UserNoticeTypeDifferentiation differentiation, Locale locale, Object[] parameters) {
        if (differentiation == null)
            return defaultToText(locale, parameters);
        if (CollectionUtils.isEmpty(differentiation.getDefines()))
            return defaultToText(locale, parameters);

        String template = (String) differentiation.getDefines().get(UserNoticeTypeDifferentiation.Differentiation_Text_Template_Define);
        if (template == null)
            return defaultToText(locale, parameters);
        return MessageFormat.format(template, parameters);
    }

    /**
     * 默认的toHTML实现，在差异化中会被{@link UserNoticeTypeDifferentiation#Differentiation_HTML_Template_Define}模板代替
     *
     * @param locale     语言
     * @param parameters 通知参数
     * @return 以html方式渲染
     * @see me.jiangcai.user.notice.differentiation.entity.UserNoticeTypeDifferentiation#Differentiation_Text_Template_Define
     */
    String defaultToHTML(Locale locale, Object[] parameters);

    default String toHTML(UserNoticeTypeDifferentiation differentiation, Locale locale, Object[] parameters) {
        if (differentiation == null)
            return defaultToHTML(locale, parameters);
        if (CollectionUtils.isEmpty(differentiation.getDefines()))
            return defaultToHTML(locale, parameters);

        String template = (String) differentiation.getDefines().get(UserNoticeTypeDifferentiation.Differentiation_HTML_Template_Define);
        if (template == null)
            return defaultToHTML(locale, parameters);
        return MessageFormat.format(template, parameters);
    }

    /**
     * 描述它所支持的参数
     *
     * @return 期望的数据类型
     */
    Class<?>[] expectedParameterTypes();


}
