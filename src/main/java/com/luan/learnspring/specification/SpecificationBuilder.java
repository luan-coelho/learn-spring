package com.luan.learnspring.specification;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecificationBuilder<T> {

    private final List<Specification<T>> specifications = new ArrayList<>();

    public SpecificationBuilder<T> with(Specification<T> specification) {
        if (specification != null) {
            specifications.add(specification);
        }
        return this;
    }

    public SpecificationBuilder<T> fromParams(Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String[] operationValue = value.split(",");
            String operation = operationValue[0];
            String valuex = operationValue[1];

            if (operation == null || valuex == null) {
                continue;
            }

            selectOperation(operation, key, valuex);
        }
        return this;
    }

    public Specification<T> build() {
        return specifications.stream().reduce(Specification::and).orElse(null);
    }

    private void selectOperation(String operation, String key, String valuex) {
        if (operation.equals(Operation.EQUALS.getValue())) {
            specifications.add(GenSpec.equals(key, valuex));
        }

        if (operation.equals(Operation.LIKE.getValue())) {
            specifications.add(GenSpec.like(key, valuex));
        }

        if (operation.equals(Operation.CONTAINS.getValue())) {
            specifications.add(GenSpec.contains(key, valuex));
        }

        if (operation.equals(Operation.CONTAINS_IGNORE_CASE.getValue())) {
            specifications.add(GenSpec.containsIgnoreCase(key, valuex));
        }
    }

    @Getter
    public enum Operation {

        EQUALS("ctn"), LIKE("lk"), CONTAINS("ctn"), CONTAINS_IGNORE_CASE("cic");

        private final String value;

        Operation(String value) {
            this.value = value;
        }

    }

}
