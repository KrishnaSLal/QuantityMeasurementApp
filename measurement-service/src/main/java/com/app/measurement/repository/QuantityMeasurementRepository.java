package com.app.measurement.repository;

import com.app.measurement.entity.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuantityMeasurementRepository
        extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findByOperationIgnoreCase(String operation);
    List<QuantityMeasurementEntity> findByThisMeasurementTypeIgnoreCase(String measurementType);
    long countByOperationIgnoreCaseAndErrorFalse(String operation);
    List<QuantityMeasurementEntity> findByErrorTrue();
}