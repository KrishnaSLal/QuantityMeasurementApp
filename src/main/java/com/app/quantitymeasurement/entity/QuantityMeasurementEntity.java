package com.app.quantitymeasurement.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;

    public QuantityMeasurementEntity() {
        this.createdAt = LocalDateTime.now();
    }

    public QuantityMeasurementEntity(String operationType, String operand1, String operand2, String result) {
        this();
        this.operationType = operationType;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = result;
        this.error = false;
    }

    public QuantityMeasurementEntity(String operationType, String operand1, String result) {
        this();
        this.operationType = operationType;
        this.operand1 = operand1;
        this.result = result;
        this.error = false;
    }

    public QuantityMeasurementEntity(String operationType, String operand1, String operand2, String errorMessage, boolean error) {
        this();
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
        this();
        this.operationType = operationType;
        this.measurementType = measurementType;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = result;
        this.error = error;
        this.errorMessage = errorMessage;
    }

    public Long getId() {
        return id;
    }

    public String getOperationType() {
        return operationType;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public String getOperand1() {
        return operand1;
    }

    public String getOperand2() {
        return operand2;
    }

    public String getResult() {
        return result;
    }

    public boolean isError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public void setOperand1(String operand1) {
        this.operand1 = operand1;
    }

    public void setOperand2(String operand2) {
        this.operand2 = operand2;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "QuantityMeasurementEntity{" +
                "id=" + id +
                ", operationType='" + operationType + '\'' +
                ", measurementType='" + measurementType + '\'' +
                ", operand1='" + operand1 + '\'' +
                ", operand2='" + operand2 + '\'' +
                ", result='" + result + '\'' +
                ", error=" + error +
                ", errorMessage='" + errorMessage + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}