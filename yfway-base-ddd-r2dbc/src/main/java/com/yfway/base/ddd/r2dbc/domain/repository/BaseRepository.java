package com.yfway.base.ddd.r2dbc.domain.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author hon_him
 * @since 2022-10-31
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends R2dbcRepository<T, ID> {

}
