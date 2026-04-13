package com.app.quantitymeasurement.unit;

public enum VolumeUnit implements IMeasurable {

    MILLILITRE(1.0),
    LITRE(1000.0),
    GALLON(3785.41);

    private final double factor;

    VolumeUnit(double factor) {
        this.factor = factor;
    }

    @Override
    public double toBase(double value) {
        return value * factor;   // base = millilitre
    }

    @Override
    public double fromBase(double value) {
        return value / factor;
    }
}