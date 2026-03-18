package com.app.quantitymeasurement.util;

import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.unit.*;

public class QuantityFactory {

    private QuantityFactory() {
    }

    public static IMeasurable getUnit(String measurementType, String unit) {
        try {
            return switch (measurementType.toUpperCase()) {
                case "LENGTHUNIT" -> LengthUnit.valueOf(unit.toUpperCase());
                case "WEIGHTUNIT" -> WeightUnit.valueOf(unit.toUpperCase());
                case "VOLUMEUNIT" -> VolumeUnit.valueOf(unit.toUpperCase());
                case "TEMPERATUREUNIT" -> TemperatureUnit.valueOf(unit.toUpperCase());
                default -> throw new QuantityMeasurementException("Invalid measurement type: " + measurementType);
            };
        } catch (IllegalArgumentException ex) {
            throw new QuantityMeasurementException("Invalid unit: " + unit + " for " + measurementType);
        }
    }
}