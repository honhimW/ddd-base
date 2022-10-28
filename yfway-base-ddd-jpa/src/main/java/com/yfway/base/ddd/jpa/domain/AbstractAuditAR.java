package com.yfway.base.ddd.jpa.domain;

import com.yfway.base.ddd.jpa.model.DaoAction;
import com.yfway.base.ddd.jpa.util.ValidatorUtils;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Consumer;
import javax.persistence.Column;
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
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditAR<A extends AbstractAuditAR<A, ID>, ID> extends AbstractAR<A, ID> implements
    DomainEntity<A, ID> {

    @Column(
        name = "created_at",
        updatable = false
    )
    @Comment("创建时间")
    @NotNull
    @CreatedDate
    private Instant createdAt;

    @Column(
        name = "updated_at"
    )
    @Comment("更新时间")
    @NotNull
    @LastModifiedDate
    private Instant updatedAt;

    @Column(
        name = "created_by",
        updatable = false
    )
    @Comment("创建人")
    @CreatedBy
    private String createdBy;

    @Column(
        name = "updated_by"
    )
    @Comment("更新人")
    @LastModifiedBy
    private String updatedBy;

//    @PreUpdate
//    protected void preUpdate() {
//        updateEvent();
//    }
//
//    @PreRemove
//    protected void preRemove() {
//        deleteEvent();
//    }
//
//    @PostPersist
//    protected void insertEvent() {
//        Optional.ofNullable(eventBuilder())
//            .map(builder -> builder.apply(DaoAction.INSERT))
//            .ifPresent(this::registerEvent);
//    }
//
//    protected void updateEvent() {
//        Optional.ofNullable(eventBuilder())
//            .map(builder -> builder.apply(DaoAction.UPDATE))
//            .ifPresent(this::registerEvent);
//    }
//
//    protected void deleteEvent() {
//        Optional.ofNullable(eventBuilder())
//            .map(builder -> builder.apply(DaoAction.DELETE))
//            .ifPresent(this::registerEvent);
//    }
//
//    @PostLoad
//    protected void selectEvent() {
//        if (enableSelectEvent()) {
//            Optional.ofNullable(eventBuilder())
//                .map(builder -> builder.apply(DaoAction.SELECT))
//                .ifPresent(this::registerEvent);
//        }
//    }

    public void validate() {
        ValidatorUtils.validate(this);
    }


    /**
     * 默认不开启读事件
     */
    protected boolean enableSelectEvent() {
        return false;
    }

    public A update(Consumer<A> updater) {
        A a = (A) this;
        updater.accept(a);
//        updateEvent();
        return a;
    }

}
