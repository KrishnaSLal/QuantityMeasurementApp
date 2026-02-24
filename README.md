# 📏 Quantity Measurement App

# ✅ UC3 – Generic Quantity Class (DRY Principle Implementation)

## 📖 Description

UC3 refactors the previous implementation (UC1 & UC2) to eliminate code duplication between `Feet` and `Inches`.

Instead of maintaining two nearly identical classes, this use case introduces:

- A single `QuantityLength` class
- A `LengthUnit` enum for supported units
- Centralized conversion logic
- Cross-unit equality comparison

This implementation follows the **DRY (Don't Repeat Yourself)** principle and improves scalability and maintainability.

---

## 🔹 Preconditions

- `QuantityMeasurementApp` is instantiated.
- Two numerical values with their respective units are provided.
- Conversion factors for supported units are defined as constants.
- Units must be part of the supported `LengthUnit` enum.

---

## 🔹 Main Flow

1. User provides two numerical values with units.
2. The `QuantityLength` class validates:
   - Numeric value
   - Supported unit
3. Values are converted to a common base unit (Feet).
4. Converted values are compared using `Double.compare()`.
5. Equality result is returned.

---

## 🔹 Postconditions

- Returns `true` if converted values are equal.
- Returns `false` if converted values are not equal.
- Cross-unit comparison is supported (e.g., 1 Foot == 12 Inches).
- All UC1 and UC2 functionality remains intact.
- Code duplication is eliminated.
- Maintenance complexity is reduced.
- System becomes scalable for adding new units.

---
🔗*Code Link*

[UC3: GenericQuantity](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC3-GenericQuantityClassForDRYPrinciple/src)

---




