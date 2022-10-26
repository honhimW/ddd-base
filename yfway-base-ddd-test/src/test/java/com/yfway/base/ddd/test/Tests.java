package com.yfway.base.ddd.test;

import com.yfway.base.ddd.test.domain.user.User;
import com.yfway.base.ddd.test.domain.user.repository.UserRepository;
import com.yfway.base.ddd.test.domain.user.service.UserServiceImpl;
import com.yfway.base.utils.YfJsonUtils;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.bus.BusAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hon_him
 * @since 2022-10-25
 */

@EnableAutoConfiguration(exclude = BusAutoConfiguration.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Tests {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Test
    @org.junit.jupiter.api.Order(0)
    void insert() {
        List<String> hello = List.of("hello", "world", "honhim");
        for (int i = 0; i < hello.size(); i++) {
            User entity = new User();
            entity.setId(String.valueOf(i));
            entity.setPhoneNumber(String.valueOf(ThreadLocalRandom.current().nextInt(0, 1_000)));
            entity.setName(hello.get(i));
            userRepository.saveAndFlush(entity);
        }
        sleep(1000L);
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void update() {
        User user = new User();
        user.setId("1");
        User update = userRepository.update(user.getId(), user1 -> user1.setName(user1.getName() + 222));
        System.out.println(update.getVersion());
        sleep(1000L);
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void updateInTransaction() {
        String id = "1";
        userRepository.findById(id).ifPresent(user -> log.info(user.getName()));
        userService.update(id);
        userRepository.findById(id).ifPresent(user -> log.info(user.getName()));
        sleep(1000L);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void logicDelete() {
        User user = new User();
        user.setId("1");
        userRepository.logicDelete(user);
        sleep(1000L);
    }

    @Autowired
    private UserServiceImpl userService;

    @Test
    @org.junit.jupiter.api.Order(3)
    void delete() {
        userService.delete("2");
        sleep(1000L);
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void page() {
        Page<User> page = userRepository.findByNameLike("%o%",
            PageRequest.of(0, 10, Sort.by(List.of(Order.desc("phoneNumber")))));
        page.forEach(user -> log.info(YfJsonUtils.toJson(user)));
    }

    public void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (Exception ignored) {
        }
    }

}
