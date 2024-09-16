package com.TransportationService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Entity
@Table(name = "ride")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "pickup_location", nullable = false)
    private String pickupLocation;

    @Column(name = "pickup_name", nullable = false)
    private String pickupName;

    @Column(name = "drop_location", nullable = false)
    private String dropLocation;

    @Column(name = "drop_name", nullable = false)
    private String dropName;

    @Column(name = "rating")
    private Integer rating = null;

    @Column(name = "fare")
    private int fare=0;

    @Column(name = "distance")
    private String distance;

    @Column(name = "duration")
    private String duration;

    @Column(name = "payment_type")
    private PaymentType paymentType;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private Date createdAt;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "cab_id",nullable = true)
    private Cab cab;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "driver_id")
    private Driver driver;
}
