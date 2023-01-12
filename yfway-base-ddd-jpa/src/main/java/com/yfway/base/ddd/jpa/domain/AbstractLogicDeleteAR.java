package com.yfway.base.ddd.jpa.domain;

import com.yfway.base.ddd.jpa.domain.ext.LogicDelete;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Version;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Where;

/**
 * @author hon_him
 * @since 2022-10-17
 */
@Getter
@Setter
@ToString(callSuper = true)
@RequiredArgsConstructor
@MappedSuperclass
@Where(clause = "deleted = false")
public abstract class AbstractLogicDeleteAR<A extends AbstractLogicDeleteAR<A, ID>, ID> extends AbstractAuditAR<A, ID> implements
    LogicDelete {

    @Version
    @Column(
        name = "version"
    )
    private Long version;

    @Column(
        name = "deleted",
        nullable = false
    )
    @Comment("是否删除")
    private Boolean deleted;

    @Override
    public boolean isDeleted() {
        return Objects.nonNull(this.deleted) ? this.deleted : false;
    }

    @PrePersist
    protected void prePersist() {
        this.setDeleted(false);
        this.setVersion(1L);
    }

    public A logicDelete() {
        A a = (A) this;
        this.setDeleted(true);
//        Optional.ofNullable(eventBuilder())
//            .map(builder -> builder.apply(DaoAction.LOGIC_DELETE))
//            .ifPresent(this::registerEvent);
        return a;
    }

}
