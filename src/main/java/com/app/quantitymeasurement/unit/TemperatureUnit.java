package com.app.quantitymeasurement.unit;

public enum TemperatureUnit implements IMeasurable {
	
    CELSIUS,
    FAHRENHEIT;

    @Override
    public double toBase(double value) {
        return this == CELSIUS ? value : (value - 32) * 5 / 9;
    }

    @Override
    public double fromBase(double value) {
        return this == CELSIUS ? value : (value * 9 / 5) + 32;
    }
}