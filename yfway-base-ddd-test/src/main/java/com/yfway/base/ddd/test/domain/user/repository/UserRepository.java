package com.yfway.base.ddd.test.domain.user.repository;

import com.yfway.base.ddd.jpa.domain.repository.BaseRepository;
import com.yfway.base.ddd.test.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, String> {

    Page<User> findByNameLike(String name, Pageable pageable);

}