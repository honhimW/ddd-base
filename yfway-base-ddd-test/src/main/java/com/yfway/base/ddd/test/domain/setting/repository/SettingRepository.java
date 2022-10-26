package com.yfway.base.ddd.test.domain.setting.repository;

import com.yfway.base.ddd.jpa.domain.repository.BaseRepository;
import com.yfway.base.ddd.test.domain.setting.Setting;
import com.yfway.base.ddd.test.domain.setting.SettingPK;
import com.yfway.base.ddd.test.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends BaseRepository<Setting, SettingPK> {

}