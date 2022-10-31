package com.yfway.base.ddd.r2dbc.domain.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

/**
 * @author hon_him
 * @since 2022-10-31
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends ReactiveSortingRepository<T, ID> {

}
