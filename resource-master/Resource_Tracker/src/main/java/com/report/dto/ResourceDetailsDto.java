package com.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDetailsDto {

    private String customId;
    private Integer spid;
    private LocalDate requestedDate;
    private String customerMgrName;
    private Double billRate;
    private String overallStatus;
    private String internalExternal;
    private String resourceName;
    private Integer noOfYears;
    private LocalDate profileSharedDate;
    private LocalDate customerInterviewDate;
    private LocalDate l1InterviewDate;
    private LocalDate dateOfJoin;
    private Integer flexField1Rating;
    private Integer flexField2Rating;
    private Integer flexField3Rating;
    private Integer flexField4Rating;
    private String customerName;
    private String stream;
    private String opptyName;
    private String managerName;
    private String skillSet;
    private List<FlexFieldsDto> flexFields;
}
