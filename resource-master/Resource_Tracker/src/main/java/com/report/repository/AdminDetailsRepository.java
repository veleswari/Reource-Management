package com.report.repository;

import com.report.Util.AdminDetailsSpecification;
import com.report.entity.AdminDetails;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDetailsRepository extends JpaRepository<AdminDetails, Integer> , JpaSpecificationExecutor<AdminDetails> {
    List<AdminDetails> findBymanagerName(String managerName);

    List<AdminDetails> findAll(Specification<AdminDetails> spec);
}

