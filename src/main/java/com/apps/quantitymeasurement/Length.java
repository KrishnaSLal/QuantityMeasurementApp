package com.apps.quantitymeasurement;

import java.util.Objects;

public class Length {

    private final double value;
    private final LengthUnit unit;

    // Enum storing conversion factor to base unit (Inches)
    public enum LengthUnit {
        FEET(12.0),
        INCHES(1.0),
        YARDS(36.0),
        CENTIMETERS(1.0 / 2.54);   // More accurate conversion

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return this.conversionFactor;
        }
    }

    // Constructor
    public Length(double value, LengthUnit unit) {
        if (unit == null)
            throw new IllegalArgumentException("Enter a valid length unit");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Enter a valid double value");

        this.value = value;
        this.unit = unit;
    }

    // Convert to base unit (inches)
    private double convertToBaseUnit() {
        return unit.getConversionFactor() * value;
    }

    // Compare two Length objects using tolerance
    public boolean compare(Length thatLength) {
        if (thatLength == null)
            return false;

        double currentBaseLength = this.convertToBaseUnit();
        double thatBaseLength = thatLength.convertToBaseUnit();

        double EPSILON = 0.0001;

        return Math.abs(currentBaseLength - thatBaseLength) < EPSILON;
    }

    // Convert to another unit
    public Length convertTo(LengthUnit targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Enter a valid unit for conversion");

        double baseValue = this.convertToBaseUnit();
        double convertedValue = baseValue / targetUnit.getConversionFactor();

        return new Length(convertedValue, targetUnit);
    }

    // Add two Length objects (result in current unit)
    public Length add(Length thatLength) {
        if (thatLength == null)
            throw new IllegalArgumentException("Enter a valid length for addition");

        double totalBaseValue = this.convertToBaseUnit() + thatLength.convertToBaseUnit();
        double resultValue = totalBaseValue / this.unit.getConversionFactor();

        return new Length(resultValue, this.unit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Length)) return false;

        Length that = (Length) o;
        return compare(that);
    }

    @Override
    public int hashCode() {
        double baseValue = convertToBaseUnit();
        return Double.valueOf(baseValue).hashCode();
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }

    // Getters (optional, if needed later)
    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }
}