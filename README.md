# 📏 Quantity Measurement App

---
# ✅ UC11: Volume Measurement Equality, Conversion, and Addition (Litre, Millilitre, Gallon)

---

## 📖 Description

UC11 extends the Quantity Measurement Application to support **Volume measurements** alongside Length and Weight.

A new measurement category — **Volume** — is introduced using the existing generic architecture:

```
Quantity<U extends IMeasurable>
```

This use case proves that the UC10 refactored design is truly scalable.

### Supported Volume Units:

- **LITRE (L)** – Base Unit
- **MILLILITRE (mL)** → 1 L = 1000 mL
- **GALLON (gal)** → 1 gallon ≈ 3.78541 L

UC11 validates that:

- No changes are required in `Quantity<U>`
- No changes are required in `IMeasurable`
- No changes are required in `QuantityMeasurementApp`
- Only a new `VolumeUnit` enum is added

---

## 🔹 Preconditions

- ✅ `Quantity<U extends IMeasurable>` from UC10 is fully operational.
- ✅ `IMeasurable` interface is defined.
- ✅ `LengthUnit` and `WeightUnit` implement `IMeasurable`.
- ✅ All UC1–UC10 test cases pass successfully.
- ✅ VolumeUnit will implement `IMeasurable`.
- ✅ LITRE is selected as base unit.
- ✅ Categories remain isolated (Volume ≠ Length ≠ Weight).

---

## 🔹 Main Flow

### Step 1 – Create `VolumeUnit` Enum

Define:

- `LITRE(1.0)`
- `MILLILITRE(0.001)`
- `GALLON(3.78541)`

Implement:

- `getConversionFactor()`
- `convertToBaseUnit(double value)`
- `convertFromBaseUnit(double baseValue)`
- `getUnitName()`

---

### Step 2 – Equality Comparison

- Two `Quantity<VolumeUnit>` objects are created.
- Values are normalized to base unit (Litre).
- Compared using generic `equals()` method.
- Returns `true` if mathematically equal (within epsilon).

Example:

```
1 L == 1000 mL → true
1 gallon == 3.78541 L → true
```

---

### Step 3 – Unit Conversion

- `convertTo(targetUnit)` is called.
- Value converts → base unit → target unit.
- Returns new immutable object.

Example:

```
1 L → 1000 mL
2 gallons → 7.57082 L
500 mL → 0.132086 gallon
```

---

### Step 4 – Addition

Two variations supported:

#### Implicit Target Unit
Result expressed in first operand’s unit.

```
1 L + 1000 mL → 2 L
```

#### Explicit Target Unit
User specifies desired unit.

```
1 L + 1000 mL (in mL) → 2000 mL
```

---

### Step 5 – Cross Category Safety

Compiler prevents:

```
Quantity<VolumeUnit> ≠ Quantity<LengthUnit>
```

Runtime protection:

```
1 L.equals(1 FOOT) → false
1 L.equals(1 KILOGRAM) → false
```

---

## 🔹 Postconditions

-  Volume measurements of same unit & value are equal.
-  Equivalent cross-unit volumes are equal.
-  Conversion results are mathematically accurate.
-  Addition returns new immutable objects.
-  Volume remains isolated from Length & Weight.
-  No modifications required in core architecture.
-  UC1–UC10 remain fully functional.
-  Architecture proven scalable.
-  New categories can be added using identical pattern.

---

🔗*Code Link*

[UC3: GenericQuantity](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC11-Volume-Measurement/src)

---
