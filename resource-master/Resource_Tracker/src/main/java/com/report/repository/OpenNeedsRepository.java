package com.report.repository;

import com.report.entity.OpenNeeds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpenNeedsRepository extends JpaRepository<OpenNeeds,String> {
    public List<OpenNeeds> findBySPID(Integer sPID);
    public OpenNeeds findByCustomId(String customId);
}
