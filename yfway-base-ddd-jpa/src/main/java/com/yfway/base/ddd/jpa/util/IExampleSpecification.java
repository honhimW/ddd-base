package com.yfway.base.ddd.jpa.util;

import com.yfway.base.ddd.jpa.util.IPageRequest.ConditionColumn;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.util.Assert;

/**
 * @author hon_him
 * @since 2022-11-21
 */

public class IExampleSpecification<T> implements Specification<T> {

    private final IPageRequest<T> iPageRequest;

    private final EscapeCharacter escapeCharacter;

    public IExampleSpecification(IPageRequest<T> iPageRequest) {
        this(iPageRequest, EscapeCharacter.DEFAULT);
    }

    public IExampleSpecification(IPageRequest<T> iPageRequest, EscapeCharacter escapeCharacter) {
        this.iPageRequest = iPageRequest;
        this.escapeCharacter = escapeCharacter;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Assert.notNull(root, "Root must not be null!");
        Assert.notNull(cb, "CriteriaBuilder must not be null!");
        Assert.notNull(iPageRequest, "iPageRequest must not be null!");

        T condition = iPageRequest.getCondition();
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(condition)) {
            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, cb, Example.of(condition)));
        }
        List<ConditionColumn> conditions = iPageRequest.getConditions();
        if (CollectionUtils.isNotEmpty(conditions)) {
            predicates.add(getPredicate(conditions, cb, root, root.getModel()));
        }

        if (predicates.size() == 1) {
            return predicates.iterator().next();
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }

    private Predicate getPredicate(List<ConditionColumn> conditionColumns, CriteriaBuilder cb, Root<?> root,
        ManagedType<?> type) {
        Map<String, List<ConditionColumn>> groups = conditionColumns.stream()
            .collect(Collectors.groupingBy(ConditionColumn::getName));
        Boolean matchAll = iPageRequest.getMatchAll();
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate = null;
        for (SingularAttribute<?, ?> attribute : type.getSingularAttributes()) {
            String name = attribute.getName();
            if (groups.containsKey(name)) {
                List<ConditionColumn> group = groups.get(name);
                for (ConditionColumn cc : group) {
                    if (attribute.getJavaType().equals(String.class) && String.class.isAssignableFrom(
                        cc.getValue().getClass())) {
                        Expression<String> expression = root.get(name);
                        switch (cc.getType()) {
                            case CONTAINING -> predicate = cb.like(
                                expression, //
                                "%" + escapeCharacter.escape(cc.getValue().toString()) + "%", //
                                escapeCharacter.getEscapeCharacter() //
                            );
                            case STARTING -> predicate = cb.like(//
                                expression, //
                                escapeCharacter.escape(cc.getValue().toString()) + "%", //
                                escapeCharacter.getEscapeCharacter()); //
                            case ENDING -> predicate = cb.like( //
                                expression, //
                                "%" + escapeCharacter.escape(cc.getValue().toString()), //
                                escapeCharacter.getEscapeCharacter()); //
                        }
                    } else if (Number.class.isAssignableFrom(attribute.getJavaType()) && Number.class.isAssignableFrom(cc.getValue().getClass())) {
                        Expression<Number> expression = root.get(name);
                        switch (cc.getType()) {
                            case GT -> predicate = cb.gt(expression, (Number) cc.getValue());
                            case GE -> predicate = cb.ge(expression, (Number) cc.getValue());
                            case LT -> predicate = cb.lt(expression, (Number) cc.getValue());
                            case LE -> predicate = cb.le(expression, (Number) cc.getValue());
                        }
                    } else if (Boolean.class.isAssignableFrom(attribute.getJavaType()) && Boolean.class.isAssignableFrom(cc.getValue().getClass())) {
                        Expression<Boolean> expression = root.get(name);
                        Boolean value = (Boolean) cc.getValue();
                        switch (cc.getType()) {
                            case NOT_EQUAL -> predicate = value ? cb.isFalse(expression) : cb.isTrue(expression);
                            case EQUAL -> predicate = value ? cb.isTrue(expression) : cb.isFalse(expression);
                        }
                    }

                    if (Objects.isNull(predicate)) {
                        Expression<Object> expression = root.get(name);
                        switch (cc.getType()) {
                            case IN -> predicate = cb.in(expression).value(cc.getValue());
                            case NOT_EQUAL -> predicate = cb.notEqual(expression, cc.getValue());
                            case EQUAL -> predicate = cb.equal(expression, cc.getValue());
                            default -> predicate = cb.equal(expression, cc.getValue());
                        }
                    }
                    predicates.add(predicate);
                }
            }
        }
        if (CollectionUtils.isEmpty(predicates)) {
            predicates.add(cb.isTrue(cb.literal(true)));
        }
        return matchAll ? cb.and(predicates.toArray(Predicate[]::new)) : cb.or(predicates.toArray(Predicate[]::new));
    }

}
