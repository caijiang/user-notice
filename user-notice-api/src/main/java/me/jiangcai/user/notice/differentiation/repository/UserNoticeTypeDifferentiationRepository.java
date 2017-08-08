package me.jiangcai.user.notice.differentiation.repository;

import me.jiangcai.user.notice.differentiation.entity.UserNoticeTypeDifferentiation;
import me.jiangcai.user.notice.differentiation.entity.support.UserNoticeTypeDifferentiationPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author CJ
 */
public interface UserNoticeTypeDifferentiationRepository extends JpaRepository<UserNoticeTypeDifferentiation
        , UserNoticeTypeDifferentiationPK>, JpaSpecificationExecutor<UserNoticeTypeDifferentiation> {

    List<UserNoticeTypeDifferentiation> findByTypeId(String noticeTypeId);

}
