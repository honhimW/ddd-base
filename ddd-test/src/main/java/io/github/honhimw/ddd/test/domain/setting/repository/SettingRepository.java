package io.github.honhimw.ddd.test.domain.setting.repository;

import io.github.honhimw.ddd.jpa.domain.repository.BaseRepository;
import io.github.honhimw.ddd.test.domain.setting.Setting;
import io.github.honhimw.ddd.test.domain.setting.SettingPK;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends BaseRepository<Setting, SettingPK> {

}