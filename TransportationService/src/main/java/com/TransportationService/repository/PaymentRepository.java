package com.TransportationService.repository;

import com.TransportationService.entity.Payment;
import com.TransportationService.entity.PaymentStatus;
import com.TransportationService.entity.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Payment p JOIN p.ride r JOIN r.driver d " +
            "WHERE  d.id = :driverId")
    boolean existsByDriverId(@Param("driverId") int driverId);

    List<Payment> findByRideCustomerId(int customerId);

    Payment findByRideId(int rideId);

    @Query("SELECT SUM(p.ride.fare) FROM Payment p WHERE p.paymentStatus = :status AND " +
            "FUNCTION('MONTH', p.createdAt) = :currentMonth AND FUNCTION('YEAR', p.createdAt) = :currentYear AND p.ride.driver.id = :driverId")
    Double findTotalEarningsByPaymentStatusAndCurrentMonthByDriverId(
            @Param("status") PaymentStatus status,
            @Param("currentMonth") int currentMonth,
            @Param("currentYear") int currentYear,
            @Param("driverId") int driverId
    );

    @Query("SELECT SUM(p.ride.fare) FROM Payment p WHERE p.paymentStatus = :status AND p.paymentType = :paymentType AND " +
            "FUNCTION('MONTH', p.createdAt) = :currentMonth AND FUNCTION('YEAR', p.createdAt) = :currentYear AND p.ride.driver.id = :driverId")
    Double findTotalEarningsByPaymentStatusAndPaymentTypeAndCurrentMonthAndDriverId(
            @Param("status") PaymentStatus status,
            @Param("paymentType") PaymentType paymentType,
            @Param("currentMonth") int currentMonth,
            @Param("currentYear") int currentYear,
            @Param("driverId") int driverId
    );
}
