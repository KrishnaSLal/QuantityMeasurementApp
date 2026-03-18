package com.app.quantitymeasurement.unit;

public enum WeightUnit implements IMeasurable {
	
    GRAM(1.0),
    KILOGRAM(1000.0);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    @Override
    public double toBase(double value) {
        return value * factor;
    }

    @Override
    public double fromBase(double value) {
        return value / factor;
        
    }
}