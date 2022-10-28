package com.yfway.base.ddd.jpa.domain;

import com.yfway.base.ddd.jpa.domain.listener.CRUDListener;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.domain.AbstractAggregateRoot;

/**
 * @author hon_him
 * @since 2022-10-17
 */
@MappedSuperclass
@EntityListeners(CRUDListener.class)
public abstract class AbstractAR<A extends AbstractAR<A, ID>, ID> extends AbstractAggregateRoot<A> implements
    DomainEntity<A, ID> {

}
