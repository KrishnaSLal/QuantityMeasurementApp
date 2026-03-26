package com.app.quantitymeasurement.unit;

public enum LengthUnit implements IMeasurable {
	
    INCHES(1.0),
    FEET(12.0),
    YARD(36.0);

    private final double factor;

    LengthUnit(double factor) {
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