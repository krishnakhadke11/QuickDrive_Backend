package com.TransportationService.entity;

import jakarta.persistence.*;
import lombok.Data;

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
    private SeatingCapacity seatingCapacity;

    @Column(name = "color")
    private String color;

    @Column(name = "model", nullable = false)
    private String model;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
                            CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;
}
