package io.github.honhimw.ddd.test.domain.user.repository;

import io.github.honhimw.ddd.jpa.domain.repository.BaseRepository;
import io.github.honhimw.ddd.test.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, String> {

    Page<User> findByNameLike(String name, Pageable pageable);

}