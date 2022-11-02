package com.yfway.base.ddd.jpa.domain.ext;

import com.yfway.base.ddd.jpa.model.Value;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * @author hon_him
 * @since 2022-11-01
 */
@Embeddable
public class Auditor implements Value<Auditor> {

    @Column(
        name = "created_at",
        updatable = false
    )
    @Comment("创建时间")
    @NotNull
    @CreatedDate
    private Instant createdAt;

    @Column(
        name = "updated_at"
    )
    @Comment("更新时间")
    @NotNull
    @LastModifiedDate
    private Instant updatedAt;

    @Column(
        name = "created_by",
        updatable = false
    )
    @Comment("创建人")
    @CreatedBy
    private String createdBy;

    @Column(
        name = "updated_by"
    )
    @Comment("更新人")
    @LastModifiedBy
    private String updatedBy;

}
