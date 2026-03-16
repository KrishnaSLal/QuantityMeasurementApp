package com.app.quantitymeasurement;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.util.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityMeasurementApp {

    private static final Logger log = LoggerFactory.getLogger(QuantityMeasurementApp.class);

    public static void main(String[] args) {
        IQuantityMeasurementRepository repository = createRepository();
        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
        QuantityMeasurementController controller = new QuantityMeasurementController(service);

        try {
            QuantityDTO length1 = new QuantityDTO(10.0, "FEET");
            QuantityDTO length2 = new QuantityDTO(6.0, "INCHES");
            log.info("Subtract Length: {}", controller.performSubtraction(length1, length2));

            QuantityDTO weight1 = new QuantityDTO(10.0, "KILOGRAM");
            QuantityDTO weight2 = new QuantityDTO(5.0, "KILOGRAM");
            log.info("Division Weight: {}", controller.performDivision(weight1, weight2));

            QuantityDTO volume1 = new QuantityDTO(5.0, "LITRE");
            QuantityDTO volume2 = new QuantityDTO(10.0, "LITRE");
            log.info("Division Volume: {}", controller.performDivision(volume1, volume2));

            QuantityDTO t1 = new QuantityDTO(100, "CELSIUS");
            QuantityDTO t2 = new QuantityDTO(212, "FAHRENHEIT");
            log.info("Temperature Equality: {}", controller.performComparison(t1, t2));
            log.info("Converted Temperature: {}", controller.performConversion(t2, "CELSIUS"));

            try {
                controller.performAddition(t1, t2);
            } catch (Exception e) {
                log.error("Temperature Addition Error: {}", e.getMessage());
            }

            log.info("Total measurements stored: {}", repository.getTotalCount());
            log.info("Pool stats: {}", repository.getPoolStatistics());

        } finally {
            repository.releaseResources();
        }
    }

    private static IQuantityMeasurementRepository createRepository() {
        String repositoryType = ApplicationConfig.getRepositoryType();

        if ("database".equalsIgnoreCase(repositoryType)) {
            log.info("Using database repository");
            return new QuantityMeasurementDatabaseRepository();
        }

        log.info("Using cache repository");
        return QuantityMeasurementCacheRepository.getInstance();
    }
}