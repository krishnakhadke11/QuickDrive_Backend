package com.TransportationService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
                            CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;
}
