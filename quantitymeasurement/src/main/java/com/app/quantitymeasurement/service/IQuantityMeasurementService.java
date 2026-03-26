package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;

import java.util.List;

public interface IQuantityMeasurementService {
    QuantityMeasurementDTO compare(QuantityInputDTO inputDTO);
    QuantityMeasurementDTO convert(QuantityInputDTO inputDTO);
    QuantityMeasurementDTO add(QuantityInputDTO inputDTO);
    QuantityMeasurementDTO subtract(QuantityInputDTO inputDTO);
    QuantityMeasurementDTO divide(QuantityInputDTO inputDTO);

    List<QuantityMeasurementDTO> getHistoryByOperation(String operation);
    List<QuantityMeasurementDTO> getHistoryByMeasurementType(String measurementType);
    long getOperationCount(String operation);
    List<QuantityMeasurementDTO> getErroredHistory();
}