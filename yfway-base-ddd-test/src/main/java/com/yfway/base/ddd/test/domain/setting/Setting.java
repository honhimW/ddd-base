package com.yfway.base.ddd.test.domain.setting;

import com.yfway.base.ddd.jpa.domain.AbstractLogicDeleteAR;
import com.yfway.base.ddd.jpa.domain.event.DomainEvent;
import com.yfway.base.ddd.jpa.model.DaoAction;
import com.yfway.base.ddd.test.domain.setting.event.SettingDeleteEvent;
import com.yfway.base.ddd.test.domain.setting.event.SettingInsertEvent;
import com.yfway.base.ddd.test.domain.setting.event.SettingLDeleteEvent;
import com.yfway.base.ddd.test.domain.setting.event.SettingSelectEvent;
import com.yfway.base.ddd.test.domain.setting.event.SettingUpdateEvent;
import java.util.function.Function;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import com.yfway.base.ddd.test.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

/**
 * @author hon_him
 * @since 2022-10-25
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(
    name = "user_setting"
)
@Where(clause = "deleted = false")
public class Setting extends AbstractLogicDeleteAR<Setting, SettingPK> {

    @EmbeddedId
    private SettingPK id;

    @Column
    private String description;

    @Column(nullable = false)
    @NotNull
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "user_ref_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private User user;

    public Setting(String description, String value) {
        this.description = description;
        this.value = value;
    }

    public Setting(String description, String value, User user) {
        this.description = description;
        this.value = value;
        this.user = user;
    }

    @Override
    public Function<DaoAction, ? extends DomainEvent<Setting, SettingPK>> eventBuilder() {
        return action -> {
            switch (action) {
                case INSERT -> {
                    return new SettingInsertEvent().entity(this);
                }
                case UPDATE -> {
                    return new SettingUpdateEvent().entity(this);
                }
                case DELETE -> {
                    return new SettingDeleteEvent().entity(this);
                }
                case LOGIC_DELETE -> {
                    return new SettingLDeleteEvent().entity(this);
                }
                case SELECT -> {
                    return new SettingSelectEvent().entity(this);
                }
                default -> {
                    return null;
                }
            }
        };
    }
}
