package com.app.quantitymeasurement.unit;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS,
    FAHRENHEIT,
    KELVIN;

    @Override
    public double toBase(double value) {
        return switch (this) {
            case CELSIUS -> value;
            case FAHRENHEIT -> (value - 32) * 5 / 9;
            case KELVIN -> value - 273.15;
        };
    }

    @Override
    public double fromBase(double value) {
        return switch (this) {
            case CELSIUS -> value;
            case FAHRENHEIT -> (value * 9 / 5) + 32;
            case KELVIN -> value + 273.15;
        };
    }
}