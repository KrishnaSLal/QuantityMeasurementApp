package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityMeasurementController {

    private static final Logger log = LoggerFactory.getLogger(QuantityMeasurementController.class);

    private final IQuantityMeasurementService quantityMeasurementService;

    public QuantityMeasurementController(IQuantityMeasurementService quantityMeasurementService) {
        if (quantityMeasurementService == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }
        this.quantityMeasurementService = quantityMeasurementService;
        log.info("QuantityMeasurementController initialized");
    }

    public boolean performComparison(QuantityDTO quantity1, QuantityDTO quantity2) {
        return quantityMeasurementService.compare(quantity1, quantity2);
    }

    public QuantityDTO performConversion(QuantityDTO sourceQuantity, String targetUnit) {
        return quantityMeasurementService.convert(sourceQuantity, targetUnit);
    }

    public QuantityDTO performAddition(QuantityDTO quantity1, QuantityDTO quantity2) {
        return quantityMeasurementService.add(quantity1, quantity2);
    }

    public QuantityDTO performSubtraction(QuantityDTO quantity1, QuantityDTO quantity2) {
        return quantityMeasurementService.subtract(quantity1, quantity2);
    }

    public double performDivision(QuantityDTO quantity1, QuantityDTO quantity2) {
        return quantityMeasurementService.divide(quantity1, quantity2);
    }
}