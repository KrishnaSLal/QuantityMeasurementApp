package com.app.quantitymeasurement.model;

import com.app.quantitymeasurement.unit.IMeasurable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class QuantityModel<U extends IMeasurable> {

    private final double value;
    private final U unit;
}