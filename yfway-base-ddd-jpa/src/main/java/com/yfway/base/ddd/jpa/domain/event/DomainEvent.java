package com.yfway.base.ddd.jpa.domain.event;

import com.yfway.base.ddd.jpa.model.DaoAction;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hon_him
 * @since 2022-10-21
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class DomainEvent<T, ID> implements Serializable {

    @NotNull
    private final DaoAction action;

    private ID id;

    private T entity;

    public DomainEvent(DaoAction action) {
        this.action = action;
    }

    public DomainEvent<T, ID> id(ID id) {
        this.setEntity(entity);
        return this;
    }

    public DomainEvent<T, ID> entity(T entity) {
        this.setEntity(entity);
        return this;
    }

}
