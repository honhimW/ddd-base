package com.yfway.base.ddd.jpa.domain.listener;

import com.yfway.base.ddd.jpa.domain.AbstractAuditAR;
import com.yfway.base.ddd.jpa.domain.DomainEntity;
import com.yfway.base.ddd.jpa.domain.ext.Auditor;
import com.yfway.base.ddd.jpa.domain.ext.LogicDelete;
import com.yfway.base.ddd.jpa.domain.ext.SelectEvent;
import com.yfway.base.ddd.jpa.model.DaoAction;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hon_him
 * @since 2022-10-27
 */
@Transactional
public class AuditingExtListener {

    @PrePersist
    public void postPersist(Object entity) {
        if (entity instanceof AbstractAuditAR<?, ?> abstractAuditAR && Objects.isNull(abstractAuditAR.getAuditor())) {
            abstractAuditAR.setAuditor(new Auditor());
        }
    }

}
