package com.report.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admin_details")
@Entity
public class AdminDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customer_name")
    private String customerName;

    private String track;

    private String status;

    @Column(name = "manager_name")
    private String managerName;
}
