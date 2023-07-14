package io.github.honhimw.ddd.test.domain.user.service;

import io.github.honhimw.ddd.test.domain.user.User;
import io.github.honhimw.ddd.test.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hon_him
 * @since 2022-10-26
 */
@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository repository;

    @Transactional
    public void delete(String id) {
        repository.findById(id).ifPresent(user ->
            repository.delete(user)
        );
    }

    @Transactional
    public void delete(User user) {
        repository.delete(user);
    }

    @Transactional
    public void update(String id) {
        repository.findById(id).ifPresent(user -> {
            user.setName(user.getName().toUpperCase());
        });
    }

}
