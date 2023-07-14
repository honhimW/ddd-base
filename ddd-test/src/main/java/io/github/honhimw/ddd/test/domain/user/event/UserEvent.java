package io.github.honhimw.ddd.test.domain.user.event;


import io.github.honhimw.ddd.jpa.domain.event.DomainEvent;
import io.github.honhimw.ddd.jpa.model.DaoAction;
import io.github.honhimw.ddd.test.domain.user.User;

/**
 * @author hon_him
 * @since 2022-10-21
 */

public class UserEvent extends DomainEvent<User, String> {

    public UserEvent(DaoAction action) {
        super(action);
    }
}
