package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.util.ConnectionPool;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementControllerTest {

    private QuantityMeasurementController controller;
    private IQuantityMeasurementRepository repository;

    @BeforeEach
    void setUp() {
        ConnectionPool pool = new ConnectionPool(
                "org.h2.Driver",
                "jdbc:h2:mem:controllerTestDb;DB_CLOSE_DELAY=-1",
                "sa",
                "",
                1,
                3
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
    void testCompareFeetAndInches() {
        assertTrue(controller.performComparison(
                new QuantityDTO(1.0, "FEET"),
                new QuantityDTO(12.0, "INCHES")
        ));
    }

    @Test
    void testConvertGallonsToLiters() {
        QuantityDTO result = controller.performConversion(
                new QuantityDTO(1.0, "GALLON"),
                "LITRE"
        );
        assertEquals("LITRE", result.getUnit());
        assertEquals(3.78541, result.getValue(), 0.05);
    }

    @Test
    void testAddition() {
        QuantityDTO result = controller.performAddition(
                new QuantityDTO(1.0, "FEET"),
                new QuantityDTO(2.0, "INCHES")
        );
        assertEquals("FEET", result.getUnit());
        assertEquals(1.17, result.getValue(), 0.02);
    }

    @Test
    void testSubtraction() {
        QuantityDTO result = controller.performSubtraction(
                new QuantityDTO(3.0, "FEET"),
                new QuantityDTO(12.0, "INCHES")
        );
        assertEquals(2.0, result.getValue(), 0.01);
    }

    @Test
    void testDivision() {
        double result = controller.performDivision(
                new QuantityDTO(24.0, "INCHES"),
                new QuantityDTO(12.0, "INCHES")
        );
        assertEquals(2.0, result, 0.001);
    }

    @Test
    void testCrossCategoryShouldFail() {
        assertThrows(RuntimeException.class, () ->
                controller.performComparison(
                        new QuantityDTO(1.0, "FEET"),
                        new QuantityDTO(1.0, "GRAM")
                )
        );
    }

    @Test
    void testTemperatureAdditionShouldFail() {
        assertThrows(RuntimeException.class, () ->
                controller.performAddition(
                        new QuantityDTO(10.0, "CELSIUS"),
                        new QuantityDTO(20.0, "CELSIUS")
                )
        );
    }
}