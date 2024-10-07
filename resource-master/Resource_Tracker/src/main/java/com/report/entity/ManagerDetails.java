package com.report.entity;

import com.report.config.ManagerDetailsId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(ManagerDetailsId.class)
@Table(name = "manager_details")
@Getter
@Setter
@NoArgsConstructor
public class ManagerDetails {

    @Id
    @Column(name = "manager_name")
    private String managerName;

    @Id
    @Column(name = "customer_name")
    private String customerName;


}
