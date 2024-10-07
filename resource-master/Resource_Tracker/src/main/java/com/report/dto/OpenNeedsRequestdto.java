package com.report.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenNeedsRequestdto {

    private String customId;
    private Integer SPID;
    private String project;
    private String resourceName;
    private String role;
    private String status;
    private Float experience;
    private String initialStatus;
    private Date profileSharedToDHDate;
    private Date DHInterviewDate;
    private Long noOfDaysAgeingForDHInterview;
    private Date DHFeedbackDate;
    private String DHLeadNameForInterview;
    private String ageingForInterviewFeedback;
    private String latestStatus;
    private Date roleClosedDate;
    private String remarks;
    private Float HVInterviewRating;
    private Time interviewSlots;

}
