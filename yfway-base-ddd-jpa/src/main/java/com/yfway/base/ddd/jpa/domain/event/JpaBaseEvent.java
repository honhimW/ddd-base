package com.yfway.base.ddd.jpa.domain.event;

import com.yfway.base.ddd.jpa.domain.AbstractJpaAR;
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
public abstract class JpaBaseEvent<T extends AbstractJpaAR<T, ID>, ID> implements Serializable {

    @NotNull
    private final DaoAction action;

    private T entity;

    public JpaBaseEvent(DaoAction action) {
        this.action = action;
    }

    public JpaBaseEvent<T, ID> entity(T entity) {
        this.setEntity(entity);
        return this;
    }

}
