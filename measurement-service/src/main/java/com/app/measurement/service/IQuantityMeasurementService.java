package com.app.measurement.service;

import com.app.measurement.dto.QuantityInputDTO;
import com.app.measurement.dto.QuantityMeasurementDTO;

import java.util.List;

public interface IQuantityMeasurementService {
    QuantityMeasurementDTO compare(QuantityInputDTO inputDTO, String userEmail);
    QuantityMeasurementDTO convert(QuantityInputDTO inputDTO, String userEmail);
    QuantityMeasurementDTO add(QuantityInputDTO inputDTO, String userEmail);
    QuantityMeasurementDTO subtract(QuantityInputDTO inputDTO, String userEmail);
    QuantityMeasurementDTO divide(QuantityInputDTO inputDTO, String userEmail);

    List<QuantityMeasurementDTO> getHistoryByOperation(String operation);
    List<QuantityMeasurementDTO> getHistoryByMeasurementType(String measurementType);
    long getOperationCount(String operation);
    List<QuantityMeasurementDTO> getErroredHistory();
}