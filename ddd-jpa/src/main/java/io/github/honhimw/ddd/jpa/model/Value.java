package io.github.honhimw.ddd.jpa.model;

import io.github.honhimw.ddd.jpa.util.ValidatorUtils;
import java.io.Serializable;

/**
 * @author hon_him
 * @since 2022-11-01
 */

public interface Value<T extends Value<T>> extends Serializable {

    default void validate() {
        ValidatorUtils.validate(this);
    }

    default boolean isEqualTo(T other) {
        return this.equals(other);
    }

}
