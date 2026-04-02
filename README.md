# 📏 Quantity Measurement App

---

# ✅ UC10: Generic Quantity Class with Unit Interface for Multi-Category Support

---

## 📖 Description

UC10 refactors the architecture into a single generic:

    Quantity<U extends IMeasurable>

This eliminates duplication introduced in UC9 (separate QuantityLength and QuantityWeight classes).

The new architecture:

- Introduces IMeasurable interface
- Makes all unit enums implement IMeasurable
- Replaces category-specific Quantity classes with a single generic class
- Simplifies QuantityMeasurementApp
- Preserves all functionality from UC1–UC9

This design ensures scalability, maintainability, and adherence to SOLID principles.

---

## ❗ Problems in UC9 (Why Refactoring Was Needed)

### Duplicate Unit Enum Structures
- LengthUnit and WeightUnit had identical method signatures.
- Same conversion logic pattern repeated.
- Violated DRY principle.

### Duplicate Quantity Classes
- QuantityLength and QuantityWeight had identical:
  - Constructor validation
  - equals() logic
  - convertTo() implementation
  - add() methods
- Any change required updating multiple classes.

### QuantityMeasurementApp SRP Violation
- Separate methods for length and weight:
  - demonstrateLengthEquality()
  - demonstrateWeightEquality()
- Duplication of logic across categories.
- Class complexity increased exponentially with new categories.

### Exponential Growth
Each new category required:
- New Unit enum
- New Quantity class
- New demonstration methods
- New test class

Not scalable.

---

## 🔹 Preconditions

- UC1–UC9 functionality fully working.
- IMeasurable interface defined.
- LengthUnit and WeightUnit refactored to implement IMeasurable.
- Generic Quantity<U extends IMeasurable> class created.
- All existing tests pass unchanged.
- Type safety enforced via generics.

---

## 🔹Main Flow

### Step 1 – Define IMeasurable Interface

Interface methods:

- double getConversionFactor()
- double convertToBaseUnit(double value)
- double convertFromBaseUnit(double baseValue)
- String getUnitName()

This standardizes behavior across all unit enums.

---

### Step 2 – Refactor LengthUnit

- Implements IMeasurable.
- Keeps constants:
  - FEET
  - INCHES
  - YARDS
  - CENTIMETERS
- No external API changes.

---

### Step 3 – Refactor WeightUnit

- Implements IMeasurable.
- Keeps constants:
  - KILOGRAM
  - GRAM
  - POUND
- Matches structure of LengthUnit.

---

### Step 4 – Create Generic Quantity Class

    public final class Quantity<U extends IMeasurable>

Fields:
- private final double value
- private final U unit

Constructor:
- Validates unit != null
- Validates value is finite

Core Methods:

#### equals()
- Checks null
- Checks object identity
- Checks unit.getClass() equality
- Converts both values to base unit
- Uses Double.compare()

#### convertTo(U targetUnit)
- Delegates conversion to unit methods
- Returns new Quantity<U>
- Rounds to two decimal places

#### add(Quantity<U> other)
- Converts both to base unit
- Adds values
- Converts result to first operand’s unit

#### add(Quantity<U> other, U targetUnit)
- Converts both to base unit
- Adds values
- Converts result to targetUnit

Overrides:
- hashCode()
- toString()

Immutable design maintained.

---

### Step 5 – Simplify QuantityMeasurementApp

Before (UC9):
- Separate methods per category.

After (UC10):
- Single generic methods:

    demonstrateEquality(Quantity<?> q1, Quantity<?> q2)
    demonstrateConversion(Quantity<?> q, IMeasurable target)
    demonstrateAddition(Quantity<?> q1, Quantity<?> q2)

No duplication.

---

### Step 6 – Backward Compatibility

- All UC1–UC9 tests pass unchanged.
- Public behavior identical.
- Only internal structure refactored.

---

## 🔹 Postconditions

- One generic Quantity class replaces all category-specific Quantity classes.
- All unit enums implement IMeasurable.
- DRY principle fully upheld.
- SRP restored.
- System open for extension, closed for modification.
- Adding new category requires only:
  - New enum implementing IMeasurable.
- No change to Quantity<U>.
- No change to QuantityMeasurementApp.
- Code growth becomes linear.

---




🔗*Code Link*

[UC10: GenericQuantityClass with Unit Interface](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC10-Generic-Quantity-Class-with-Unit-Interface-For-Multi-Category-Support/src)

---




