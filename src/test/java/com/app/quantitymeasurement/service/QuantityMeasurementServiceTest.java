package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.util.ConnectionPool;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementServiceTest {

    private IQuantityMeasurementRepository repository;
    private IQuantityMeasurementService service;

    @BeforeEach
    void setUp() {
        ConnectionPool pool = new ConnectionPool(
                "org.h2.Driver",
                "jdbc:h2:mem:serviceTestDb;DB_CLOSE_DELAY=-1",
                "sa",
                "",
                1,
                3
        );
        repository = new QuantityMeasurementDatabaseRepository(pool);
        repository.deleteAll();
        service = new QuantityMeasurementServiceImpl(repository);
    }

    @AfterEach
    void tearDown() {
        repository.releaseResources();
    }

    @Test
    void testCompare() {
        boolean result = service.compare(
                new QuantityDTO(1.0, "FEET"),
                new QuantityDTO(12.0, "INCHES")
        );

        assertTrue(result);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    void testConvert() {
        QuantityDTO result = service.convert(
                new QuantityDTO(1.0, "GALLON"),
                "LITRE"
        );

        assertEquals("LITRE", result.getUnit());
        assertEquals(3.79, result.getValue(), 0.05);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    void testAdd() {
        QuantityDTO result = service.add(
                new QuantityDTO(1.0, "FEET"),
                new QuantityDTO(12.0, "INCHES")
        );

        assertEquals("FEET", result.getUnit());
        assertEquals(2.0, result.getValue(), 0.01);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    void testSubtract() {
        QuantityDTO result = service.subtract(
                new QuantityDTO(3.0, "FEET"),
                new QuantityDTO(12.0, "INCHES")
        );

        assertEquals(2.0, result.getValue(), 0.01);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    void testDivide() {
        double result = service.divide(
                new QuantityDTO(24.0, "INCHES"),
                new QuantityDTO(12.0, "INCHES")
        );

        assertEquals(2.0, result, 0.001);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    void testTemperatureAdditionShouldFail() {
        assertThrows(QuantityMeasurementException.class, () ->
                service.add(
                        new QuantityDTO(10.0, "CELSIUS"),
                        new QuantityDTO(20.0, "CELSIUS")
                )
        );

        assertEquals(1, repository.getTotalCount());
    }

    @Test
    void testCrossCategoryShouldFail() {
        assertThrows(QuantityMeasurementException.class, () ->
                service.compare(
                        new QuantityDTO(1.0, "FEET"),
                        new QuantityDTO(1.0, "GRAM")
                )
        );

        assertEquals(1, repository.getTotalCount());
    }
}