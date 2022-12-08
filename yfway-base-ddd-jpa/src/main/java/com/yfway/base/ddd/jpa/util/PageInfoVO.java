package com.yfway.base.ddd.jpa.util;

import com.yfway.base.core.common.param.BaseParam;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author hon_him
 * @since 2022-07-27
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoVO<T> implements Serializable {

    private Integer pageNum;
    private Integer pageSize;
    private Integer size;
    private Long startRow;
    private Long endRow;
    private Integer pages;
    private Integer prePage;
    private Integer nextPage;
    private Boolean isFirstPage;
    private Boolean isLastPage;
    private Boolean hasPreviousPage;
    private Boolean hasNextPage;
    private Integer navigatePages;
    private List<Integer> navigatepageNums;
    private Integer navigateFirstPage;
    private Integer navigateLastPage;
    private List<T> list;
    private Long total;

}
