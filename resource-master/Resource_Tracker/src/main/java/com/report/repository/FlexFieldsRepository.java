package com.report.repository;

import com.report.config.FlexFieldsId;
import com.report.entity.FlexFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlexFieldsRepository extends JpaRepository<FlexFields, String> , JpaSpecificationExecutor<FlexFields> {
    @Query("SELECT DISTINCT LOWER(f.flexValue) FROM FlexFields f WHERE LOWER(f.customerName) = LOWER(:customerName) and LOWER(f.skillSet) = LOWER(:skillSet)")
    List<String> findFlexValueByCustomerNameAndSkillSet(@Param("customerName")String customerName,@Param("skillSet")String skillSet);

    public FlexFields findByCustomId(String customId);

    @Query("SELECT DISTINCT f.skillSet FROM FlexFields f WHERE LOWER(f.customerName) = LOWER(:customerName)")
    public List<String> findSkillSetByCustomerName(@Param("customerName")String CustomerName);

        List<FlexFields> findBySkillSet(String skillSet);

        List<FlexFields> findBymanagerName(String managerName);
}

