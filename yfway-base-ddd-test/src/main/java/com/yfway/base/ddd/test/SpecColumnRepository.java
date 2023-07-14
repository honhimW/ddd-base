package com.yfway.base.ddd.test;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * @author hon_him
 * @since 2023-05-13
 */

public class SpecColumnRepository<T, ID> extends SimpleJpaRepository<T, ID> {

    private final JpaEntityInformation<T, ?> ei;

    private final EntityManager em;

    public SpecColumnRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.ei = entityInformation;
        this.em = entityManager;
    }

    @Override
    protected <S extends T> TypedQuery<S> getQuery(Specification<S> spec, Class<S> domainClass, Sort sort) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<S> query = builder.createQuery(domainClass);

        CriteriaQuery<Tuple> tupleQuery = builder.createTupleQuery();

        Root<S> root = applySpecificationToCriteria(spec, domainClass, query);
//        Root<S> root = applyTupleSpecificationToCriteria(spec, domainClass, tupleQuery);
        if (query.getSelection() == null) {
            query.select(root);
        }

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }
        return applyRepositoryMethodMetadata(em.createQuery(query));
    }

    private <S, U extends T> Root<U> applyTupleSpecificationToCriteria(@Nullable Specification<U> spec, Class<U> domainClass,
                                                                  CriteriaQuery<Tuple> query) {

        Assert.notNull(domainClass, "Domain class must not be null!");
        Assert.notNull(query, "CriteriaQuery must not be null!");

        Root<U> root = query.from(domainClass);

        if (spec == null) {
            return root;
        }

        CriteriaBuilder builder = em.getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        return root;
    }

    private <S, U extends T> Root<U> applySpecificationToCriteria(@Nullable Specification<U> spec, Class<U> domainClass,
                                                                  CriteriaQuery<S> query) {

        Assert.notNull(domainClass, "Domain class must not be null!");
        Assert.notNull(query, "CriteriaQuery must not be null!");

        Root<U> root = query.from(domainClass);

        if (spec == null) {
            return root;
        }

        CriteriaBuilder builder = em.getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        return root;
    }

    private <S> TypedQuery<S> applyRepositoryMethodMetadata(TypedQuery<S> query) {
        CrudMethodMetadata metadata = getRepositoryMethodMetadata();
        if (metadata == null) {
            return query;
        }

        LockModeType type = metadata.getLockModeType();
        TypedQuery<S> toReturn = type == null ? query : query.setLockMode(type);

        getQueryHints().withFetchGraphs(em).forEach(toReturn::setHint);

        return toReturn;
    }

}
