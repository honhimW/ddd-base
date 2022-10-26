package com.yfway.base.ddd.jpa.domain;

import com.yfway.base.ddd.jpa.domain.event.JpaBaseEvent;
import com.yfway.base.ddd.jpa.model.DaoAction;
import com.yfway.base.ddd.jpa.util.ValidatorUtils;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.AbstractAggregateRoot;

/**
 * @author hon_him
 * @since 2022-10-17
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public abstract class AbstractJpaAR<A extends AbstractJpaAR<A, ID>, ID> extends AbstractAggregateRoot<A> {

    public abstract ID getId();

    @Column(
        name = "created_at",
//        nullable = false,
        updatable = false
    )
    @Comment("创建时间")
    @NotNull
    private Instant createdAt;

    @Column(
        name = "updated_at",
        nullable = false
    )
    @Comment("更新时间")
    private Instant updatedAt;

    @Column(
        name = "deleted",
        nullable = false
    )
    @Comment("是否删除")
    private Boolean deleted;

    @PrePersist
    protected void prePersist() {
        Instant now = Instant.now();
        this.setCreatedAt(now);
        this.setUpdatedAt(now);
        this.setDeleted(false);
    }

    @PreUpdate
    protected void preUpdate() {
        this.setUpdatedAt(Instant.now());
        updateEvent();
    }

    @PreRemove
    protected void preRemove() {
        this.setDeleted(true);
        deleteEvent();
    }

    @PostPersist
    protected void insertEvent() {
        Optional.ofNullable(eventBuilder())
            .map(builder -> builder.apply(DaoAction.INSERT))
            .ifPresent(this::registerEvent);
    }

    protected void updateEvent() {
        Optional.ofNullable(eventBuilder())
            .map(builder -> builder.apply(DaoAction.UPDATE))
            .ifPresent(this::registerEvent);
    }

    protected void deleteEvent() {
        Optional.ofNullable(eventBuilder())
            .map(builder -> builder.apply(DaoAction.DELETE))
            .ifPresent(this::registerEvent);
    }

    @PostLoad
    protected void selectEvent() {
        if (enableSelectEvent()) {
            Optional.ofNullable(eventBuilder())
                .map(builder -> builder.apply(DaoAction.SELECT))
                .ifPresent(this::registerEvent);
        }
    }

    public void validate() {
        ValidatorUtils.validate(this);
    }

    protected Function<DaoAction, ? extends JpaBaseEvent<A, ID>> eventBuilder() {
        return action -> null;
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
        updateEvent();
        return a;
    }

    public A logicDelete() {
        A a = (A) this;
        this.setDeleted(true);
        Optional.ofNullable(eventBuilder())
            .map(builder -> builder.apply(DaoAction.LOGIC_DELETE))
            .ifPresent(this::registerEvent);
        return a;
    }

}
