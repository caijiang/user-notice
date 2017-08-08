package me.jiangcai.user.notice.differentiation.bean;

import me.jiangcai.user.notice.BusinessOwner;
import me.jiangcai.user.notice.UserNoticeType;
import me.jiangcai.user.notice.differentiation.UserNoticeDifferentiationService;
import me.jiangcai.user.notice.differentiation.entity.UserNoticeTypeDifferentiation;
import me.jiangcai.user.notice.differentiation.repository.UserNoticeTypeDifferentiationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author CJ
 */
@Service
public class UserNoticeDifferentiationServiceImpl implements UserNoticeDifferentiationService {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserNoticeTypeDifferentiationRepository userNoticeTypeDifferentiationRepository;

    @Override
    public void updateIfAbsent(UserNoticeType type, String key, Object value) {
        // 如果一个都没有就添加一个
        List<UserNoticeTypeDifferentiation> list = userNoticeTypeDifferentiationRepository.findByTypeId(type.id());

        if (list.isEmpty()) {
            UserNoticeTypeDifferentiation differentiation = new UserNoticeTypeDifferentiation();
            differentiation.setTypeId(type.id());
            userNoticeTypeDifferentiationRepository.save(differentiation);
            list = userNoticeTypeDifferentiationRepository.findByTypeId(type.id());
        }

        list.forEach(userNoticeTypeDifferentiation -> {
            Map<String, Object> map;
            if (userNoticeTypeDifferentiation.getDefines() == null) {
                map = new HashMap<>();
            } else {
                map = new HashMap<>(userNoticeTypeDifferentiation.getDefines());
            }

            map.putIfAbsent(key, value);
            userNoticeTypeDifferentiation.setDefines(map);
//            userNoticeTypeDifferentiation.getDefines().putIfAbsent(key, value);
//            userNoticeTypeDifferentiation.setDefines(userNoticeTypeDifferentiation.getDefines());
//            userNoticeTypeDifferentiationRepository.save(userNoticeTypeDifferentiation);
        });
    }

    @Override
    public UserNoticeTypeDifferentiation bestDifferentiation(BusinessOwner from, UserNoticeType type, Locale locale) {
        List<UserNoticeTypeDifferentiation> list = userNoticeTypeDifferentiationRepository.findByTypeId(type.id());
        if (list.isEmpty())
            return null;

        return list.stream()
                // 如果存在语言 from 都匹配的
                .filter(userNoticeTypeDifferentiation -> userNoticeTypeDifferentiation.getBusinessOwnerId().equals(from.id()))
                .filter(userNoticeTypeDifferentiation -> locale == null || userNoticeTypeDifferentiation.getLocale().equals(locale))
                .findFirst()
                .orElse(
                        // 只要求宿主匹配
                        list.stream()
                                .filter(userNoticeTypeDifferentiation -> userNoticeTypeDifferentiation.getBusinessOwnerId().equals(from.id()))
                                .filter(userNoticeTypeDifferentiation -> userNoticeTypeDifferentiation.getLocale().equals(Locale.ROOT))
                                .findFirst()
                                .orElse(
                                        // 只要求类型匹配
                                        list.stream()
                                                .filter(userNoticeTypeDifferentiation -> userNoticeTypeDifferentiation.getLocale().equals(Locale.ROOT))
                                                .filter(userNoticeTypeDifferentiation -> userNoticeTypeDifferentiation.getBusinessOwnerId().length() == 0)
                                                .findFirst()
                                                .orElse(null)
                                )
                );
    }

}
