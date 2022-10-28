package com.yfway.base.ddd.jpa.domain.listener;

import com.yfway.base.ddd.jpa.domain.DomainEntity;
import com.yfway.base.ddd.jpa.domain.ext.LogicDelete;
import com.yfway.base.ddd.jpa.model.DaoAction;
import java.util.Optional;
import javax.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hon_him
 * @since 2022-10-27
 */
@Transactional
public class LogicDeleteListener {

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostUpdate
    public void postUpdate(Object entity) {
        if (entity instanceof LogicDelete logicDeleteEntity) {
            if (logicDeleteEntity.isDeleted() && entity instanceof DomainEntity<?, ?> domainEntity) {
                Optional.of(domainEntity)
                    .map(DomainEntity::eventBuilder)
                    .map(daoActionFunction -> daoActionFunction.apply(DaoAction.LOGIC_DELETE))
                    .ifPresent(event -> publisher.publishEvent(event));
            }
        }
    }

}
