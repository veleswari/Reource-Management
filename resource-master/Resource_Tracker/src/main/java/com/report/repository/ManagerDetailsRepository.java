package com.report.repository;

import com.report.config.ManagerDetailsId;
import com.report.entity.ManagerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManagerDetailsRepository extends JpaRepository<ManagerDetails, ManagerDetailsId> {

    @Query("SELECT DISTINCT UPPER(m.customerName) FROM AdminDetails m WHERE UPPER(m.managerName) = UPPER(:managerName)")
    List<String> findCustomerNamesByManagerName(@Param("managerName") String managerName);

}
