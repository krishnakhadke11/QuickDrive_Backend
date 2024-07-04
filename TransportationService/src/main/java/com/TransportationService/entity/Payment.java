package com.TransportationService.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "payment_type",nullable = false)
    private String paymentType;

    @Column(name = "payment_status",nullable = false)
    private String paymentStatus;

    @OneToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "ride_id",referencedColumnName = "id")
    private Ride ride;
}
