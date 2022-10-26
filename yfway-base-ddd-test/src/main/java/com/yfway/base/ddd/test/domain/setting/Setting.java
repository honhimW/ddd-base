package com.yfway.base.ddd.test.domain.setting;

import com.yfway.base.ddd.jpa.domain.AbstractCasAR;
import com.yfway.base.ddd.jpa.domain.event.JpaBaseEvent;
import com.yfway.base.ddd.jpa.model.DaoAction;
import com.yfway.base.ddd.test.domain.setting.event.SettingDeleteEvent;
import com.yfway.base.ddd.test.domain.setting.event.SettingInsertEvent;
import com.yfway.base.ddd.test.domain.setting.event.SettingLDeleteEvent;
import com.yfway.base.ddd.test.domain.setting.event.SettingSelectEvent;
import com.yfway.base.ddd.test.domain.setting.event.SettingUpdateEvent;
import java.util.function.Function;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
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
public class Setting extends AbstractCasAR<Setting, SettingPK> {

    @EmbeddedId
    private SettingPK id;

    @Column
    private String description;

    @Column(nullable = false)
    @NotNull
    private String value;

    @Override
    protected Function<DaoAction, ? extends JpaBaseEvent<Setting, SettingPK>> eventBuilder() {
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
