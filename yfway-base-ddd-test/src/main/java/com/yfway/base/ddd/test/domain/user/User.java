package com.yfway.base.ddd.test.domain.user;

import com.yfway.base.ddd.jpa.domain.AbstractLogicDeleteAR;
import com.yfway.base.ddd.jpa.domain.event.DomainEvent;
import com.yfway.base.ddd.jpa.model.DaoAction;
import com.yfway.base.ddd.test.domain.user.event.UserEvent;
import com.yfway.base.utils.YfJsonUtils;
import java.time.LocalDateTime;
import java.util.function.Function;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

/**
 * @author hon_him
 * @since 2022-09-29
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(
    name = "user",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "phoneNumber")
    },
    indexes = {
//        @Index(columnList = "phoneNumber")
    }
)
public class User extends AbstractLogicDeleteAR<User, String> {

    @Id
    private String id;

    @Column
    private String name;

    @Column(nullable = false)
    @NotNull
    private String phoneNumber;

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column
    private LocalDateTime time;

    @Embedded
    @NotNull
    private Password password;

    @Override
    public Function<DaoAction, ? extends DomainEvent<User, String>> eventBuilder() {
        return action -> new UserEvent(action).entity(YfJsonUtils.fromJson(YfJsonUtils.toJson(this), this.getClass()));
    }

}
