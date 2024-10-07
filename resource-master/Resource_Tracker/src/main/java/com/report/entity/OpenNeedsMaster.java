package com.report.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "openneedsmaster")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenNeedsMaster {

    @Id
    @Column(name = "spid", nullable = false)
    private Integer SPID;

    @Column(name = "slno", nullable = false, insertable = false, updatable = false)
    private Integer slNo;

    @Column(name = "project")
    private String project;

    @Column(name = "role")
    private String role;

    @Column(name = "remarks")
    private String remarks;
}
