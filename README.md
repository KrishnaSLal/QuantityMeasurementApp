# 📏 Quantity Measurement App
---

# ✅ UC12 – Subtraction and Division Operations

## 📖 Description

UC12 extends the scalable generic architecture introduced in UC10 and validated in UC11 by adding support for two new arithmetic operations:

- **SUBTRACTION**
- **DIVISION**

This enhancement transforms the `Quantity<U extends IMeasurable>` class into a fully capable arithmetic measurement framework.

Subtraction allows users to compute the difference between two quantities of the same category.

Division allows users to compute a dimensionless ratio between two quantities.

No architectural restructuring is required — only method additions inside the existing `Quantity` class.

---

## 🔹 Preconditions

- The generic `Quantity<U extends IMeasurable>` class from UC10 is fully operational.
- `LengthUnit`, `WeightUnit`, and `VolumeUnit` implement `IMeasurable`.
- Base unit conversion logic is correctly implemented.
- All functionality from UC1–UC11 remains intact.
- Subtraction and division methods are added to the existing `Quantity` class.

---

## 🔹 Main Flow

### ➖ Subtraction

1. User calls `subtract(Quantity<U> other)`  
   OR `subtract(Quantity<U> other, U targetUnit)`
2. The system validates:
   - Operand is not null
   - Units belong to the same category
   - Numeric values are finite
3. Both quantities are converted to their common base unit.
4. Subtraction is performed in base unit.
5. Result is converted:
   - To first operand’s unit (implicit), OR
   - To explicitly specified target unit.
6. Result is rounded to two decimal places.
7. A new immutable `Quantity<U>` object is returned.

---

###  Division

1. User calls `divide(Quantity<U> other)`
2. The system validates:
   - Operand is not null
   - Units belong to the same category
   - Divisor is not zero
3. Both quantities are converted to base unit.
4. Division is performed.
5. A dimensionless `double` value is returned.

---

## 🔹 Postconditions

### Subtraction

- Returns a new `Quantity<U>` object.
- Supports same-unit subtraction.
- Supports cross-unit subtraction within the same category.
- Supports explicit target unit specification.
- Negative results are supported.
- Zero results are supported.
- Cross-category subtraction is prevented.
- Original objects remain unchanged (immutability preserved).

### Division

- Returns a dimensionless scalar (`double`).
- Supports same-unit division.
- Supports cross-unit division within the same category.
- Division by zero throws `ArithmeticException`.
- Cross-category division is prevented.
- Original objects remain unchanged.

---

## 🔹 Example Outputs

### Subtraction (Implicit Target Unit)

- `10 FEET - 6 INCHES → 9.5 FEET`
- `10 KILOGRAM - 5000 GRAM → 5.0 KILOGRAM`
- `5 LITRE - 500 MILLILITRE → 4.5 LITRE`

### Subtraction (Explicit Target Unit)

- `10 FEET - 6 INCHES (INCHES) → 114.0 INCHES`
- `5 LITRE - 2 LITRE (MILLILITRE) → 3000.0 MILLILITRE`

### Division

- `10 FEET ÷ 2 FEET → 5.0`
- `24 INCHES ÷ 2 FEET → 1.0`
- `2000 GRAM ÷ 1 KILOGRAM → 2.0`
- `5 LITRE ÷ 10 LITRE → 0.5`

---

## 🔹 Mathematical Properties

- Subtraction is non-commutative.
- Division is non-commutative.
- Division is non-associative.
- Addition and subtraction maintain inverse relationship:
  - `A.add(B).subtract(B) ≈ A`

---

## 🔹 Architectural Impact

- No new classes introduced.
- No duplication introduced.
- No enum modifications required.
- Generic architecture remains unchanged.
- Fully scalable across all measurement categories.
- All previous use cases (UC1–UC11) remain unaffected.

---

🔗 *Code Link*

[UC12: Subtraction and Division Operations](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC12-QuantitySubtractionAndDivision/src)

---
