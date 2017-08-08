package me.jiangcai.user.notice.demo;

import me.jiangcai.user.notice.UserNoticeConfig;
import me.jiangcai.wx.classics.SinglePublicAccountSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author CJ
 */
@Configuration
@Import(UserNoticeConfig.class)
@ComponentScan("me.jiangcai.user.notice.demo.bean")
public class DemoProjectConfig {

//    @Bean
//    public SinglePublicAccountSupplier singlePublicAccountSupplier(){
//        return new SinglePublicAccountSupplier();
//    }

}
