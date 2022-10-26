package com.yfway.base.ddd.mp.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.util.Assert;

/**
 * TODO hook方法发布事件
 * @author hon_him
 * @since 2022-10-26
 */

public abstract class AbstractAR<A extends AbstractAR<A>> extends Model<A> {

    private transient final List<Object> domainEvents = new ArrayList<>();

    protected <T> T registerEvent(T event) {
        Assert.notNull(event, "Domain event must not be null!");
        this.domainEvents.add(event);
        return event;
    }

    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    public Collection<Object> domainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    @SuppressWarnings("unchecked")
    protected final A andEventsFrom(A aggregate) {
        Assert.notNull(aggregate, "Aggregate must not be null!");
        this.domainEvents.addAll(aggregate.domainEvents());
        return (A) this;
    }


    @SuppressWarnings("unchecked")
    protected final A andEvent(Object event) {
        registerEvent(event);
        return (A) this;
    }

}
