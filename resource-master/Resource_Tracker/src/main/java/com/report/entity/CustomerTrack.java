package com.report.entity;

import com.report.config.CustomerTrackId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_track")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CustomerTrackId.class)
public class CustomerTrack {


    @Id
    @Column(name = "customer_name")
    private String customerName;

    @Id
    @Column(name = "stream")
    private String stream;
}

