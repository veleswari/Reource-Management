package com.report.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlexFieldsDto {
    private String customId;
    private String customerName;
    private String skillSet;
    private String flexValue;
    private String managerName;
}

