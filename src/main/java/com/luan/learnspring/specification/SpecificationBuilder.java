package com.luan.learnspring.specification;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {

    private final List<Specification<T>> specifications = new ArrayList<>();

    public SpecificationBuilder<T> with(Specification<T> specification) {
        if (specification != null) {
            specifications.add(specification);
        }
        return this;
    }

    public Specification<T> build() {
        return specifications.stream()
                .reduce(Specification::and)
                .orElse(null);
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
