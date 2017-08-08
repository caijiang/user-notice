package me.jiangcai.user.notice.demo;

import javax.annotation.PostConstruct;

/**
 * @author CJ
 */
public interface MyService {

    boolean sendMessage();

    @PostConstruct
    void init();

}
