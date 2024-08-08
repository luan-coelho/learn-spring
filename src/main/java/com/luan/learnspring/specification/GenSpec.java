package com.luan.learnspring.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public final class GenSpec {

    public static <T> Specification<T> equals(String fieldName, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (value == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(getPath(root, fieldName), value);
        };
    }

    public static <T> Specification<T> like(String fieldName, String value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (value == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(getPath(root, fieldName).as(String.class), value);
        };
    }

    public static <T> Specification<T> contains(String fieldName, String value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (value == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(getPath(root, fieldName).as(String.class), "%" + value + "%");
        };
    }

    public static <T> Specification<T> containsIgnoreCase(String fieldName, String value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (value == null) {
                return criteriaBuilder.conjunction();
            }
            Expression<String> lowerExpression = criteriaBuilder.lower(getPath(root, fieldName).as(String.class));
            return criteriaBuilder.like(lowerExpression, "%" + value.toLowerCase() + "%");
        };
    }

    private static <T> Path<?> getPath(Root<T> root, String fieldName) {
        String[] parts = fieldName.split("\\.");
        Path<?> path = root;
        for (String part : parts) {
            path = path.get(part);
        }
        return path;
    }

}

