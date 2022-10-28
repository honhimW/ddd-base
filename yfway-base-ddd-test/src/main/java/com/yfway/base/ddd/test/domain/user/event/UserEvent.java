package com.yfway.base.ddd.test.domain.user.event;


import com.yfway.base.ddd.jpa.domain.event.DomainEvent;
import com.yfway.base.ddd.jpa.model.DaoAction;
import com.yfway.base.ddd.test.domain.user.User;

/**
 * @author hon_him
 * @since 2022-10-21
 */

public class UserEvent extends DomainEvent<User, String> {

    public UserEvent(DaoAction action) {
        super(action);
    }
}
