package com.app.quantitymeasurement.controller;


import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    @PostMapping("/compare")
    public QuantityMeasurementDTO compare(@Valid @RequestBody QuantityInputDTO inputDTO) {
        return service.compare(inputDTO);
    }

    @PostMapping("/convert")
    public QuantityMeasurementDTO convert(@Valid @RequestBody QuantityInputDTO inputDTO) {
        return service.convert(inputDTO);
    }

    @PostMapping("/add")
    public QuantityMeasurementDTO add(@Valid @RequestBody QuantityInputDTO inputDTO) {
        return service.add(inputDTO);
    }

    @PostMapping("/subtract")
    public QuantityMeasurementDTO subtract(@Valid @RequestBody QuantityInputDTO inputDTO) {
        return service.subtract(inputDTO);
    }

    @PostMapping("/divide")
    public QuantityMeasurementDTO divide(@Valid @RequestBody QuantityInputDTO inputDTO) {
        return service.divide(inputDTO);
    }

    @GetMapping("/history/operation/{operation}")
    public List<QuantityMeasurementDTO> getHistoryByOperation(@PathVariable String operation) {
        return service.getHistoryByOperation(operation);
    }

    @GetMapping("/history/type/{measurementType}")
    public List<QuantityMeasurementDTO> getHistoryByMeasurementType(@PathVariable String measurementType) {
        return service.getHistoryByMeasurementType(measurementType);
    }

    @GetMapping("/count/{operation}")
    public long getOperationCount(@PathVariable String operation) {
        return service.getOperationCount(operation);
    }

    @GetMapping("/history/errored")
    public List<QuantityMeasurementDTO> getErroredHistory() {
        return service.getErroredHistory();
    }
}