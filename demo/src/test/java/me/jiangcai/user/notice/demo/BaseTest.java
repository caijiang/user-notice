package me.jiangcai.user.notice.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 都有的测试
 *
 * @author CJ
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatasourceConfig.class, DemoProjectConfig.class})
public abstract class BaseTest {

    @Autowired
    private MyService myService;

    @Test
    public void sendMessage() throws Exception {
        assertThat(myService.sendMessage())
                .isTrue();
    }
}
