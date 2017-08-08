package me.jiangcai.user.notice.demo.bean;

import me.jiangcai.user.notice.UserNoticeService;
import me.jiangcai.user.notice.demo.MyService;
import me.jiangcai.user.notice.demo.SimpleNotice;
import me.jiangcai.user.notice.demo.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CJ
 */
@Service
public class MyServiceImpl implements MyService {

    @Autowired
    private UserNoticeService userNoticeService;
    // 发送消息给某个人

    public boolean sendMessage() {
        return userNoticeService.sendMessage(null, new SimpleUser(), null, new SimpleNotice()
                , "hello", "约");
    }

    @Override
    public void init() {
        userNoticeService.registerNoticeType(new SimpleNotice());
    }
}
