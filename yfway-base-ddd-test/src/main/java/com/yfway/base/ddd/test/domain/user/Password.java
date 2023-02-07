package com.yfway.base.ddd.test.domain.user;

import com.yfway.base.ddd.jpa.model.Value;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hon_him
 * @since 2022-11-01
 */
@Embeddable
@NoArgsConstructor
@Setter
@Getter
public class Password implements Value<Password> {

    @Column(nullable = false)
    @NotNull
    private String password;

    @Column(nullable = false)
    @NotNull
    private String salt;

    public static Password of(String pass) {
        Password password1 = new Password();
        password1.setPassword(pass);
        password1.setSalt("1");
        return password1;
    }

}
