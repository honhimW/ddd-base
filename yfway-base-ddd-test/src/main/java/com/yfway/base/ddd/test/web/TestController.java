package com.yfway.base.ddd.test.web;

import com.yfway.base.ddd.test.domain.user.User;
import com.yfway.base.ddd.test.domain.user.repository.UserRepository;
import com.yfway.base.ddd.test.domain.user.service.UserServiceImpl;
import com.yfway.base.utils.YfJsonUtils;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hon_him
 * @since 2022-10-25
 */
@RestController
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/insert")
    public String insert() {
        User entity = new User();
        entity.setId("1");
        entity.setName("honhim");
        entity.setPhoneNumber("12324");
        userRepository.saveAndFlush(entity);
        return YfJsonUtils.toJson(entity);
    }

    @RequestMapping("/update")
    public String update() {
        userRepository.update("1", user -> user.setName(user.getName().toUpperCase()));
        User entity = userRepository.findById("1").get();
        return YfJsonUtils.toJson(entity);
    }

    @Autowired
    private EntityManager entityManager;

    @RequestMapping("/ldelete")
    @Transactional
    public String ldelete() {
        userRepository.logicDelete("1");
        return "logic_delete";
    }

    @RequestMapping("/delete")
    public String delete() {
        userService.delete("1");
        return "delete";
    }

}
