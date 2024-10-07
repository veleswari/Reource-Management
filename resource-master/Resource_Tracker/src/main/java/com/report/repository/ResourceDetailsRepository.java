package com.report.repository;

import com.report.entity.ResourceDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceDetailsRepository extends JpaRepository<ResourceDetails, String>, JpaSpecificationExecutor<ResourceDetails> {

    List<ResourceDetails> findByManagerName(String managerName);

    List<ResourceDetails> findByCustomerName(String customerName);

    List<ResourceDetails> findByCustomerNameAndStream(String customerName, String stream);

    @Query("SELECT r.stream FROM ResourceDetails r WHERE LOWER(r.stream) LIKE LOWER(CONCAT(:stream, '%'))")
    List<String> findByStreamStartingWith(String stream);

    List<ResourceDetails> findByCustomerNameAndManagerName(String customerName, String managerName);

    List<ResourceDetails> findByCustomerNameAndStreamAndManagerName(String customerName, String stream, String managerName);

    Page<ResourceDetails> findAll(Pageable pageable);
}