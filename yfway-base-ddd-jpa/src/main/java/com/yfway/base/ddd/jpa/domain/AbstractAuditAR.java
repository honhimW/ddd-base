package com.yfway.base.ddd.jpa.domain;

import com.yfway.base.ddd.jpa.domain.ext.Auditor;
import com.yfway.base.ddd.jpa.domain.listener.AuditingExtListener;
import com.yfway.base.ddd.jpa.model.DaoAction;
import com.yfway.base.ddd.jpa.util.ValidatorUtils;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Consumer;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author hon_him
 * @since 2022-10-17
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class AbstractAuditAR<A extends AbstractAuditAR<A, ID>, ID> extends AbstractAR<A, ID> implements
    DomainEntity<A, ID> {

    @Embedded
    private Auditor auditor = new Auditor();

}
