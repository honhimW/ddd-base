package com.yfway.base.ddd.test.jpa;

import com.yfway.base.ddd.test.TestApp;
import com.yfway.base.ddd.test.domain.setting.ISetting;
import com.yfway.base.ddd.test.domain.setting.Setting;
import com.yfway.base.ddd.test.domain.setting.SettingPK;
import com.yfway.base.ddd.test.domain.setting.repository.SettingRepository;
import com.yfway.base.ddd.test.domain.user.Password;
import com.yfway.base.ddd.test.domain.user.User;
import com.yfway.base.ddd.test.domain.user.UserStatus;
import com.yfway.base.ddd.test.domain.user.repository.UserRepository;
import com.yfway.base.utils.YfJsonUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author hon_him
 * @since 2023-05-13
 */

@Slf4j
@SpringBootTest(classes = TestApp.class)
public class SpecificationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SettingRepository settingRepository;

    @BeforeEach
    void init() {
        User user = new User(
            "1",
            "honhim",
            "13",
            UserStatus.NORMAL,
            LocalDateTime.now(),
            Password.of("123")
        );
        user = userRepository.save(user);
        Setting setting = new Setting(
            new SettingPK("1", 1L),
            null,
            "world",
            user
        );
        settingRepository.save(setting);
    }

    @Test
    @SneakyThrows
    void specColumns() {
        List<Setting> settings = settingRepository.findAll((root, query, cb) -> {
//            query.multiselect(root.get("value"), root.get("description"), root.get("user"));

            return null;
        });
        for (Object setting : settings) {
            System.out.println("===========================================================");
            System.out.println(YfJsonUtils.toJsonWithDefaultPrettyPrinter(setting));
            System.out.println("===========================================================");
        }
    }

    @Autowired
    private EntityManager em;

    @Test
    @SneakyThrows
    void em() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Setting> query = cb.createQuery(Setting.class);
        Root<Setting> root = query.from(Setting.class);
        query.multiselect(root.get("value"), root.get("description"));
        List<Setting> resultList = em.createQuery(query).getResultList();
        resultList.forEach(System.out::println);
    }

}
