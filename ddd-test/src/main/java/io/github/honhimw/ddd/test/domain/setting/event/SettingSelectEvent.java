package io.github.honhimw.ddd.test.domain.setting.event;

import io.github.honhimw.ddd.jpa.domain.event.DomainEvent;
import io.github.honhimw.ddd.jpa.model.DaoAction;
import io.github.honhimw.ddd.test.domain.setting.Setting;
import io.github.honhimw.ddd.test.domain.setting.SettingPK;

/**
 * @author hon_him
 * @since 2022-10-25
 */

public class SettingSelectEvent extends DomainEvent<Setting, SettingPK> {

    public SettingSelectEvent() {
        super(DaoAction.SELECT);
    }
}
