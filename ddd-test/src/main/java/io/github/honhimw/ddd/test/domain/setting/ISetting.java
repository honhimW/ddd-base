package io.github.honhimw.ddd.test.domain.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hon_him
 * @since 2023-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ISetting implements Serializable {
    private String value;
    private String description;
}
