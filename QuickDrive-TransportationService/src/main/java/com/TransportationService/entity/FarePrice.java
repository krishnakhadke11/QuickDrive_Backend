package com.TransportationService.entity;

public enum FarePrice {
    FIVE_SEATER(10),
    SEVEN_SEATER(12);

    private final double ratePerKm;

    FarePrice(double ratePerKm) {
        this.ratePerKm = ratePerKm;
    }

    public double getRatePerKm() {
        return ratePerKm;
    }
}
