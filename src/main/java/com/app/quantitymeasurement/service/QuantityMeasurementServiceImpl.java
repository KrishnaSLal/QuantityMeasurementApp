package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.Quantity;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.LengthUnit;
import com.app.quantitymeasurement.unit.TemperatureUnit;
import com.app.quantitymeasurement.unit.VolumeUnit;
import com.app.quantitymeasurement.unit.WeightUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger log = LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }

    private String normalizeUnitName(String unitName) {
        String normalized = unitName.trim().toUpperCase();

        return switch (normalized) {
            case "GRAMS" -> "GRAM";
            case "KILOGRAMS" -> "KILOGRAM";
            case "POUNDS" -> "POUND";
            case "LITRES", "LITERS" -> "LITRE";
            case "MILLILITRES", "MILLILITERS" -> "MILLILITRE";
            case "GALLONS" -> "GALLON";
            default -> normalized;
        };
    }

    private IMeasurable parseUnit(String unitName) {
        if (unitName == null || unitName.isBlank()) {
            throw new QuantityMeasurementException("Unit cannot be null or blank");
        }

        String normalized = normalizeUnitName(unitName);

        try {
            return LengthUnit.valueOf(normalized);
        } catch (IllegalArgumentException ignored) { }

        try {
            return WeightUnit.valueOf(normalized);
        } catch (IllegalArgumentException ignored) { }

        try {
            return VolumeUnit.valueOf(normalized);
        } catch (IllegalArgumentException ignored) { }

        try {
            return TemperatureUnit.valueOf(normalized);
        } catch (IllegalArgumentException ignored) { }

        throw new QuantityMeasurementException("Invalid unit: " + unitName);
    }

    private void validateSameCategory(IMeasurable unit1, IMeasurable unit2) {
        if (!unit1.getClass().equals(unit2.getClass())) {
            throw new QuantityMeasurementException("Cross-category operations not allowed");
        }
    }

    @SuppressWarnings("unchecked")
    private <U extends IMeasurable> Quantity<U> buildQuantity(QuantityDTO dto) {
        if (dto == null) {
            throw new QuantityMeasurementException("QuantityDTO cannot be null");
        }

        IMeasurable measurable = parseUnit(dto.getUnit());
        return new Quantity<>(dto.getValue(), (U) measurable);
    }

    private QuantityDTO toDTO(Quantity<?> quantity) {
        return new QuantityDTO(quantity.getValue(), quantity.getUnit().toString());
    }

    private String resolveMeasurementType(IMeasurable measurable) {
        if (measurable instanceof LengthUnit) return "LENGTH";
        if (measurable instanceof WeightUnit) return "WEIGHT";
        if (measurable instanceof VolumeUnit) return "VOLUME";
        if (measurable instanceof TemperatureUnit) return "TEMPERATURE";
        return "UNKNOWN";
    }

    private void persistSuccess(String operation, String measurementType, String operand1, String operand2, String result) {
        repository.save(new QuantityMeasurementEntity(
                operation,
                measurementType,
                operand1,
                operand2,
                result,
                false,
                null
        ));
    }

    private void persistError(String operation, String measurementType, String operand1, String operand2, String errorMessage) {
        repository.save(new QuantityMeasurementEntity(
                operation,
                measurementType,
                operand1,
                operand2,
                null,
                true,
                errorMessage
        ));
    }

    @Override
    public boolean compare(QuantityDTO quantity1, QuantityDTO quantity2) {
        try {
            Quantity<?> q1 = buildQuantity(quantity1);
            Quantity<?> q2 = buildQuantity(quantity2);

            validateSameCategory(q1.getUnit(), q2.getUnit());

            boolean result = q1.equals(q2);
            persistSuccess("COMPARE", resolveMeasurementType(q1.getUnit()),
                    quantity1.toString(), quantity2.toString(), String.valueOf(result));

            log.info("Comparison performed successfully");
            return result;

        } catch (Exception e) {
            persistError("COMPARE", "UNKNOWN", String.valueOf(quantity1), String.valueOf(quantity2), e.getMessage());
            throw new QuantityMeasurementException("Comparison failed: " + e.getMessage(), e);
        }
    }

    @Override
    public QuantityDTO convert(QuantityDTO sourceQuantity, String targetUnit) {
        try {
            Quantity<?> source = buildQuantity(sourceQuantity);
            IMeasurable targetMeasurable = parseUnit(targetUnit);

            validateSameCategory(source.getUnit(), targetMeasurable);

            Quantity<?> converted;

            if (targetMeasurable instanceof LengthUnit target) {
                converted = ((Quantity<LengthUnit>) source).convertTo(target);
            } else if (targetMeasurable instanceof WeightUnit target) {
                converted = ((Quantity<WeightUnit>) source).convertTo(target);
            } else if (targetMeasurable instanceof VolumeUnit target) {
                converted = ((Quantity<VolumeUnit>) source).convertTo(target);
            } else if (targetMeasurable instanceof TemperatureUnit target) {
                converted = ((Quantity<TemperatureUnit>) source).convertTo(target);
            } else {
                throw new QuantityMeasurementException("Unsupported target unit");
            }

            QuantityDTO result = toDTO(converted);
            persistSuccess("CONVERT", resolveMeasurementType(source.getUnit()),
                    sourceQuantity.toString(), targetUnit, result.toString());

            log.info("Conversion performed successfully");
            return result;

        } catch (Exception e) {
            persistError("CONVERT", "UNKNOWN", String.valueOf(sourceQuantity), targetUnit, e.getMessage());
            throw new QuantityMeasurementException("Conversion failed: " + e.getMessage(), e);
        }
    }

    @Override
    public QuantityDTO add(QuantityDTO quantity1, QuantityDTO quantity2) {
        try {
            Quantity<?> q1 = buildQuantity(quantity1);
            Quantity<?> q2 = buildQuantity(quantity2);

            validateSameCategory(q1.getUnit(), q2.getUnit());

            Quantity<?> result;

            if (q1.getUnit() instanceof LengthUnit) {
                result = ((Quantity<LengthUnit>) q1).add((Quantity<LengthUnit>) q2);
            } else if (q1.getUnit() instanceof WeightUnit) {
                result = ((Quantity<WeightUnit>) q1).add((Quantity<WeightUnit>) q2);
            } else if (q1.getUnit() instanceof VolumeUnit) {
                result = ((Quantity<VolumeUnit>) q1).add((Quantity<VolumeUnit>) q2);
            } else if (q1.getUnit() instanceof TemperatureUnit) {
                result = ((Quantity<TemperatureUnit>) q1).add((Quantity<TemperatureUnit>) q2);
            } else {
                throw new QuantityMeasurementException("Unsupported measurement type for addition");
            }

            QuantityDTO resultDTO = toDTO(result);
            persistSuccess("ADD", resolveMeasurementType(q1.getUnit()),
                    quantity1.toString(), quantity2.toString(), resultDTO.toString());

            log.info("Addition performed successfully");
            return resultDTO;

        } catch (Exception e) {
            persistError("ADD", "UNKNOWN", String.valueOf(quantity1), String.valueOf(quantity2), e.getMessage());
            throw new QuantityMeasurementException("Addition failed: " + e.getMessage(), e);
        }
    }

    @Override
    public QuantityDTO subtract(QuantityDTO quantity1, QuantityDTO quantity2) {
        try {
            Quantity<?> q1 = buildQuantity(quantity1);
            Quantity<?> q2 = buildQuantity(quantity2);

            validateSameCategory(q1.getUnit(), q2.getUnit());

            Quantity<?> result;

            if (q1.getUnit() instanceof LengthUnit) {
                result = ((Quantity<LengthUnit>) q1).subtract((Quantity<LengthUnit>) q2);
            } else if (q1.getUnit() instanceof WeightUnit) {
                result = ((Quantity<WeightUnit>) q1).subtract((Quantity<WeightUnit>) q2);
            } else if (q1.getUnit() instanceof VolumeUnit) {
                result = ((Quantity<VolumeUnit>) q1).subtract((Quantity<VolumeUnit>) q2);
            } else if (q1.getUnit() instanceof TemperatureUnit) {
                result = ((Quantity<TemperatureUnit>) q1).subtract((Quantity<TemperatureUnit>) q2);
            } else {
                throw new QuantityMeasurementException("Unsupported measurement type for subtraction");
            }

            QuantityDTO resultDTO = toDTO(result);
            persistSuccess("SUBTRACT", resolveMeasurementType(q1.getUnit()),
                    quantity1.toString(), quantity2.toString(), resultDTO.toString());

            log.info("Subtraction performed successfully");
            return resultDTO;

        } catch (Exception e) {
            persistError("SUBTRACT", "UNKNOWN", String.valueOf(quantity1), String.valueOf(quantity2), e.getMessage());
            throw new QuantityMeasurementException("Subtraction failed: " + e.getMessage(), e);
        }
    }

    @Override
    public double divide(QuantityDTO quantity1, QuantityDTO quantity2) {
        try {
            Quantity<?> q1 = buildQuantity(quantity1);
            Quantity<?> q2 = buildQuantity(quantity2);

            validateSameCategory(q1.getUnit(), q2.getUnit());

            double result;

            if (q1.getUnit() instanceof LengthUnit) {
                result = ((Quantity<LengthUnit>) q1).divide((Quantity<LengthUnit>) q2);
            } else if (q1.getUnit() instanceof WeightUnit) {
                result = ((Quantity<WeightUnit>) q1).divide((Quantity<WeightUnit>) q2);
            } else if (q1.getUnit() instanceof VolumeUnit) {
                result = ((Quantity<VolumeUnit>) q1).divide((Quantity<VolumeUnit>) q2);
            } else if (q1.getUnit() instanceof TemperatureUnit) {
                result = ((Quantity<TemperatureUnit>) q1).divide((Quantity<TemperatureUnit>) q2);
            } else {
                throw new QuantityMeasurementException("Unsupported measurement type for division");
            }

            persistSuccess("DIVIDE", resolveMeasurementType(q1.getUnit()),
                    quantity1.toString(), quantity2.toString(), String.valueOf(result));

            log.info("Division performed successfully");
            return result;

        } catch (Exception e) {
            persistError("DIVIDE", "UNKNOWN", String.valueOf(quantity1), String.valueOf(quantity2), e.getMessage());
            throw new QuantityMeasurementException("Division failed: " + e.getMessage(), e);
        }
    }
}