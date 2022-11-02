package com.yfway.base.ddd.test.domain.user;

import com.yfway.base.ddd.jpa.model.Value;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author hon_him
 * @since 2022-11-01
 */
@Embeddable
public class Password implements Value<Password> {

    @Column(nullable = false)
    @NotNull
    @Pattern(regexp = "")
    private String password;

    @Column(nullable = false)
    @NotNull
    @Pattern(regexp = "")
    private String salt;

}
