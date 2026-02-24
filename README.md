# 📏 Quantity Measurement App
---

# ✅ UC4 – Extended Unit Support (Yards & Centimeters)

## 📖 Description

UC4 extends the scalable design introduced in UC3 by adding support for two new length units:

- **YARDS**
- **CENTIMETERS**

This enhancement demonstrates how the generic `QuantityLength` class design allows effortless scaling without modifying core logic.

All equality comparisons now work seamlessly across:

- Feet
- Inches
- Yards
- Centimeters

No additional classes are required — only enum modification.

---

## 🔹 Preconditions

- `QuantityMeasurementApp` uses the refactored `QuantityLength` class from UC3.
- Two numerical values with their respective units are provided.
- Conversion factors are defined in the `LengthUnit` enum:
  - 1 Yard = 3 Feet
  - 1 Yard = 36 Inches
  - 1 cm = 0.393701 Inches

---

## 🔹 Main Flow

1. User inputs two numerical values with units (Feet, Inches, Yards, Centimeters).
2. The system validates:
   - Numeric values
   - Supported unit types
3. Both values are converted to a common base unit (Feet).
4. Converted values are compared using `Double.compare()`.
5. Equality result is returned.

---

## 🔹 Postconditions

- Returns `true` if converted values are equal.
- Returns `false` if converted values are not equal.
- Yard-to-yard comparisons are supported.
- Yard-to-feet comparisons are supported.
- Yard-to-inches comparisons are supported.
- Centimeter-to-centimeter comparisons are supported.
- Centimeter-to-inch comparisons are supported.
- Centimeter-to-feet comparisons are supported.
- All previous UC1–UC3 functionality remains intact.
- No code duplication exists.
- Adding new units requires only enum modification.

---


🔗*Code Link*

[UC4: Extended Unit Support](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC4-ExtendedUnitSupport/src)
---

---

