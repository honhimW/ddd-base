package com.yfway.base.ddd.jpa.util;

import com.yfway.base.core.common.param.BaseParam;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
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
@Generated("分页")
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class IPageRequest<T> implements Serializable {

    @Generated("页数")
    @NotNull
    @Min(0)
    private Integer pageNo;

    @Generated("页尺寸")
    private Integer pageSize;

    @NotNull
    @Min(1)
    @Max(65536)
    public Integer getPageSize() {
        return pageSize;
    }

    @Generated("实体类, 非空值作为where条件参数查询")
    @Valid
    private T condition;

    @Generated("where条件参数，value为空时等于is null，该参数非空时忽略condition")
    @Valid
    private List<ConditionColumn> conditions;

    @Generated("排序列参数")
    @Valid
    private List<OrderColumn> orders;

    @Generated("匹配全部参数")
    private Boolean matchAll = true;

    @Data
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConditionColumn implements Serializable {

        @Generated("列名")
        @NotBlank
        private String name;

        @Generated("值")
        private Object value;

        @Generated("匹配类型")
        private MatchingType type = MatchingType.EQUAL;

    }

    @Data
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderColumn implements Serializable {

        public static final String RANDOM_ORDER = "randomOrderSpec";

        @Generated("列名")
        @NotBlank
        private String name;

        @Generated("倒序")
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
