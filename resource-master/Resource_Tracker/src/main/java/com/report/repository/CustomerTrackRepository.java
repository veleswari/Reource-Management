package com.report.repository;

import com.report.config.CustomerTrackId;
import com.report.entity.CustomerTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerTrackRepository extends JpaRepository<CustomerTrack, CustomerTrackId> {

    @Query("SELECT DISTINCT LOWER(c.track) FROM AdminDetails c WHERE LOWER(c.customerName) = LOWER(:customerName)")
    List<String> findStreamByCustomerName(@Param("customerName")String customerName);

    Optional<CustomerTrack> findByCustomerNameAndStream(String customerName,String stream);
}
