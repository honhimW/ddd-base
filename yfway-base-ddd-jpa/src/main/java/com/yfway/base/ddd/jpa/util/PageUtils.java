package com.yfway.base.ddd.jpa.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yfway.base.ddd.common.IPageRequest;
import com.yfway.base.ddd.common.IPageRequest.OrderColumn;
import com.yfway.base.ddd.common.PageInfoVO;
import com.yfway.base.utils.YfJsonUtils;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author hon_him
 * @since 2022-11-18
 */
@SuppressWarnings("unused")
public class PageUtils {

    private static int FIRST_PAGE_NO = 0;

    public static void setFirstPageNo(int firstPageNo) {
        FIRST_PAGE_NO = firstPageNo;
    }

    public static <T> Specification<T> spec(IPageRequest<T> iPageRequest) {
        return new IExampleSpecification<>(iPageRequest);
    }

    public static <T> PageRequest page(IPageRequest<T> iPageRequest) {
        PageRequest pr = PageRequest.of(springPageNo(iPageRequest.getPageNo()), iPageRequest.getPageSize());
        List<OrderColumn> orders = iPageRequest.getOrders();
        if (CollectionUtils.isNotEmpty(orders)) {
            List<Order> os = orders.stream().map(orderColumn -> {
                String name = orderColumn.getName();
                Boolean desc = orderColumn.getDesc();
                if (BooleanUtils.isTrue(desc)) {
                    return Order.desc(name);
                } else {
                    return Order.asc(name);
                }
            }).toList();
            pr.withSort(Sort.by(os));
        }
        return pr;
    }

    public static <T> Page<T> paging(JpaSpecificationExecutor<T> repository, IPageRequest<T> iPageRequest) {
        return repository.findAll(spec(iPageRequest), page(iPageRequest));
    }


    public static <T, R> IPageRequest<R> convertRequest(IPageRequest<T> iPageRequest, Function<T, R> converter) {
        IPageRequest<R> another = new IPageRequest<>();
        another.setPageNo(iPageRequest.getPageNo());
        another.setPageSize(iPageRequest.getPageSize());
        another.setConditions(iPageRequest.getConditions());
        another.setOrders(iPageRequest.getOrders());
        another.setMatchAll(iPageRequest.getMatchAll());
        another.setCondition(converter.apply(iPageRequest.getCondition()));
        return another;
    }

    public static <T, R> Page<R> convertPage(Page<T> page, Function<T, R> mapper) {
        List<R> rs = page.stream().map(mapper).toList();
        return new PageImpl<>(rs, page.getPageable(), page.getTotalElements());
    }

    public static <T, R> PageInfoVO<R> pageInfoDTO(Page<T> page, Function<T, R> mapper) {
        List<R> rs = page.stream().map(mapper).toList();
        PageInfoVO<R> pageInfoDTO = new PageInfoVO<>();
        pageInfoDTO.setPages(page.getTotalPages());
        pageInfoDTO.setTotal(page.getTotalElements());
        pageInfoDTO.setList(rs);
        pageInfoDTO.setPageNum(originPageNo(page.getNumber()));
        pageInfoDTO.setPageSize(page.getSize());
        pageInfoDTO.setPages(page.getTotalPages());
        pageInfoDTO.setSize(page.getNumberOfElements());
        return pageInfoDTO;
    }


    private static int springPageNo(int pageNo) {
        return Math.max(pageNo - FIRST_PAGE_NO, 0);
    }

    private static int originPageNo(int pageNo) {
        return pageNo + FIRST_PAGE_NO;
    }


    public static void main(String[] args) throws Exception {
        ObjectMapper mvcObjectMapper = YfJsonUtils.getMvcObjectMapper();
        ObjectNode objectNode = mvcObjectMapper.createObjectNode();
        CC cc = new CC();
        cc.b = true;
        cc.i = 1;
        cc.str = "true";
        cc.localDateTime = LocalDateTime.now();
        JsonNode jsonNode = mvcObjectMapper.valueToTree(cc);
        System.out.println(mvcObjectMapper.writeValueAsString(jsonNode));
        Object o1 = mvcObjectMapper.readerFor(Integer.class)
            .readValue(String.format("\"%s\"", "1231"));
        System.out.println(o1.toString());
        Object o = LocalDateTime.now().plusDays(1);
        System.out.println(mvcObjectMapper.writerFor(LocalDateTime.class).writeValueAsString(o));

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CC implements Serializable {
        Integer i;

        Boolean b;

        String str;

        LocalDateTime localDateTime;

    }

    public enum EE {
        HELLO,WORLD;
    }

}
