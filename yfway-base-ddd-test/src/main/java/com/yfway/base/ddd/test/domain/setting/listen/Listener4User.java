package com.yfway.base.ddd.test.domain.setting.listen;

import com.yfway.base.ddd.test.domain.setting.Setting;
import com.yfway.base.ddd.test.domain.setting.SettingPK;
import com.yfway.base.ddd.test.domain.setting.repository.SettingRepository;
import com.yfway.base.ddd.test.domain.user.event.UserEvent;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author hon_him
 * @since 2022-10-25
 */
@Slf4j
@Component
public class Listener4User {

    @Autowired
    private SettingRepository settingRepository;

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void userEvent(UserEvent event) {
        log.info("UserEvent: action: {}, entity: {}", event.getAction(), event.getEntity());
        switch (event.getAction()) {
            case INSERT -> {
                Setting entity = new Setting()
                    .setId(new SettingPK(event.getEntity().getId(), System.currentTimeMillis()))
                    .setDescription(event.getEntity().getPhoneNumber())
                    .setValue(UUID.randomUUID().toString());
                Setting save = settingRepository.saveAndFlush(
                    entity
                );
                log.info(save.getVersion() + "");
            }
            case DELETE -> settingRepository.deleteAll(settingRepository.findAll(Example.of(new Setting().setId(new SettingPK(event.getEntity().getId(), null)))));
            case LOGIC_DELETE -> settingRepository.logicDeleteAll(settingRepository
                .findAll(Example.of(new Setting().setId(new SettingPK(event.getEntity().getId(), null))))
                .stream()
                .map(Setting::getId)
                .toList());
        }
    }

}
