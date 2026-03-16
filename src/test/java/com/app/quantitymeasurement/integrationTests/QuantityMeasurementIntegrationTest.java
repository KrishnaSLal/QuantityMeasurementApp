package com.app.quantitymeasurement.integrationTests;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.util.ConnectionPool;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementIntegrationTest {

    private IQuantityMeasurementRepository repository;
    private QuantityMeasurementController controller;

    @BeforeEach
    void setUp() {
        ConnectionPool pool = new ConnectionPool(
                "org.h2.Driver",
                "jdbc:h2:mem:integrationDb;DB_CLOSE_DELAY=-1",
                "sa",
                "",
                2,
                5
        );
        repository = new QuantityMeasurementDatabaseRepository(pool);
        repository.deleteAll();

        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
        controller = new QuantityMeasurementController(service);
    }

    @AfterEach
    void tearDown() {
        repository.releaseResources();
    }

    @Test
    void testEndToEndOperationsPersistToDatabase() {
        assertTrue(controller.performComparison(
                new QuantityDTO(1.0, "FEET"),
                new QuantityDTO(12.0, "INCHES")
        ));

        QuantityDTO converted = controller.performConversion(
                new QuantityDTO(1.0, "GALLON"),
                "LITRE"
        );
        assertEquals("LITRE", converted.getUnit());

        QuantityDTO added = controller.performAddition(
                new QuantityDTO(1.0, "LITRE"),
                new QuantityDTO(1000.0, "MILLILITRE")
        );
        assertEquals(2.0, added.getValue(), 0.01);

        assertEquals(3, repository.getTotalCount());
        assertEquals(1, repository.findByOperationType("COMPARE").size());
        assertEquals(1, repository.findByOperationType("CONVERT").size());
        assertEquals(1, repository.findByOperationType("ADD").size());
    }

    @Test
    void testErrorOperationAlsoPersists() {
        assertThrows(RuntimeException.class, () ->
                controller.performAddition(
                        new QuantityDTO(10.0, "CELSIUS"),
                        new QuantityDTO(20.0, "CELSIUS")
                )
        );

        assertEquals(1, repository.getTotalCount());
        assertTrue(repository.findAll().get(0).isError());
    }
}