package me.jiangcai.user.notice.demo;

import me.jiangcai.user.notice.UserNoticeType;

import java.util.Locale;

/**
 * @author CJ
 */
public class SimpleNotice implements UserNoticeType {
    @Override
    public String id() {
        return "1";
    }

    @Override
    public String title() {
        return "建议通知";
    }

    @Override
    public boolean allowDifferentiation() {
        return true;
    }

    @Override
    public String defaultToText(Locale locale, Object[] parameters) {
        return null;
    }

    @Override
    public String defaultToHTML(Locale locale, Object[] parameters) {
        return "<h1>哈哈</h1>";
    }

    @Override
    public Class<?>[] expectedParameterTypes() {
        return new Class<?>[]{
                String.class, String.class
        };
    }
}
