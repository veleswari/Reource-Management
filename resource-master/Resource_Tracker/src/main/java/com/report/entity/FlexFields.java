package com.report.entity;

import com.report.config.CustomerTrackId;
import com.report.config.FlexFieldsId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "flex_fields")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlexFields {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_id_sequence")
    @GenericGenerator(name = "custom_id_sequence", strategy = "com.report.config.CustomSequenceGenerator")
    @Column(name = "custom_id")
    private String customId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "skill_set")
    private String skillSet;

    @Column(name = "flex_value")
    private String flexValue;

    @Column(name = "manager_name")
    private String managerName;
}
