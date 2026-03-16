package com.app.quantitymeasurement.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String operationType;
    private String measurementType;
    private String operand1;
    private String operand2;
    private String result;
    private boolean error;
    private String errorMessage;
    private LocalDateTime createdAt = LocalDateTime.now();

    public QuantityMeasurementEntity(String operationType, String operand1, String operand2, String result) {
        this.operationType = operationType;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = result;
        this.error = false;
    }

    public QuantityMeasurementEntity(String operationType, String operand1, String result) {
        this.operationType = operationType;
        this.operand1 = operand1;
        this.result = result;
        this.error = false;
    }

    public QuantityMeasurementEntity(String operationType, String operand1, String operand2, String errorMessage, boolean error) {
        this.operationType = operationType;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.errorMessage = errorMessage;
        this.error = error;
    }

    public QuantityMeasurementEntity(
            String operationType,
            String measurementType,
            String operand1,
            String operand2,
            String result,
            boolean error,
            String errorMessage) {
        this.operationType = operationType;
        this.measurementType = measurementType;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = result;
        this.error = error;
        this.errorMessage = errorMessage;
    }
}