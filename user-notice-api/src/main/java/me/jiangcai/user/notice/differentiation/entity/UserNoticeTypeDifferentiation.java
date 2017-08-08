package me.jiangcai.user.notice.differentiation.entity;

import lombok.Getter;
import lombok.Setter;
import me.jiangcai.user.notice.differentiation.entity.support.JsonConverter;
import me.jiangcai.user.notice.differentiation.entity.support.LocaleConverter;
import me.jiangcai.user.notice.differentiation.entity.support.UserNoticeTypeDifferentiationPK;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import java.util.Locale;
import java.util.Map;

/**
 * 所有模板都是传统Java消息模板
 *
 * @author CJ
 * @see java.text.MessageFormat#format(String, Object...)
 */
@Setter
@Getter
@Entity
@IdClass(UserNoticeTypeDifferentiationPK.class)
public class UserNoticeTypeDifferentiation {

    /**
     * 渲染文本的模板key
     */
    public static final String Differentiation_Text_Template_Define = "text_template";
    /**
     * 渲染html的模板key
     */
    public static final String Differentiation_HTML_Template_Define = "html_template";

    /**
     * 业务宿主主键，如果为""则表示所有业务宿主公用
     */
    @Id
    @Column(length = 36)
    private String businessOwnerId = "";
    /**
     * 类型主键，不可为null
     */
    @Id
    @Column(length = 36)
    private String typeId;
    /**
     * 特定语言，如果为 {@link Locale#ROOT} 表示所有语言公用
     */
    @Id
    @Convert(converter = LocaleConverter.class)
    @Column(length = 10)
    private Locale locale = Locale.ROOT;

    /**
     * 个性化定义
     * 数据将以json保存，数据表若支持则可以将其类型更正为JSON
     */
    @Lob
    @Column(columnDefinition = "text")
    @Convert(converter = JsonConverter.class)
    private Map<String, Object> defines;

}
