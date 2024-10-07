package com.report.entity;

import com.report.config.CustomerStatusId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CustomerStatusId.class)
public class CustomerStatus {



    @Id
    @Column(name = "customer_name")
    private String customerName;

    @Id
    @Column(name = "status")
    private String status;
}
