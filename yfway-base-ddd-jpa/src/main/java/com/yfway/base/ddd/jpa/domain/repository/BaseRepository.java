package com.yfway.base.ddd.jpa.domain.repository;

import com.yfway.base.ddd.jpa.domain.AbstractAuditAR;
import com.yfway.base.ddd.jpa.domain.AbstractLogicDeleteAR;
import java.util.function.Consumer;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hon_him
 * @since 2022-10-17
 */
@Transactional
@NoRepositoryBean
public interface BaseRepository<T extends AbstractLogicDeleteAR<T, ID>, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    default void logicDelete(ID id) {
        findById(id).map(AbstractLogicDeleteAR::logicDelete).ifPresent(this::save);
    }

    default void logicDelete(T t) {
        findBy(Example.of(t), FetchableFluentQuery::first).map(AbstractLogicDeleteAR::logicDelete).ifPresent(this::save);
    }

    default void logicDeleteAll(Iterable<ID> ids) {
        findAllById(ids).stream().map(AbstractLogicDeleteAR::logicDelete).forEach(this::save);
    }

    default T update(ID id, Consumer<T> updater) {
        return findById(id).map(t -> t.update(updater)).map(this::save).orElseThrow();
    }

}
