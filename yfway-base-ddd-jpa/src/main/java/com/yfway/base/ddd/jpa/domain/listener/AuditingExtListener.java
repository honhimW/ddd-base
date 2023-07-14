package com.yfway.base.ddd.jpa.domain.listener;

import com.yfway.base.ddd.jpa.domain.AbstractAuditAR;
import com.yfway.base.ddd.jpa.domain.ext.Auditor;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.PrePersist;
import java.util.Objects;

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
