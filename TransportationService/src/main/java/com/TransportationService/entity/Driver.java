package com.TransportationService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "drivers_license", unique = true, nullable = false)
    private String driversLicense;

    @Column(name = "start_time", nullable = false)
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<DriverCabOwner> ownedCabs;

    public void addDriverCabOwner(DriverCabOwner driverCabOwner) {
        if (ownedCabs == null) {
            ownedCabs = new ArrayList<>();
        }
        ownedCabs.add(driverCabOwner);
    }
}
