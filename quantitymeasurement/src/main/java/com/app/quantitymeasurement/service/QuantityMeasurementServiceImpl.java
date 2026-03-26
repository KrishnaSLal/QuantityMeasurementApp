package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.util.QuantityFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final QuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    @Override
    public QuantityMeasurementDTO compare(QuantityInputDTO inputDTO) {
        QuantityDTO q1 = inputDTO.getThisQuantityDTO();
        QuantityDTO q2 = inputDTO.getThatQuantityDTO();

        validateSameMeasurementType(q1, q2);

        IMeasurable unit1 = QuantityFactory.getUnit(q1.getMeasurementType(), q1.getUnit());
        IMeasurable unit2 = QuantityFactory.getUnit(q2.getMeasurementType(), q2.getUnit());

        double base1 = unit1.toBase(q1.getValue());
        double base2 = unit2.toBase(q2.getValue());

        boolean result = Double.compare(base1, base2) == 0;

        QuantityMeasurementEntity entity = buildBaseEntity(q1, q2, "COMPARE");
        entity.setResultString(String.valueOf(result));

        return saveAndReturn(entity);
    }

    @Override
    public QuantityMeasurementDTO convert(QuantityInputDTO inputDTO) {
        QuantityDTO q1 = inputDTO.getThisQuantityDTO();
        QuantityDTO q2 = inputDTO.getThatQuantityDTO();

        validateSameMeasurementType(q1, q2);

        IMeasurable fromUnit = QuantityFactory.getUnit(q1.getMeasurementType(), q1.getUnit());
        IMeasurable toUnit = QuantityFactory.getUnit(q2.getMeasurementType(), q2.getUnit());

        double baseValue = fromUnit.toBase(q1.getValue());
        double converted = toUnit.fromBase(baseValue);

        QuantityMeasurementEntity entity = buildBaseEntity(q1, q2, "CONVERT");
        entity.setResultValue(converted);
        entity.setResultUnit(q2.getUnit());
        entity.setResultMeasurementType(q2.getMeasurementType());

        return saveAndReturn(entity);
    }

    @Override
    public QuantityMeasurementDTO add(QuantityInputDTO inputDTO) {
        return arithmeticOperation(inputDTO, "ADD");
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityInputDTO inputDTO) {
        return arithmeticOperation(inputDTO, "SUBTRACT");
    }

    @Override
    public QuantityMeasurementDTO divide(QuantityInputDTO inputDTO) {
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

        double result = base1 / base2;

        QuantityMeasurementEntity entity = buildBaseEntity(q1, q2, "DIVIDE");
        entity.setResultValue(result);
        entity.setResultUnit("RATIO");
        entity.setResultMeasurementType("NUMBER");
        entity.setError(false);

        return saveAndReturn(entity);
    }

    private QuantityMeasurementDTO arithmeticOperation(QuantityInputDTO inputDTO, String operation) {
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

        double result = unit1.fromBase(resultBase);

        QuantityMeasurementEntity entity = buildBaseEntity(q1, q2, operation);
        entity.setResultValue(result);
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
            throw new QuantityMeasurementException(
                    "Cannot perform operation between different measurement categories: "
                            + q1.getMeasurementType() + " and " + q2.getMeasurementType()
            );
        }
    }

    private QuantityMeasurementEntity buildBaseEntity(QuantityDTO q1, QuantityDTO q2, String operation) {
        return QuantityMeasurementEntity.builder()
                .thisValue(q1.getValue())
                .thisUnit(q1.getUnit())
                .thisMeasurementType(q1.getMeasurementType())
                .thatValue(q2.getValue())
                .thatUnit(q2.getUnit())
                .thatMeasurementType(q2.getMeasurementType())
                .operation(operation)
                .error(false)
                .build();
    }

    private QuantityMeasurementDTO saveAndReturn(QuantityMeasurementEntity entity) {
        return QuantityMeasurementDTO.fromEntity(repository.save(entity));
    }
}