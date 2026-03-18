package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuantityMeasurementServiceImplTest {

    @Mock
    private QuantityMeasurementRepository repository;

    @InjectMocks
    private QuantityMeasurementServiceImpl service;

    @Test
    void givenEqualLengths_whenCompare_thenReturnTrue() {
        QuantityInputDTO input = new QuantityInputDTO(
                new QuantityDTO(1.0, "FEET", "LengthUnit"),
                new QuantityDTO(12.0, "INCHES", "LengthUnit")
        );

        when(repository.save(any(QuantityMeasurementEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        QuantityMeasurementDTO result = service.compare(input);

        assertEquals("COMPARE", result.getOperation());
        assertEquals("true", result.getResultString());
        assertFalse(result.isError());
    }

    @Test
    void givenLengthAndInches_whenConvert_thenReturnConvertedValue() {
        QuantityInputDTO input = new QuantityInputDTO(
                new QuantityDTO(1.0, "FEET", "LengthUnit"),
                new QuantityDTO(0.0, "INCHES", "LengthUnit")
        );

        when(repository.save(any(QuantityMeasurementEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        QuantityMeasurementDTO result = service.convert(input);

        assertEquals("CONVERT", result.getOperation());
        assertEquals(12.0, result.getResultValue());
        assertEquals("INCHES", result.getResultUnit());
    }

    @Test
    void givenSameLengthUnits_whenAdd_thenReturnSum() {
        QuantityInputDTO input = new QuantityInputDTO(
                new QuantityDTO(1.0, "FEET", "LengthUnit"),
                new QuantityDTO(12.0, "INCHES", "LengthUnit")
        );

        when(repository.save(any(QuantityMeasurementEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        QuantityMeasurementDTO result = service.add(input);

        assertEquals("ADD", result.getOperation());
        assertEquals(2.0, result.getResultValue());
        assertEquals("FEET", result.getResultUnit());
    }

    @Test
    void givenSameLengthUnits_whenSubtract_thenReturnDifference() {
        QuantityInputDTO input = new QuantityInputDTO(
                new QuantityDTO(2.0, "FEET", "LengthUnit"),
                new QuantityDTO(12.0, "INCHES", "LengthUnit")
        );

        when(repository.save(any(QuantityMeasurementEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        QuantityMeasurementDTO result = service.subtract(input);

        assertEquals("SUBTRACT", result.getOperation());
        assertEquals(1.0, result.getResultValue());
    }

    @Test
    void givenValidInput_whenDivide_thenReturnRatio() {
        QuantityInputDTO input = new QuantityInputDTO(
                new QuantityDTO(24.0, "INCHES", "LengthUnit"),
                new QuantityDTO(12.0, "INCHES", "LengthUnit")
        );

        when(repository.save(any(QuantityMeasurementEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        QuantityMeasurementDTO result = service.divide(input);

        assertEquals("DIVIDE", result.getOperation());
        assertEquals(2.0, result.getResultValue());
    }

    @Test
    void givenDivideByZero_whenDivide_thenThrowException() {
        QuantityInputDTO input = new QuantityInputDTO(
                new QuantityDTO(1.0, "FEET", "LengthUnit"),
                new QuantityDTO(0.0, "INCHES", "LengthUnit")
        );

        QuantityMeasurementException ex = assertThrows(
                QuantityMeasurementException.class,
                () -> service.divide(input)
        );

        assertEquals("Divide by zero", ex.getMessage());
    }

    @Test
    void givenDifferentMeasurementTypes_whenAdd_thenThrowException() {
        QuantityInputDTO input = new QuantityInputDTO(
                new QuantityDTO(1.0, "FEET", "LengthUnit"),
                new QuantityDTO(1.0, "KILOGRAM", "WeightUnit")
        );

        QuantityMeasurementException ex = assertThrows(
                QuantityMeasurementException.class,
                () -> service.add(input)
        );

        assertTrue(ex.getMessage().contains("different measurement categories"));
    }

    @Test
    void givenTemperatureInput_whenAdd_thenThrowException() {
        QuantityInputDTO input = new QuantityInputDTO(
                new QuantityDTO(10.0, "CELSIUS", "TemperatureUnit"),
                new QuantityDTO(20.0, "CELSIUS", "TemperatureUnit")
        );

        QuantityMeasurementException ex = assertThrows(
                QuantityMeasurementException.class,
                () -> service.add(input)
        );

        assertEquals("ADD not supported for TemperatureUnit", ex.getMessage());
    }

    @Test
    void whenGetHistoryByOperation_thenReturnMappedDtos() {
        QuantityMeasurementEntity entity = QuantityMeasurementEntity.builder()
                .operation("COMPARE")
                .resultString("true")
                .error(false)
                .build();

        when(repository.findByOperationIgnoreCase("COMPARE")).thenReturn(List.of(entity));

        List<QuantityMeasurementDTO> result = service.getHistoryByOperation("COMPARE");

        assertEquals(1, result.size());
        assertEquals("COMPARE", result.get(0).getOperation());
    }

    @Test
    void whenGetOperationCount_thenReturnCount() {
        when(repository.countByOperationIgnoreCaseAndErrorFalse("COMPARE")).thenReturn(3L);

        long count = service.getOperationCount("COMPARE");

        assertEquals(3L, count);
    }

    @Test
    void whenCompare_thenRepositorySaveShouldBeCalled() {
        QuantityInputDTO input = new QuantityInputDTO(
                new QuantityDTO(1.0, "FEET", "LengthUnit"),
                new QuantityDTO(12.0, "INCHES", "LengthUnit")
        );

        when(repository.save(any(QuantityMeasurementEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        service.compare(input);

        ArgumentCaptor<QuantityMeasurementEntity> captor =
                ArgumentCaptor.forClass(QuantityMeasurementEntity.class);

        verify(repository, times(1)).save(captor.capture());
        assertEquals("COMPARE", captor.getValue().getOperation());
    }
}