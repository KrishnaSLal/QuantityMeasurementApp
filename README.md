# 📏 Quantity Measurement App

# ✅ UC7: Addition with Target Unit Specification 

---

## 📖 Description

UC7 extends UC6 by allowing the caller to explicitly specify the unit for the addition result.

Instead of defaulting to the unit of the first operand, this use case enables:

Example:

1 foot + 12 inches in YARDS  
→ ≈ 0.667 yards  

This provides greater flexibility when the result must be expressed in a specific unit.

---

## 🔹 Preconditions

- `QuantityLength` class exists (from UC3–UC6).
- `LengthUnit` enum includes:
  - FEET
  - INCHES
  - YARDS
  - CENTIMETERS
- Conversion factors are defined relative to a common base unit.
- Two `QuantityLength` objects are provided.
- A valid `targetUnit` is explicitly specified.
- All units belong to the same measurement category (Length).

---

## 🔹 Main Flow

1️. Client calls

2. Validate:
- length1 is not null
- length2 is not null
- targetUnit is not null
- Units are valid
- Values are finite (`Double.isFinite`)

3️. Convert both operands to common base unit.

4️. Add normalized values.

5️. Convert result to explicitly specified `targetUnit`.

6️. Return new `QuantityLength` instance.

---

## 🔹 Postconditions

- A new `QuantityLength` object is returned.
- Result is always expressed in the explicitly specified target unit.
- Original objects remain unchanged (Immutability preserved).
- Addition is mathematically accurate within floating-point tolerance.
- Addition remains commutative:
  
---

🔗*Code Link*

[UC7: Addition with Target Unit Specification](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC7-Addition-With-Target-Unit-Specification/src)

---
