package me.jiangcai.user.notice.wechat;

import me.jiangcai.user.notice.UserNoticeService;
import me.jiangcai.user.notice.demo.BaseTest;
import me.jiangcai.user.notice.demo.SimpleNotice;
import me.jiangcai.wx.model.message.SimpleTemplateMessageParameter;
import me.jiangcai.wx.model.message.TemplateMessageParameter;
import me.jiangcai.wx.model.message.TemplateMessageStyle;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author CJ
 */
@ContextConfiguration(classes = {UserNoticeWechatConfig.class})
public class WechatSendSupplierTest extends BaseTest {

    @Autowired
    private WechatSendSupplier wechatSendSupplier;
    @Autowired
    private UserNoticeService userNoticeService;

    @Before
    public void before() {
        wechatSendSupplier.registerTemplateMessage(new SimpleNotice(), new TemplateMessageStyle() {
            @Override
            public Collection<? extends TemplateMessageParameter> parameterStyles() {
                return Arrays.asList(
                        new SimpleTemplateMessageParameter("p1", "{0}{1}")
                        , new SimpleTemplateMessageParameter("p2", "比如{0}")
                );
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
                return "YoWOhKTShg9oCJmWT_41A45OgmcdstHHlZiPdFiSOGI";
            }

            @Override
            public void setTemplateId(String templateId) {

            }
        }, "https://www.baidu.com/{0}");

//        userNoticeService.registerSendSupplier(wechatSendSupplier);
    }

}