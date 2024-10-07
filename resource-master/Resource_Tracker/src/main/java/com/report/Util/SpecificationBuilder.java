package com.report.Util;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecificationBuilder {

    public static <T> Specification<T> getSpecifications(Map<String, Object> filterCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            filterCriteria.forEach((key, value) -> {
                if (value != null) {
                    predicates.add(criteriaBuilder.equal(root.get(key), value));
                }
                // Add more conditions for other fields as needed
            });

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
