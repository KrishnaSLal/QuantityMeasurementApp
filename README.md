# 📏 Quantity Measurement App

#✅ UC5: Unit-to-Unit Conversion (Same Measurement Type)

##📖 Description

UC5 extends UC4 by providing explicit conversion operations between length units  
(e.g., feet → inches, yards → inches, centimeters → feet).

Instead of only comparing equality, the `QuantityLength` API now exposes a 
conversion method that returns a numeric value converted from a source unit 
to a target unit using centralized conversion factors.

---

## 🔹 Preconditions

- `QuantityLength` class (from UC3/UC4) exists.
- `LengthUnit` enum includes:
  - FEET
  - INCHES
  - YARDS
  - CENTIMETERS
- Each unit defines a conversion factor relative to a chosen base unit.
- Input consists of:
  - numeric value
  - valid source `LengthUnit`
  - valid target `LengthUnit`

---

## 🔹 Main Flow

1. Client calls

2. The method validates:
- value is finite (`Double.isFinite`)
- sourceUnit is not null
- targetUnit is not null

3. Convert value to common base unit.

4. Convert base unit to target unit.

5. Apply precision handling (epsilon tolerance if required).

6. Return converted numeric value.

---

## 🔹 Postconditions

- A numeric value representing the converted measurement is returned.
- Invalid inputs (null unit, NaN, infinity) throw `IllegalArgumentException`.
- Conversion preserves mathematical equivalence within floating-point precision limits.
- Same-unit conversion returns the original value unchanged.
- Zero values remain zero.
- Negative values preserve sign.
- Round-trip conversions maintain value within epsilon tolerance.

---


🔗*Code Link*

[UC5: Unit-to-Unit Conversion](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC5-Unit-to-UnitConversion/src)
---

---

