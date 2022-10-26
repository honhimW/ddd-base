package com.yfway.base.ddd.test.domain.setting.event;

import com.yfway.base.ddd.jpa.domain.event.JpaBaseEvent;
import com.yfway.base.ddd.jpa.model.DaoAction;
import com.yfway.base.ddd.test.domain.setting.Setting;
import com.yfway.base.ddd.test.domain.setting.SettingPK;

/**
 * @author hon_him
 * @since 2022-10-25
 */

public class SettingInsertEvent extends JpaBaseEvent<Setting, SettingPK> {

    public SettingInsertEvent() {
        super(DaoAction.INSERT);
    }
}
