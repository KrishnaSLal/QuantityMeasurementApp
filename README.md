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

📂 Project Structure
--

```text
QuantityMeasurementApp
│
├── src
│   ├── main
│   │   └── java
│   │       └── com.apps.quantitymeasurement
│   │
│   └── test
│       └── java
│           └── com.apps.quantitymeasurement
│
├── pom.xml
└── README.md
```

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


---

## 🔹 UC12 – Advanced Arithmetic Operations

Expanded arithmetic capabilities across all measurable categories.

### Added:
- `subtract()`
- `divide()`
- Overloaded arithmetic methods with target unit support

### Supported:
- Cross-unit arithmetic
- Base-unit normalization before operation
- Immutable result objects

### Ensured:
- Subtraction remains **non-commutative**
- Division returns **dimensionless double**
- Cross-category arithmetic prohibited

📌 Focus: Complete arithmetic support with strict validation.

---

🔗 *Code Link*

[UC12: Subtraction and Division Operations](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC12-QuantitySubtractionAndDivision/src)

---

## 🔹 UC13 – Centralized Arithmetic Refactor

🎯 Major Internal Refactor (No Public API Change)

Refactored arithmetic logic to remove duplication and improve maintainability.

### Introduced:
```
ArithmeticOperation enum
DoubleBinaryOperator (Lambda-based dispatch)
Centralized validation helper
Core base-unit arithmetic method
```

### Achieved:
- DRY principle fully enforced
- Centralized validation logic
- Clean separation of concerns
- No behavioral changes from UC12
- All existing test cases passed without modification

📌 Focus: Clean architecture + lambda-driven arithmetic dispatch.

---

🔗 *Code Link*

[UC13: Centralized Arithemaatic Logic](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC13-Centralized-Arithmetic-Logic/src)

---


## 🔹 UC14 – Temperature Measurement Support

Extended system to support **Temperature category** with controlled arithmetic capability.

### Added:
- `TemperatureUnit` enum
  - Celsius (base)
  - Fahrenheit
  - Kelvin

### Special Behavior:
- Supports equality
- Supports unit conversion
- Does NOT support arithmetic operations

### Architectural Enhancement:
- Refactored `IMeasurable` to support optional arithmetic
- Introduced capability-based validation
- Used default methods + functional interface
- Preserved backward compatibility

📌 Focus: Extending system while respecting domain constraints.

---

🔗 *Code Link*

[UC14: Temperature Measurement](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC14-TemperatureMeasurement/src)

---

## 🔹 UC15 – N-Tier Architecture Refactoring

UC15 refactors the **Quantity Measurement Application** into a structured **N-Tier Architecture** to improve maintainability, scalability, and testability while preserving all functionality from **UC1–UC14**.

### Layered Architecture

The application is reorganized into the following layers:

- **Application Layer**
  - `QuantityMeasurementApp`
  - Initializes dependencies and acts as the entry point.

- **Controller Layer**
  - `QuantityMeasurementController`
  - Handles requests and delegates operations to the service layer.

- **Service Layer**
  - `IQuantityMeasurementService`
  - `QuantityMeasurementServiceImpl`
  - Implements core business logic:
    - Quantity comparison
    - Unit conversion
    - Addition, subtraction, division
    - Validation and exception handling

- **Repository Layer**
  - `IQuantityMeasurementRepository`
  - `QuantityMeasurementCacheRepository`
  - Stores measurement operations using an **in-memory cache (Singleton pattern)**.

- **Entity / Model Layer**
  - `QuantityDTO` – data transfer between layers
  - `QuantityModel` – internal quantity representation
  - `QuantityMeasurementEntity` – stores operation history and results

### Key Improvements

- Clear **separation of concerns**
- Business logic centralized in **Service Layer**
- Standardized data flow using **DTOs**
- Operation history tracking via **Entity objects**
- **Custom exception handling** (`QuantityMeasurementException`)
- **Dependency Injection–ready design**
- Improved **testability and extensibility**

### Outcome

The application now follows a **clean enterprise-style architecture** while keeping all previous features intact.  
This design prepares the system for future extensions such as **REST APIs, database persistence, and framework integration**.

📌 Focus: A scalable N-Tier architecture by separating responsibilities into Controller, Service, Repository, and Entity layers

---

🔗 *Code Link*

[UC15: N-Tier Architecture Refractor](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC15-NTierArchitectureRefactor/src)

---
