package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.util.ConnectionPool;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementDatabaseRepositoryTest {

    private QuantityMeasurementDatabaseRepository repository;

    @BeforeEach
    void setUp() {
        ConnectionPool pool = new ConnectionPool(
                "org.h2.Driver",
                "jdbc:h2:mem:repoTestDb;DB_CLOSE_DELAY=-1",
                "sa",
                "",
                1,
                3
        );
        repository = new QuantityMeasurementDatabaseRepository(pool);
        repository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        repository.releaseResources();
    }

    @Test
    void testSaveEntity() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                "COMPARE", "LENGTH", "1 FEET", "12 INCHES", "true", false, null
        );

        repository.save(entity);

        assertEquals(1, repository.getTotalCount());
        assertNotNull(entity.getId());
    }

    @Test
    void testFindAll() {
        repository.save(new QuantityMeasurementEntity("ADD", "LENGTH", "1 FEET", "12 INCHES", "2 FEET", false, null));
        repository.save(new QuantityMeasurementEntity("DIVIDE", "WEIGHT", "10 KG", "5 KG", "2.0", false, null));

        List<com.app.quantitymeasurement.entity.QuantityMeasurementEntity> all = repository.findAll();

        assertEquals(2, all.size());
    }

    @Test
    void testFindByOperationType() {
        repository.save(new QuantityMeasurementEntity("ADD", "LENGTH", "1 FEET", "12 INCHES", "2 FEET", false, null));
        repository.save(new QuantityMeasurementEntity("DIVIDE", "WEIGHT", "10 KG", "5 KG", "2.0", false, null));

        List<com.app.quantitymeasurement.entity.QuantityMeasurementEntity> result =
                repository.findByOperationType("ADD");

        assertEquals(1, result.size());
        assertEquals("ADD", result.get(0).getOperationType());
    }

    @Test
    void testFindByMeasurementType() {
        repository.save(new QuantityMeasurementEntity("ADD", "LENGTH", "1 FEET", "12 INCHES", "2 FEET", false, null));
        repository.save(new QuantityMeasurementEntity("DIVIDE", "WEIGHT", "10 KG", "5 KG", "2.0", false, null));

        List<com.app.quantitymeasurement.entity.QuantityMeasurementEntity> result =
                repository.findByMeasurementType("LENGTH");

        assertEquals(1, result.size());
        assertEquals("LENGTH", result.get(0).getMeasurementType());
    }

    @Test
    void testDeleteAll() {
        repository.save(new QuantityMeasurementEntity("ADD", "LENGTH", "1 FEET", "12 INCHES", "2 FEET", false, null));
        repository.save(new QuantityMeasurementEntity("DIVIDE", "WEIGHT", "10 KG", "5 KG", "2.0", false, null));

        assertEquals(2, repository.getTotalCount());

        repository.deleteAll();

        assertEquals(0, repository.getTotalCount());
    }

    @Test
    void testPoolStatistics() {
        String stats = repository.getPoolStatistics();
        assertNotNull(stats);
        assertTrue(stats.contains("ConnectionPool"));
    }
}