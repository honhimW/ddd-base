package io.github.honhimw.ddd.jpa.domain;

import io.github.honhimw.ddd.jpa.domain.listener.CRUDListener;
import io.github.honhimw.ddd.jpa.util.ValidatorUtils;
import java.util.function.Consumer;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.domain.AbstractAggregateRoot;

/**
 * @author hon_him
 * @since 2022-10-17
 */
@MappedSuperclass
@EntityListeners(CRUDListener.class)
public abstract class AbstractAR<A extends AbstractAR<A, ID>, ID> extends AbstractAggregateRoot<A> implements
    DomainEntity<A, ID> {

    public void validate() {
        ValidatorUtils.validate(this);
    }

    /**
     * 默认不开启读事件
     */
    protected boolean enableSelectEvent() {
        return false;
    }

    @SuppressWarnings("unchecked")
    public A update(Consumer<A> updater) {
        A a = (A) this;
        updater.accept(a);
//        updateEvent();
        return a;
    }

}
