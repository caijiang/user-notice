package me.jiangcai.user.notice.wechat.bean;

import me.jiangcai.user.notice.NoticeChannel;
import me.jiangcai.user.notice.UserNoticeService;
import me.jiangcai.user.notice.UserNoticeType;
import me.jiangcai.user.notice.differentiation.UserNoticeDifferentiationService;
import me.jiangcai.user.notice.differentiation.entity.UserNoticeTypeDifferentiation;
import me.jiangcai.user.notice.wechat.UserNoticeWechatConfig;
import me.jiangcai.user.notice.wechat.WechatNoticeChannel;
import me.jiangcai.user.notice.wechat.WechatSendSupplier;
import me.jiangcai.wx.model.PublicAccount;
import me.jiangcai.wx.model.message.SimpleTemplateMessageParameter;
import me.jiangcai.wx.model.message.TemplateMessageParameter;
import me.jiangcai.wx.model.message.TemplateMessageStyle;
import me.jiangcai.wx.protocol.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CJ
 */
@Service
public class WechatSendSupplierImpl implements WechatSendSupplier {

    @Autowired
    private UserNoticeDifferentiationService userNoticeDifferentiationService;
    @Autowired
    private UserNoticeService userNoticeService;

    @Override
    public Set<? extends NoticeChannel> supportedChannel() {
        return Collections.singleton(WechatNoticeChannel.templateMessage);
    }

    @Override
    public void sendUserNotice(UserNoticeTypeDifferentiation differentiation, NoticeChannel channel
            , Map<String, Object> fromCredential
            , Map<String, Object> toCredential
            , UserNoticeType type, Object[] parameters) throws Exception {

        if (channel == WechatNoticeChannel.templateMessage) {
            if (differentiation == null)
                throw new IllegalStateException("请先注册该模板消息");
            //
            PublicAccount account = (PublicAccount) fromCredential.get(WechatNoticeChannel.PublicAccountCredentialFrom);
            Protocol protocol = Protocol.forAccount(account);

            String openId = (String) toCredential.get(WechatNoticeChannel.OpenIdCredentialTo);

            String urlTemplate = (String) differentiation.getDefines().get(UserNoticeWechatConfig.URLTemplateDefine);
            String url;
            if (StringUtils.isEmpty(urlTemplate))
                url = null;
            else
                url = MessageFormat.format(urlTemplate, parameters);

            protocol.sendTemplate(openId, new TemplateMessageStyle() {
                @Override
                public Collection<? extends TemplateMessageParameter> parameterStyles() {
                    //noinspection unchecked
                    Collection<Object> set = (Collection<Object>) differentiation.getDefines().get(UserNoticeWechatConfig.ParameterArrayDefine);
                    return set.stream()
                            .map(WechatSendSupplierImpl.this::fromMap)
                            .collect(Collectors.toSet());
                }

                @Override
                public String getTemplateIdShort() {
                    return null;
                }

                @Override
                public String getTemplateTitle() {
                    return null;
                }

                @Override
                public String getIndustryId() {
                    return null;
                }

                @Override
                public String getTemplateId() {
                    return (String) differentiation.getDefines().get(UserNoticeWechatConfig.TemplateIdDefine);
                }

                @Override
                public void setTemplateId(String templateId) {

                }
            }, url, null, parameters);
        } else
            throw new IllegalArgumentException("not support for:" + channel);
    }

    @Override
    public void registerTemplateMessage(UserNoticeType type, TemplateMessageStyle defaultStyle, String urlTemplate) {
        userNoticeService.registerNoticeType(type);

        userNoticeDifferentiationService.updateIfAbsent(type, UserNoticeWechatConfig.TemplateIdDefine
                , defaultStyle.getTemplateId());
        userNoticeDifferentiationService.updateIfAbsent(type, UserNoticeWechatConfig.URLTemplateDefine, urlTemplate);
        // 支持的字段
        // name,pattern 打个包呗
        userNoticeDifferentiationService.updateIfAbsent(type, UserNoticeWechatConfig.ParameterArrayDefine
                , defaultStyle.parameterStyles().stream()
                        .map(this::toMap).collect(Collectors.toSet()));
    }

    @PostConstruct
    @Override
    public void init() {
        userNoticeService.registerSendSupplier(this);
    }

    private HashMap<String, Object> toMap(TemplateMessageParameter templateMessageParameter) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", templateMessageParameter.getName());
        final Color defaultColor = templateMessageParameter.getDefaultColor();
        if (defaultColor != null)
            data.put("color", defaultColor.getRGB());
        data.put("pattern", templateMessageParameter.getPattern());
        return data;
    }

    private TemplateMessageParameter fromMap(Object data) {
        //noinspection unchecked
        Map<String, Object> map = (Map<String, Object>) data;
        SimpleTemplateMessageParameter simpleTemplateMessageParameter
                = new SimpleTemplateMessageParameter((String) map.get("name"), (String) map.get("pattern"));
        if (map.containsKey("color")) {
            simpleTemplateMessageParameter.setDefaultColor(new Color((Integer) map.get("color")));
        }
        return simpleTemplateMessageParameter;
    }
}
