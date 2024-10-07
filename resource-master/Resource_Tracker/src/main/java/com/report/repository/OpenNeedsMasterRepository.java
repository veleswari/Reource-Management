package com.report.repository;

import com.report.entity.OpenNeedsMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenNeedsMasterRepository extends JpaRepository<OpenNeedsMaster, Integer> {
     OpenNeedsMaster findBySPID(Integer spid);

}
