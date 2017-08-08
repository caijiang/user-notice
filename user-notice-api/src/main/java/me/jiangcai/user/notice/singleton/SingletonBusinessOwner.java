package me.jiangcai.user.notice.singleton;

import me.jiangcai.user.notice.BusinessOwner;

/**
 * 单业务宿主模式
 * 在Spring中提供该bean即可
 *
 * @author CJ
 */
public interface SingletonBusinessOwner {

    /**
     * @return 唯一的业务宿主
     */
    BusinessOwner globalBusinessOwner();

}
