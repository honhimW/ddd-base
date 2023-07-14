package io.github.honhimw.ddd.common;

import java.io.Serializable;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

        private String group = "root";

        public static ConditionColumn of(String name, Object value) {
            return of(name, value, MatchingType.EQUAL);
        }
        public static ConditionColumn of(String name, Object value, MatchingType type) {
            ConditionColumn column = new ConditionColumn();
            column.setName(name);
            column.setValue(value);
            column.setType(type);
            return column;
        }

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

        public static OrderColumn of(String name, Boolean desc) {
            OrderColumn column = new OrderColumn();
            column.setName(name);
            column.setDesc(desc);
            return column;
        }

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
