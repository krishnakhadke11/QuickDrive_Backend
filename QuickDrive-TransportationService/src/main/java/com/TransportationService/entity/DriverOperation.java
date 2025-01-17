package com.TransportationService.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
@Entity
@Table(name = "driver_operation")
public class DriverOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    @Column(name = "cab_status")
    private CabStatus cabStatus = CabStatus.AVAILABLE;

    @OneToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "driver_id",nullable = false,unique = true)
    private Driver driver;

    @OneToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "cab_id",nullable = false,unique = true)
    private Cab cab;
}
