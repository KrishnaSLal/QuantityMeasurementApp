package com.app.quantitymeasurement.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    private static QuantityMeasurementCacheRepository instance;
    private final List<QuantityMeasurementEntity> measurementCache;

    private QuantityMeasurementCacheRepository() {
        this.measurementCache = new ArrayList<>();
    }

    public static synchronized QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementCacheRepository();
        }
        return instance;
    }

    @Override
    public synchronized void save(QuantityMeasurementEntity entity) {
        measurementCache.add(entity);
    }

    @Override
    public synchronized List<QuantityMeasurementEntity> findAll() {
        return new ArrayList<>(measurementCache);
    }

    @Override
    public synchronized List<QuantityMeasurementEntity> findByOperationType(String operationType) {
        return measurementCache.stream()
                .filter(e -> operationType != null && operationType.equalsIgnoreCase(e.getOperationType()))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized List<QuantityMeasurementEntity> findByMeasurementType(String measurementType) {
        return measurementCache.stream()
                .filter(e -> measurementType != null && measurementType.equalsIgnoreCase(e.getMeasurementType()))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized int getTotalCount() {
        return measurementCache.size();
    }

    @Override
    public synchronized void deleteAll() {
        measurementCache.clear();
    }
}