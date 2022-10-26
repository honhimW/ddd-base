package com.yfway.base.ddd.test.domain.setting.service;

import com.yfway.base.ddd.test.domain.setting.Setting;
import com.yfway.base.ddd.test.domain.setting.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hon_him
 * @since 2022-10-26
 */
@Service
public class SettingServiceImpl {

    @Autowired
    private SettingRepository repository;

    @Transactional
    public void delete(Setting setting) {
        repository.delete(setting);
    }

    @Transactional
    public void delete(String userId) {
    }

}
