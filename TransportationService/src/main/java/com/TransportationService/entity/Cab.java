package com.TransportationService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "cab")
public class Cab {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "register_no" ,unique = true, nullable = false)
    private String registerNo;

    @Column(name = "seating_capacity", nullable = false)
    private int seatingCapacity;

    @Column(name = "color")
    private String color;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "status", nullable = false)
    private boolean status; // True: Available, False: Not Available

    @OneToOne(mappedBy = "cab",cascade = CascadeType.ALL)
    private DriverCabOwner driverOwner;

    @OneToOne(mappedBy = "cab",cascade = CascadeType.ALL)
    private AdminCabOwner adminOwner;
}
