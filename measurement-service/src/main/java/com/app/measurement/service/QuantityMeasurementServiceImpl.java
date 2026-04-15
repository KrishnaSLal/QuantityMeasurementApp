package com.app.measurement.service;

import com.app.measurement.dto.QuantityDTO;
import com.app.measurement.dto.QuantityInputDTO;
import com.app.measurement.dto.QuantityMeasurementDTO;
import com.app.measurement.entity.QuantityMeasurementEntity;
import com.app.measurement.exception.QuantityMeasurementException;
import com.app.measurement.repository.QuantityMeasurementRepository;
import com.app.measurement.unit.IMeasurable;
import com.app.measurement.util.QuantityFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final QuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    @Override
    public QuantityMeasurementDTO compare(QuantityInputDTO inputDTO, String userEmail) {
        QuantityDTO q1 = inputDTO.getThisQuantityDTO();
        QuantityDTO q2 = inputDTO.getThatQuantityDTO();

        validateSameMeasurementType(q1, q2);

        IMeasurable unit1 = QuantityFactory.getUnit(q1.getMeasurementType(), q1.getUnit());
        IMeasurable unit2 = QuantityFactory.getUnit(q2.getMeasurementType(), q2.getUnit());

        boolean result = Double.compare(unit1.toBase(q1.getValue()), unit2.toBase(q2.getValue())) == 0;

        QuantityMeasurementEntity entity = buildBaseEntity(q1, q2, "COMPARE", userEmail);
        entity.setResultString(String.valueOf(result));
        return saveAndReturn(entity);
    }

    @Override
    public QuantityMeasurementDTO convert(QuantityInputDTO inputDTO, String userEmail) {
        QuantityDTO q1 = inputDTO.getThisQuantityDTO();
        QuantityDTO q2 = inputDTO.getThatQuantityDTO();

        validateSameMeasurementType(q1, q2);

        IMeasurable fromUnit = QuantityFactory.getUnit(q1.getMeasurementType(), q1.getUnit());
        IMeasurable toUnit = QuantityFactory.getUnit(q2.getMeasurementType(), q2.getUnit());

        double converted = toUnit.fromBase(fromUnit.toBase(q1.getValue()));

        QuantityMeasurementEntity entity = buildBaseEntity(q1, q2, "CONVERT", userEmail);
        entity.setResultValue(converted);
        entity.setResultUnit(q2.getUnit());
        entity.setResultMeasurementType(q2.getMeasurementType());
        return saveAndReturn(entity);
    }

    @Override
    public QuantityMeasurementDTO add(QuantityInputDTO inputDTO, String userEmail) {
        return arithmeticOperation(inputDTO, "ADD", userEmail);
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityInputDTO inputDTO, String userEmail) {
        return arithmeticOperation(inputDTO, "SUBTRACT", userEmail);
    }

    @Override
    public QuantityMeasurementDTO divide(QuantityInputDTO inputDTO, String userEmail) {
        QuantityDTO q1 = inputDTO.getThisQuantityDTO();
        QuantityDTO q2 = inputDTO.getThatQuantityDTO();

        validateSameMeasurementType(q1, q2);

        if ("TemperatureUnit".equalsIgnoreCase(q1.getMeasurementType())) {
            throw new QuantityMeasurementException("DIVIDE not supported for TemperatureUnit");
        }

        IMeasurable unit1 = QuantityFactory.getUnit(q1.getMeasurementType(), q1.getUnit());
        IMeasurable unit2 = QuantityFactory.getUnit(q2.getMeasurementType(), q2.getUnit());

        double base1 = unit1.toBase(q1.getValue());
        double base2 = unit2.toBase(q2.getValue());

        if (base2 == 0.0) {
            throw new QuantityMeasurementException("Divide by zero");
        }

        QuantityMeasurementEntity entity = buildBaseEntity(q1, q2, "DIVIDE", userEmail);
        entity.setResultValue(base1 / base2);
        entity.setResultUnit("RATIO");
        entity.setResultMeasurementType("NUMBER");
        return saveAndReturn(entity);
    }

    private QuantityMeasurementDTO arithmeticOperation(QuantityInputDTO inputDTO,
                                                       String operation,
                                                       String userEmail) {
        QuantityDTO q1 = inputDTO.getThisQuantityDTO();
        QuantityDTO q2 = inputDTO.getThatQuantityDTO();

        validateSameMeasurementType(q1, q2);

        if ("TemperatureUnit".equalsIgnoreCase(q1.getMeasurementType())) {
            throw new QuantityMeasurementException(operation + " not supported for TemperatureUnit");
        }

        IMeasurable unit1 = QuantityFactory.getUnit(q1.getMeasurementType(), q1.getUnit());
        IMeasurable unit2 = QuantityFactory.getUnit(q2.getMeasurementType(), q2.getUnit());

        double base1 = unit1.toBase(q1.getValue());
        double base2 = unit2.toBase(q2.getValue());

        double resultBase = switch (operation) {
            case "ADD" -> base1 + base2;
            case "SUBTRACT" -> base1 - base2;
            default -> throw new QuantityMeasurementException("Unsupported arithmetic operation");
        };

        QuantityMeasurementEntity entity = buildBaseEntity(q1, q2, operation, userEmail);
        entity.setResultValue(unit1.fromBase(resultBase));
        entity.setResultUnit(q1.getUnit());
        entity.setResultMeasurementType(q1.getMeasurementType());
        return saveAndReturn(entity);
    }

    @Override
    public List<QuantityMeasurementDTO> getHistoryByOperation(String operation) {
        return repository.findByOperationIgnoreCase(operation)
                .stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .toList();
    }

    @Override
    public List<QuantityMeasurementDTO> getHistoryByMeasurementType(String measurementType) {
        return repository.findByThisMeasurementTypeIgnoreCase(measurementType)
                .stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .toList();
    }

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperationIgnoreCaseAndErrorFalse(operation);
    }

    @Override
    public List<QuantityMeasurementDTO> getErroredHistory() {
        return repository.findByErrorTrue()
                .stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .toList();
    }

    private void validateSameMeasurementType(QuantityDTO q1, QuantityDTO q2) {
        if (!q1.getMeasurementType().equalsIgnoreCase(q2.getMeasurementType())) {
            throw new QuantityMeasurementException("Measurement types must match");
        }
    }

    private QuantityMeasurementEntity buildBaseEntity(QuantityDTO q1,
                                                      QuantityDTO q2,
                                                      String operation,
                                                      String userEmail) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setThisValue(q1.getValue());
        entity.setThisUnit(q1.getUnit());
        entity.setThisMeasurementType(q1.getMeasurementType());
        entity.setThatValue(q2.getValue());
        entity.setThatUnit(q2.getUnit());
        entity.setThatMeasurementType(q2.getMeasurementType());
        entity.setOperation(operation);
        entity.setError(false);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUserEmail(userEmail);
        return entity;
    }

    private QuantityMeasurementDTO saveAndReturn(QuantityMeasurementEntity entity) {
        return QuantityMeasurementDTO.fromEntity(repository.save(entity));
    }
}