package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        IQuantityMeasurementRepository repository = QuantityMeasurementCacheRepository.getInstance();
        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
        QuantityMeasurementController controller = new QuantityMeasurementController(service);

        QuantityDTO length1 = new QuantityDTO(10.0, "FEET");
        QuantityDTO length2 = new QuantityDTO(6.0, "INCHES");

        System.out.println("Subtract Length: " + controller.performSubtraction(length1, length2));

        QuantityDTO weight1 = new QuantityDTO(10.0, "KILOGRAM");
        QuantityDTO weight2 = new QuantityDTO(5.0, "KILOGRAM");

        System.out.println("Division Weight: " + controller.performDivision(weight1, weight2));

        QuantityDTO volume1 = new QuantityDTO(5.0, "LITRE");
        QuantityDTO volume2 = new QuantityDTO(10.0, "LITRE");

        System.out.println("Division Volume: " + controller.performDivision(volume1, volume2));

        QuantityDTO t1 = new QuantityDTO(100, "CELSIUS");
        QuantityDTO t2 = new QuantityDTO(212, "FAHRENHEIT");

        System.out.println("Temperature Equality: " + controller.performComparison(t1, t2));

        System.out.println("Converted Temperature: " + controller.performConversion(t2, "CELSIUS"));

        try {
            System.out.println(controller.performAddition(t1, t2));
        } catch (Exception e) {
            System.out.println("Temperature Addition Error: " + e.getMessage());
        }
    }
}