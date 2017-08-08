package me.jiangcai.user.notice.differentiation.entity.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Locale;

/**
 * @author CJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNoticeTypeDifferentiationPK implements Serializable {

    private static final long serialVersionUID = -6101739068104882878L;

    @Column(length = 36)
    private String businessOwnerId;
    @Column(length = 36, nullable = false)
    private String typeId;
    @Column(length = 10)
    private Locale locale;

}
