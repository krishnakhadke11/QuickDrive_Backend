package com.TransportationService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "email",unique = true, length = 100, nullable = false)
    private String email;

//    @JsonIgnore
    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "phone_no",unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "role")
    private Role role;

//    @OneToMany(mappedBy = "user",
//            cascade = {CascadeType.DETACH,CascadeType.MERGE,
//                    CascadeType.PERSIST,CascadeType.REFRESH},
//            fetch = FetchType.EAGER)
//    List<Cab> ownedCabs;
//
//    public void addCab(Cab cab) {
//        if(ownedCabs == null){
//            ownedCabs = new ArrayList<>();
//        }
//        ownedCabs.add(cab);
//    }
}
