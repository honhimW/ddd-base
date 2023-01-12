package com.yfway.base.ddd.jpa.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yfway.base.ddd.common.IPageRequest;
import com.yfway.base.ddd.common.IPageRequest.ConditionColumn;
import com.yfway.base.ddd.common.IPageRequest.MatchingType;
import com.yfway.base.utils.YfJsonUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.PropertyValueTransformer;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.data.support.ExampleMatcherAccessor;
import org.springframework.data.util.DirectFieldAccessFallbackBeanWrapper;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

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
        Boolean matchAll = iPageRequest.getMatchAll();
        Map<String, List<ConditionColumn>> groups = conditionColumns.stream()
            .collect(Collectors.groupingBy(ConditionColumn::getName));
        List<Predicate> predicates = getPredicates("", groups, cb, root, type, new PathNode("root", null, ""));
        return matchAll ? cb.and(predicates.toArray(Predicate[]::new)) : cb.or(predicates.toArray(Predicate[]::new));
    }

    private List<Predicate> getPredicates(String path, Map<String, List<ConditionColumn>> groups, CriteriaBuilder cb, Path<?> root,
        ManagedType<?> type, PathNode currentNode) {
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate = null;
        for (SingularAttribute<?, ?> attribute : type.getSingularAttributes()) {
            String name = attribute.getName();
            String currentPath = StringUtils.isBlank(path) ? name : path + "." + name;

            if (attribute.getPersistentAttributeType().equals(PersistentAttributeType.EMBEDDED)
                || (isAssociation(attribute) && !(root instanceof From))) {
                predicates
                    .addAll(getPredicates(currentPath, groups, cb, root.get(name), (ManagedType<?>) attribute.getType(),
                        currentNode));
                continue;
            }
            if (isAssociation(attribute)) {
                PathNode node = currentNode.add(name, "attributeValue");
                if (node.spansCycle()) {
                    throw new InvalidDataAccessApiUsageException(
                        String.format("Path '%s' must not span a cyclic property reference!%n%s", currentPath, node));
                }
                predicates.addAll(getPredicates(currentPath, groups, cb, ((From<?, ?>) root).join(name),
                    (ManagedType<?>) attribute.getType(), node));
                continue;
            }

            if (groups.containsKey(currentPath)) {
                List<ConditionColumn> group = groups.get(currentPath);
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
                        Object conditionValue = cc.getValue();
                        if (conditionValue instanceof Map) {
                            continue;
                        }
                        switch (cc.getType()) {
                            case IN -> {
                                if (conditionValue instanceof Collection<?> collection) {
                                    In<Object> in = cb.in(expression);
                                    for (Object o : collection) {
                                        in = in.value(o);
                                    }
                                    predicate = in;
                                } else if (conditionValue.getClass().isArray()) {
                                    Object[] value = (Object[]) conditionValue;
                                    In<Object> in = cb.in(expression);
                                    for (Object o : value) {
                                        in = in.value(o);
                                    }
                                    predicate = in;
                                } else {
                                    predicate = cb.in(expression).value(conditionValue);
                                }
                            }
                            case NOT_EQUAL -> predicate = cb.notEqual(expression, conditionValue);
                            case EQUAL -> predicate = cb.equal(expression, conditionValue);
                            default -> predicate = cb.equal(expression, conditionValue);
                        }
                    }
                    predicates.add(predicate);
                }
            }
        }
        if (CollectionUtils.isEmpty(predicates)) {
            predicates.add(cb.isTrue(cb.literal(true)));
        }
        return predicates;

    }

    private static final Set<PersistentAttributeType> ASSOCIATION_TYPES;

    static {
        ASSOCIATION_TYPES = EnumSet.of(PersistentAttributeType.MANY_TO_MANY, //
            PersistentAttributeType.MANY_TO_ONE, //
            PersistentAttributeType.ONE_TO_MANY, //
            PersistentAttributeType.ONE_TO_ONE);
    }

    private static boolean isAssociation(Attribute<?, ?> attribute) {
        return ASSOCIATION_TYPES.contains(attribute.getPersistentAttributeType());
    }

    private static class PathNode {

        String name;
        @Nullable
        PathNode parent;
        List<PathNode> siblings = new ArrayList<>();
        @Nullable Object value;

        PathNode(String edge, @Nullable PathNode parent, @Nullable Object value) {

            this.name = edge;
            this.parent = parent;
            this.value = value;
        }

        PathNode add(String attribute, @Nullable Object value) {

            PathNode node = new PathNode(attribute, this, value);
            siblings.add(node);
            return node;
        }

        boolean spansCycle() {

            if (value == null) {
                return false;
            }

            String identityHex = ObjectUtils.getIdentityHexString(value);
            PathNode current = parent;

            while (current != null) {

                if (current.value != null && ObjectUtils.getIdentityHexString(current.value).equals(identityHex)) {
                    return true;
                }
                current = current.parent;
            }

            return false;
        }

        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder();
            if (parent != null) {
                sb.append(parent);
                sb.append(" -");
                sb.append(name);
                sb.append("-> ");
            }

            sb.append("[{ ");
            sb.append(ObjectUtils.nullSafeToString(value));
            sb.append(" }]");
            return sb.toString();
        }
    }

}
