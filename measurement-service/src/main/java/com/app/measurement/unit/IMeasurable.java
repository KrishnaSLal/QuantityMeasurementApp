package com.app.measurement.unit;

public interface IMeasurable {
    double toBase(double value);
    double fromBase(double value);
}