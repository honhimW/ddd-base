package com.yfway.base.ddd.jpa.domain.listener;

import com.yfway.base.ddd.jpa.domain.DomainEntity;
import com.yfway.base.ddd.jpa.domain.ext.LogicDelete;
import com.yfway.base.ddd.jpa.domain.ext.SelectEvent;
import com.yfway.base.ddd.jpa.model.DaoAction;
import java.util.Optional;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hon_him
 * @since 2022-10-27
 */
@Transactional
public class CRUDListener {

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostPersist
    public void postPersist(Object entity) {
        if (entity instanceof DomainEntity<?, ?> domainEntity) {
            publish(domainEntity, DaoAction.INSERT);
        }
    }

    @PostLoad
    public void postLoad(Object entity) {
        if (entity instanceof SelectEvent && entity instanceof DomainEntity<?, ?> domainEntity) {
            publish(domainEntity, DaoAction.SELECT);
        }
    }

    @PostUpdate
    public void postUpdate(Object entity) {
        if (entity instanceof DomainEntity<?, ?> domainEntity) {
            if (entity instanceof LogicDelete logicDeleteEntity) {
                if (logicDeleteEntity.isDeleted()) {
                    publish(domainEntity, DaoAction.LOGIC_DELETE);
                    return;
                }
            }
            publish(domainEntity, DaoAction.UPDATE);
        }
    }

    @PostRemove
    public void postRemove(Object entity) {
        if (entity instanceof DomainEntity<?, ?> domainEntity) {
            publish(domainEntity, DaoAction.DELETE);
        }
    }

    protected void publish(DomainEntity<?, ?> domainEntity, DaoAction action) {
        Optional.of(domainEntity)
            .map(DomainEntity::eventBuilder)
            .map(daoActionFunction -> daoActionFunction.apply(action))
            .ifPresent(event -> publisher.publishEvent(event));
    }

}