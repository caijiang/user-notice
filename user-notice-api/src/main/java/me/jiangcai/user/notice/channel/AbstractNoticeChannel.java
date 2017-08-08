package me.jiangcai.user.notice.channel;

import me.jiangcai.user.notice.NoticeChannel;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author CJ
 */
public abstract class AbstractNoticeChannel implements NoticeChannel, Serializable {

    @Override
    public boolean equals(Object o) {
        return this == o || Objects.equals(getClass(), o.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }
}
