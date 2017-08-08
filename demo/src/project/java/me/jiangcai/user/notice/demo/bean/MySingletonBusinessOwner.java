package me.jiangcai.user.notice.demo.bean;

import me.jiangcai.user.notice.BusinessOwner;
import me.jiangcai.user.notice.demo.SimpleOwner;
import me.jiangcai.user.notice.singleton.SingletonBusinessOwner;
import org.springframework.stereotype.Component;

/**
 * @author CJ
 */
@Component
public class MySingletonBusinessOwner implements SingletonBusinessOwner {
    @Override
    public BusinessOwner globalBusinessOwner() {
        return new SimpleOwner();
    }
}
