package com.app.measurement.controller;

import com.app.measurement.dto.QuantityInputDTO;
import com.app.measurement.dto.QuantityMeasurementDTO;
import com.app.measurement.service.IQuantityMeasurementService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/measurements")
public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    @PostMapping("/compare")
    public QuantityMeasurementDTO compare(@Valid @RequestBody QuantityInputDTO inputDTO,
                                          Authentication authentication) {
        return service.compare(inputDTO, authentication.getName());
    }

    @PostMapping("/convert")
    public QuantityMeasurementDTO convert(@Valid @RequestBody QuantityInputDTO inputDTO,
                                          Authentication authentication) {
        return service.convert(inputDTO, authentication.getName());
    }

    @PostMapping("/add")
    public QuantityMeasurementDTO add(@Valid @RequestBody QuantityInputDTO inputDTO,
                                      Authentication authentication) {
        return service.add(inputDTO, authentication.getName());
    }

    @PostMapping("/subtract")
    public QuantityMeasurementDTO subtract(@Valid @RequestBody QuantityInputDTO inputDTO,
                                           Authentication authentication) {
        return service.subtract(inputDTO, authentication.getName());
    }

    @PostMapping("/divide")
    public QuantityMeasurementDTO divide(@Valid @RequestBody QuantityInputDTO inputDTO,
                                         Authentication authentication) {
        return service.divide(inputDTO, authentication.getName());
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
    public Long getOperationCount(@PathVariable String operation) {
        return service.getOperationCount(operation);
    }

    @GetMapping("/history/errored")
    public List<QuantityMeasurementDTO> getErroredHistory() {
        return service.getErroredHistory();
    }
}