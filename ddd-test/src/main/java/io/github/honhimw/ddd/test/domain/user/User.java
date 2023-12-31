package io.github.honhimw.ddd.test.domain.user;

import io.github.honhimw.ddd.jpa.domain.AbstractLogicDeleteAR;
import io.github.honhimw.ddd.jpa.domain.event.DomainEvent;
import io.github.honhimw.ddd.jpa.model.DaoAction;
import io.github.honhimw.ddd.test.domain.user.event.UserEvent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.function.Function;

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
        return action -> new UserEvent(action).entity(this);
    }

}
