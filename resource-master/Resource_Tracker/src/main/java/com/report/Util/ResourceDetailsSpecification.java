package com.report.Util;

import com.report.entity.ResourceDetails;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResourceDetailsSpecification {

    public static Specification<ResourceDetails> getResourceDetailsSpecification(
            String customId, Integer spid, String customerMgrName, Double billRate, String overallStatus, String internalExternal,
            String resourceName, Integer noOfYears, LocalDate profileSharedDate, LocalDate customerInterviewDate,
            LocalDate l1InterviewDate, LocalDate dateOfJoin, String customerName, String stream, String managerName, String skillSet
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (customId != null) {
                predicates.add(cb.like(root.get("customId"), "%" + customId + "%"));
            }
            if (spid != null) {
                predicates.add(cb.equal(root.get("spid"), spid));
            }
            if (customerMgrName != null) {
                predicates.add(cb.like(root.get("customerMgrName"), "%" + customerMgrName + "%"));
            }
            if (billRate != null) {
                predicates.add(cb.equal(root.get("billRate"), billRate));
            }
            if (overallStatus != null) {
                predicates.add(cb.equal(root.get("overallStatus"), overallStatus));
            }
            if (internalExternal != null) {
                predicates.add(cb.equal(root.get("internalExternal"), internalExternal));
            }
            if (resourceName != null) {
                predicates.add(cb.like(root.get("resourceName"), "%" + resourceName + "%"));
            }
            if (noOfYears != null) {
                predicates.add(cb.equal(root.get("noOfYears"), noOfYears));
            }
            if (profileSharedDate != null) {
                predicates.add(cb.equal(root.get("profileSharedDate"), profileSharedDate));
            }
            if (customerInterviewDate != null) {
                predicates.add(cb.equal(root.get("customerInterviewDate"), customerInterviewDate));
            }
            if (l1InterviewDate != null) {
                predicates.add(cb.equal(root.get("l1InterviewDate"), l1InterviewDate));
            }
            if (dateOfJoin != null) {
                predicates.add(cb.equal(root.get("dateOfJoin"), dateOfJoin));
            }
            if (customerName != null) {
                predicates.add(cb.like(root.get("customerName"), "%" + customerName + "%"));
            }
            if (stream != null) {
                predicates.add(cb.equal(root.get("stream"), stream));
            }
            if (managerName != null) {
                predicates.add(cb.like(root.get("managerName"), "%" + managerName + "%"));
            }
            if (skillSet != null) {
                predicates.add(cb.like(root.get("skillSet"), "%" + skillSet + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
