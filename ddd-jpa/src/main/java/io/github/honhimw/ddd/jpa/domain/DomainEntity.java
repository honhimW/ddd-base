package io.github.honhimw.ddd.jpa.domain;

import io.github.honhimw.ddd.jpa.domain.event.DomainEvent;
import io.github.honhimw.ddd.jpa.model.DaoAction;

import java.util.function.Function;

/**
 * @author hon_him
 * @since 2022-10-17
 */
public interface DomainEntity<A extends DomainEntity<A, ID>, ID> {

    ID getId();

    default Function<DaoAction, ? extends DomainEvent<A, ID>> eventBuilder() {
        return action -> null;
    }

}
