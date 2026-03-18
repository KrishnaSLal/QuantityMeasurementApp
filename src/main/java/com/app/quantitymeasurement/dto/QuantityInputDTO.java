package com.app.quantitymeasurement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuantityInputDTO {

    @Valid
    @NotNull(message = "First quantity is required")
    private QuantityDTO thisQuantityDTO;

    @Valid
    @NotNull(message = "Second quantity is required")
    private QuantityDTO thatQuantityDTO;
}