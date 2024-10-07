package com.report.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.sql.Time;


@Entity
@Table(name = "openneeds")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenNeeds {
                @Id
                @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_id_generator")
                @GenericGenerator(name = "custom_id_generator", strategy = "com.report.config.CustomIdGenerator")
                @Column(name = "customer_id")
                private String customId;

                @Column(name = "spid")
                private Integer SPID;

                @Column(name = "project")
                private String project;

                @Column(name = "role")
                private String role;

                @Column(name = "resourcename")
                private String resourceName;

                @Column(name = "status")
                private String status;

                @Column(name = "experience")
                private Float experience;

                @Column(name = "initial_status")
                private String initialStatus;

                @Column(name = "profile_shared_to_dh_date")
                private Date profileSharedToDHDate;

                @Column(name = "dh_interview_date")
                private Date DHInterviewDate;

                @Column(name = "no_of_days_ageing_for_dh_interview")
                private Integer noOfDaysAgeingForDHInterview;

                @Column(name = "dh_feedback_date")
                private Date DHFeedbackDate;

                @Column(name = "dh_lead_name_for_interview")
                private String DHLeadNameForInterview;

                @Column(name = "ageing_for_interview_feedback")
                private String ageingForInterviewFeedback;

                @Column(name = "latest_status")
                private String latestStatus;

                @Column(name = "role_closed_date")
                private Date roleClosedDate;

                @Column(name = "remarks")
                private String remarks;

                @Column(name = "hv_interview_rating")
                private Float HVInterviewRating;

                @Column(name = "interview_slots")
                private Time interviewSlots;

}





