package com.yfway.base.ddd.common;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author hon_him
 * @since 2022-07-26
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class IPageRequest<T> implements Serializable {

    @NotNull
    @Min(0)
    private Integer pageNo;

    private Integer pageSize;

    @NotNull
    @Min(1)
    @Max(65536)
    public Integer getPageSize() {
        return pageSize;
    }

    @Valid
    private T condition;

    @Valid
    private List<ConditionColumn> conditions;

    @Valid
    private List<OrderColumn> orders;

    private Boolean matchAll = true;

    @Data
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConditionColumn implements Serializable {

        @NotBlank
        private String name;

        private Object value;

        private MatchingType type = MatchingType.EQUAL;

    }

    @Data
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderColumn implements Serializable {

        public static final String RANDOM_ORDER = "randomOrderSpec";

        @NotBlank
        private String name;

        private Boolean desc = false;

    }

    public enum MatchingType {

        EQUAL,
        NOT_EQUAL,
        IN,

        NULL,
        NOT_NULL,

        /**
         * StringMatching
         */
        STARTING,
        ENDING,
        CONTAINING,

        /**
         * NumberMatching
         */

        GT,
        GE,
        LT,
        LE,

    }

}
