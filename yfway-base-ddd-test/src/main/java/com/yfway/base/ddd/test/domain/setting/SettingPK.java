package com.yfway.base.ddd.test.domain.setting;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author hon_him
 * @since 2022-10-25
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors
public class SettingPK implements Serializable {

    private String userId;

    private Long uuid;

}
