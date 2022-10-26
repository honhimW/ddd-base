package com.yfway.base.ddd.test.domain.user.event;


import com.yfway.base.ddd.jpa.domain.event.JpaBaseEvent;
import com.yfway.base.ddd.jpa.model.DaoAction;
import com.yfway.base.ddd.test.domain.user.User;

/**
 * @author hon_him
 * @since 2022-10-21
 */

public class UserEvent extends JpaBaseEvent<User, String> {

    public UserEvent(DaoAction action) {
        super(action);
    }
}
