# 📏 Quantity Measurement App
---

# ✅ UC9: Weight Measurement Equality, Conversion, and Addition (Kilogram, Gram, Pound)

---

## 📖 Description

UC9 extends the Quantity Measurement Application to support **weight measurements** alongside length measurements.

A new measurement category — **Weight** — is introduced and operates independently from Length.

Supported weight units:

- KILOGRAM (kg) → Base unit
- GRAM (g) → 1 kg = 1000 g
- POUND (lb) → 1 lb ≈ 0.453592 kg

UC9 validates that the scalable architectural pattern built in UC1–UC8 works seamlessly for multiple measurement categories without modifying existing length logic.

The `WeightUnit` enum and `QuantityWeight` class mirror the `LengthUnit` and `QuantityLength` structure introduced in UC8.

---

## 🔹 Preconditions

- QuantityMeasurementApp is instantiated.
- WeightUnit enum exists as a standalone enum (similar to LengthUnit in UC8).
- Conversion factors are defined relative to the base unit (KILOGRAM).
- Length functionality from UC1–UC8 remains unaffected.
- Weight and Length are treated as separate categories.
- All input values must be finite numbers.
- Units must not be null.

---

## 🔹 Main Flow

### 1. Equality Comparison

1. Two QuantityWeight objects are provided.
2. Both values are converted to the base unit (KILOGRAM).
3. Comparison is performed using equals().
4. Result (true/false) is returned.

### 2. Unit Conversion

1. User provides a value, source unit, and target unit.
2. Value is normalized to base unit (KILOGRAM).
3. Converted to target unit.
4. New immutable QuantityWeight object is returned.

### 3. Addition (Implicit Target Unit)

1. Two QuantityWeight objects are provided.
2. Both are converted to base unit.
3. Values are added.
4. Result converted to first operand's unit.
5. New QuantityWeight object returned.

### 4. Addition (Explicit Target Unit)

1. Two QuantityWeight objects + target WeightUnit provided.
2. Both converted to base unit.
3. Values added.
4. Result converted to specified target unit.
5. New QuantityWeight object returned.

---

## 🔹 Postconditions

- Equivalent weight values across units are equal.
- Conversion accuracy is maintained within floating-point tolerance (1e-6).
- Addition returns a new immutable object.
- Original objects remain unchanged.
- Weight and Length cannot be compared.
- All previous length functionality remains fully operational.
- Architecture supports easy addition of new measurement categories.

---

🔗*Code Link*

[UC9: Weight Measurement](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC9-Weight-Measurement/src)

---


