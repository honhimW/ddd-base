package com.yfway.base.ddd.jpa.domain.ext;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hon_him
 * @since 2022-10-27
 */
@Getter
@Setter
@Embeddable
public class Auditor implements Serializable {

    @Column(
        name = "created_by"
    )
    private String createdBy;

    @Column(
        name = "updated_by"
    )
    private String updatedBy;

}
