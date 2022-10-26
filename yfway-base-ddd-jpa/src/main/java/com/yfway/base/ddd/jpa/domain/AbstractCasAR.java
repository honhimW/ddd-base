package com.yfway.base.ddd.jpa.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hon_him
 * @since 2022-10-17
 */
@Getter
@Setter
@ToString(callSuper = true)
@RequiredArgsConstructor
@MappedSuperclass
public abstract class AbstractCasAR<A extends AbstractCasAR<A, ID>, ID> extends AbstractJpaAR<A, ID> {

    @Version
    @Column(
        name = "version"
    )
    private Integer version;

    @Override
    public void prePersist() {
        super.prePersist();
        this.setVersion(1);
    }

}
