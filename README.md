# 📏 Quantity Measurement App

## 📌 Project Overview

The **Quantity Measurement App** is a Java-based application developed using **Test-Driven Development (TDD)** principles.  
It focuses on validating and comparing different physical quantities such as length, weight, and volume.

Each feature is implemented in a separate Git branch following a **1 Use Case = 1 Branch** strategy to maintain clean version control and structured development.

---

## 🛠 Tech Stack

- Java 8+
- Maven
- JUnit 5
- Object-Oriented Programming (OOP)
- Test-Driven Development (TDD)

---

## 🌿 Branching Strategy

This project follows a **feature-branch workflow**.

- Total Use Cases: **14**
- Total Branches: **14**
- Each branch implements exactly **one use case**

This ensures:
- Clean commit history
- Isolated feature development
- Easy debugging
- Professional Git workflow

---

# 📚 Use Cases

---

## 🔹 UC1 – Feet Measurement Equality

- Implemented `Length` class with `FEET`.
- Implemented value-based equality.
- Covered:
  - Reflexive
  - Symmetric
  - Transitive
  - Null safety
  - Floating-point comparison using `Double.compare()`

📌 Focus: Object equality fundamentals.

---


🔗*Code Link*

[UC1: FeetEquality](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC1-FeetEquality)

---

## 🔹 UC2 – Feet and Inches Equality

- Introduced separate `Inches` class.
- Compared feet-to-feet and inch-to-inch.
- Identified **code duplication problem (DRY violation)**.

📌 Focus: Equality extension & design limitations.

---

🔗*Code Link*

[UC2: InchEquality](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC2-InchEquality/src)

---


## 🔹 UC3 – Cross Unit Length Equality

- Compared `1 ft == 12 inches`.
- Introduced **unit conversion logic**.
- Established base unit normalization.

📌 Focus: Conversion before comparison.

---

🔗*Code Link*

[UC3: GenericQuantity](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC3-GenericQuantityClassForDRYPrinciple/src)

---

## 🔹 UC4 – Multi-Unit Length Support

- Added:
  - Yards
  - Centimeters
- Centralized conversion through base unit.
- Improved test coverage.

📌 Focus: Extensibility within one category.

---

🔗*Code Link*

[UC4: Extended Unit Support](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC4-ExtendedUnitSupport/src)

---

## 🔹 UC5 – Length Addition

- Implemented `add()` method.
- Supported:
  - Same-unit addition
  - Cross-unit addition
- Returned immutable result.

📌 Focus: Arithmetic operations + immutability.

---

🔗*Code Link*

[UC5: Unit-to-Unit Conversion](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC5-Unit-to-UnitConversion/src)

---


## 🔹 UC6 – Refactoring to Quantity Class

- Replaced multiple length classes with:
  ```
  Quantity + LengthUnit enum
  ```
- Removed duplication.
- Introduced enum-based conversion factors.

📌 Focus: Clean design & DRY principle.

---


🔗*Code Link*

[UC6: Addition of Two Length Units](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC6-Addition-Of-Two-Length-Units/src)

---

## 🔹 UC7 – Enhanced Quantity Design

- Strengthened validation.
- Improved equals/hashCode contract.
- Ensured immutability strictly enforced.

📌 Focus: Robust domain modeling.

---

🔗*Code Link*

[UC7: Addition with Target Unit Specification](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC7-Addition-With-Target-Unit-Specification/src)

---


## 🔹 UC8 – Weight Measurement Support

- Added new category:
  - Kilogram (base)
  - Gram
  - Pound
- Created separate `QuantityWeight` class.
- Realized duplication across categories.

📌 Focus: Multi-category support & architectural limitations.

---

🔗*Code Link*

[UC8: Refactoring Unit Enum to Standalone](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC2-InchEquality/src)

---


## 🔹 UC9 – Category-Specific Quantity Classes

- Separate:
  - `QuantityLength`
  - `QuantityWeight`
- Architecture worked but:
  - Violated DRY
  - Increased maintenance burden
  - Not scalable

📌 Focus: Identifying scalability problem.

---

🔗*Code Link*

[UC9: Weight Measurement](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC9-Weight-Measurement/src)

---

## 🔹 UC10 – Generic Architecture Refactor

🎯 Major Architectural Upgrade

Introduced:

```
Quantity<U extends IMeasurable>
```

### Added:
- `IMeasurable` interface
- Generic bounded types
- Single reusable Quantity class

### Benefits:
- Eliminated duplication
- Restored SRP
- Enforced type safety
- Enabled true scalability
- No category-specific Quantity classes needed

📌 Focus: Generics + Interface-driven polymorphism.

---

🔗*Code Link*

[UC10: GenericQuantityClass with Unit Interface](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC10-Generic-Quantity-Class-with-Unit-Interface-For-Multi-Category-Support/src)

---

## 🔹 UC11 – Volume Measurement Support

Added third category:

- Litre (base)
- Millilitre
- Gallon

Implemented only:

```
VolumeUnit implements IMeasurable
```

No modification required in:
- `Quantity<U>`
- `QuantityMeasurementApp`
- Existing test infrastructure

📌 Focus: Proving scalability of UC10 design.

---

🔗*Code Link*

[UC11: VolumeMeasurement](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC11-Volume-Measurement/src)

---
