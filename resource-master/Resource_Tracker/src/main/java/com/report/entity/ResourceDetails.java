package com.report.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Table(name = "resource_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDetails {

    @Id
    @Column(name = "customid")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_id_generator")
    @GenericGenerator(name = "custom_id_generator", strategy = "com.report.config.CustomIdGenerator")
    private String customId;

    @Column(name = "spid", nullable = false)
    private Integer spid;

    @Column(name = "requested_date")
    private LocalDate requestedDate;

    @Column(name = "customer_mgr_name", nullable = false)
    private String customerMgrName;

    @Column(name = "bill_rate")
    private Double billRate;

    @Column(name = "overall_status")
    private String overallStatus;

    @Column(name = "internal_external")
    private String internalExternal;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "no_of_years")
    private Integer noOfYears;

    @Column(name = "profile_shared_date")
    private LocalDate profileSharedDate;

    @Column(name = "customer_interview_date")
    private LocalDate customerInterviewDate;

    @Column(name = "l1_interview_date")
    private LocalDate l1InterviewDate;

    @Column(name = "date_of_join")
    private LocalDate dateOfJoin;

    @Column(name = "flex_field_1_rating")
    private Integer flexField1Rating;

    @Column(name = "flex_field_2_rating")
    private Integer flexField2Rating;

    @Column(name = "flex_field_3_rating")
    private Integer flexField3Rating;

    @Column(name = "flex_field_4_rating")
    private Integer flexField4Rating;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "stream")
    private String stream;

    @Column(name = "oppty_name")
    private String opptyName;

    @Column(name = "manager_name")
    private String managerName;

    @Column(name = "skill_set")
    private String skillSet;

}
