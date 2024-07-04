package com.TransportationService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "driver_operation")
public class DriverOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Date startTime;

    private Date endTime;

    @OneToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "driver_id",referencedColumnName = "id")
    private Driver driver;

    @OneToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "cab_id",referencedColumnName = "id")
    private Cab cab;
}
