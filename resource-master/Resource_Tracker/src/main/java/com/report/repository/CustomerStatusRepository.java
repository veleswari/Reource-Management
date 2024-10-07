package com.report.repository;

import com.report.config.CustomerStatusId;
import com.report.entity.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerStatusRepository extends JpaRepository<CustomerStatus, CustomerStatusId> {
}