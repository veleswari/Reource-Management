package com.report.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenNeedsMasterdto {
    private Integer SPID;
    private String project;
    private String role;
    private String remarks;
}
