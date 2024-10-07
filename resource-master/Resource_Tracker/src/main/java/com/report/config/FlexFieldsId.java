package com.report.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlexFieldsId implements Serializable {
    private String customerName;
    private String skillSet;
    private String flexValue;
}
