package com.yfway.base.ddd.test;

import com.yfway.base.ddd.common.IPageRequest;
import com.yfway.base.ddd.common.IPageRequest.ConditionColumn;
import com.yfway.base.ddd.common.IPageRequest.MatchingType;
import com.yfway.base.ddd.jpa.util.PageUtils;
import com.yfway.base.ddd.test.domain.user.Password;
import com.yfway.base.ddd.test.domain.user.User;
import com.yfway.base.ddd.test.domain.user.UserStatus;
import com.yfway.base.ddd.test.domain.user.repository.UserRepository;
import com.yfway.base.ddd.test.domain.user.service.UserServiceImpl;
import com.yfway.base.utils.DateTimeUtils;
import com.yfway.base.utils.JsonUtils;
import com.yfway.base.utils.RandomUtils;
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
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
            entity.setPassword(Password.of("123"));
            entity.setStatus(ThreadLocalRandom.current().nextBoolean() ? UserStatus.NORMAL : UserStatus.SICK);
            entity.setTime(LocalDateTime.now().plusSeconds(ThreadLocalRandom.current().nextInt(-100000, 100000)));
            userRepository.saveAndFlush(entity);
        }
        for (int i = 10; i < 100; i++) {
            User entity = new User();
            entity.setId(String.valueOf(i));
            entity.setPhoneNumber(RandomUtils.randomId(11, RandomUtils.NUMBER_CHARS));
            entity.setName(RandomUtils.randomId(10));
            entity.setPassword(Password.of("123"));
            entity.setStatus(ThreadLocalRandom.current().nextBoolean() ? UserStatus.NORMAL : UserStatus.SICK);
            entity.setTime(LocalDateTime.now().plusSeconds(ThreadLocalRandom.current().nextInt(-100000, 100000)));
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
        page.forEach(user -> log.info(JsonUtils.toJson(user)));
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void list() {
        IPageRequest<User> iPageRequest = new IPageRequest<>();
        iPageRequest.setPageNo(0);
        iPageRequest.setPageSize(1000);
        ConditionColumn e = ConditionColumn.of("status", "SICK");
        String current = "2023-02-07 17:27:00";
        ConditionColumn e1 = ConditionColumn.of("time", current, MatchingType.LE);
        iPageRequest.setConditions(List.of(e, e1));
        Page<User> paging = PageUtils.paging(userRepository, iPageRequest);
        LocalDateTime localDateTime = DateTimeUtils.parseLocalDateTime(current, "yyyy-MM-dd HH:mm:ss");
        Assert.state(paging.stream().map(User::getTime).allMatch(localDateTime1 -> localDateTime1.isBefore(localDateTime)), "条件异常");
        Assert.state(paging.stream().allMatch(user -> user.getStatus() == UserStatus.SICK), "条件异常");
    }

    public void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (Exception ignored) {
        }
    }

}
