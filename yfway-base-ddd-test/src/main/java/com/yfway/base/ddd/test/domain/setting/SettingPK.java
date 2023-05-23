package com.yfway.base.ddd.test.domain.setting;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author hon_him
 * @since 2022-10-25
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SettingPK implements Serializable {

    private String userId;

    private Long uuid;

}
