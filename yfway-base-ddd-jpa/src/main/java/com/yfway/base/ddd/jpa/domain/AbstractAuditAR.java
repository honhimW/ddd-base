package com.yfway.base.ddd.jpa.domain;

import com.yfway.base.ddd.jpa.domain.ext.Auditor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

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
