package com.yfway.base.ddd.test.domain.user;

import com.yfway.base.ddd.jpa.domain.AbstractCasAR;
import com.yfway.base.ddd.jpa.domain.event.JpaBaseEvent;
import com.yfway.base.ddd.jpa.model.DaoAction;
import com.yfway.base.ddd.test.domain.user.event.UserEvent;
import com.yfway.base.ddd.test.domain.user.mapper.UserMapper;
import java.util.Objects;
import java.util.function.Function;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.hibernate.Hibernate;
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
@Where(clause = "deleted = false")
public class User extends AbstractCasAR<User, String> {

    @Id
    private String id;

    @Column
    private String name;

    @Column(nullable = false)
    @NotNull
    private String phoneNumber;

    @Override
    protected Function<DaoAction, ? extends JpaBaseEvent<User, String>> eventBuilder() {
        return action -> new UserEvent(action).entity(UserMapper.MAPPER.copy(this));
    }

}
