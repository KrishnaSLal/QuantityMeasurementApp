# 📏 Quantity Measurement App
---

# ✅ UC15 – N-Tier Architecture Refactoring for Quantity Measurement Application

---

## 📖 Description

UC15 refactors the **Quantity Measurement Application** from a monolithic structure into a professional **N-Tier Architecture** by separating responsibilities into distinct layers:

- **Application Layer**
- **Controller Layer**
- **Service Layer**
- **Entity / Model Layer**
- **Repository Layer**

This use case transforms the application into a more **scalable**, **maintainable**, and **testable** system while preserving all functionality from **UC1–UC14**.

The refactoring ensures that:

- Business logic is no longer mixed with application flow
- Data movement between layers is standardized
- Error handling becomes more structured
- The design becomes ready for future extension such as:
  - REST API integration
  - persistence/database support
  - dependency injection frameworks
  - GUI or CLI reuse

UC15 does **not change behavior**, it only improves the **internal architecture** of the application.

---

# 🔹 Preconditions

- All functionality from **UC1–UC14** is fully operational
- `Quantity<U>` generic implementation already supports:
  - Equality
  - Conversion
  - Addition
  - Subtraction
  - Division
- `IMeasurable` and all unit enums are already implemented:
  - `LengthUnit`
  - `WeightUnit`
  - `VolumeUnit`
  - `TemperatureUnit`
- Temperature continues to support:
  - Equality
  - Conversion
  - Selective arithmetic restriction
- Existing monolithic `QuantityMeasurementApp` contains demonstration logic
- Existing test cases from UC1–UC14 must continue to pass
- The goal is architectural refactoring without breaking previous use cases

---

# 🔹 Main Flow

---

## Step 1 – Introduce N-Tier Architecture

The existing monolithic design is refactored into dedicated layers:

### Application Layer
Responsible for:
- Bootstrapping the application
- Initializing dependencies
- Creating controller, service, and repository objects
- Starting the application flow

### Controller Layer
Responsible for:
- Accepting requests
- Delegating work to the service layer
- Returning standardized results

### Service Layer
Responsible for:
- Performing business operations
- Invoking quantity comparison, conversion, and arithmetic logic
- Validating operations
- Handling exceptions
- Interacting with repository layer

### Entity / Model Layer
Responsible for:
- Standardized data flow between layers
- Internal data representation
- External request/response structures
- Persistent operation history representation

### Repository Layer
Responsible for:
- Storing quantity measurement operation history
- Providing abstraction for persistence
- Supporting future storage implementations

---

## Step 2 – Create DTO and POJO Classes

UC15 introduces structured objects for communication between layers.

### `QuantityDTO`
Used for transferring quantity data between layers.

It typically contains:
- `value`
- `unit`

Used for:
- Controller input
- Service input/output
- Future API payloads

---

### `QuantityModel`
Used internally in the service layer.

It represents:
- quantity value
- quantity unit
- generic unit type bounded by `IMeasurable`

Used for:
- internal business processing
- safe layer-level abstraction

---

### `QuantityMeasurementEntity`
Represents a complete measurement operation.

It stores:
- first operand
- second operand (if applicable)
- operation type
- result
- error information

Used for:
- repository storage
- operation history
- debugging and reporting
- structured error representation

---

## Step 3 – Create Repository Layer

### `IQuantityMeasurementRepository`
Defines the repository contract.

Typical responsibilities:
- save a quantity measurement record
- return all stored measurements

This ensures:
- loose coupling
- interface-based design
- easy substitution of implementation

---

### `QuantityMeasurementCacheRepository`
Implements the repository contract as an **in-memory cache repository**.

Features:
- Singleton design pattern
- Stores operation history in memory
- Can be extended later for file/database storage
- Centralized access point for measurement records

This layer makes future migration to database persistence easier without changing service logic.

---

## Step 4 – Create Custom Exception

### `QuantityMeasurementException`

A dedicated custom runtime exception is introduced to handle application-specific failures such as:

- invalid units
- invalid conversions
- unsupported arithmetic operations
- cross-category operations
- divide-by-zero situations

This improves:
- readability
- centralized error handling
- maintainability
- debugging

---

## Step 5 – Create Service Layer

### `IQuantityMeasurementService`
Defines the business contract for the application.

Supported service operations:
- Compare two quantities
- Convert one quantity into another unit
- Add two quantities
- Subtract two quantities
- Divide two quantities

This interface isolates the controller from implementation details.

---

### `QuantityMeasurementServiceImpl`
Implements all business operations.

This class:
- accepts `QuantityDTO` input
- converts DTOs into internal quantity representations
- delegates to existing `Quantity<U>` logic
- performs validation
- handles exceptions
- stores operation details in repository
- returns standardized DTO results

This preserves all existing functionality while introducing architectural separation.

---

## Step 6 – Create Controller Layer

### `QuantityMeasurementController`

Acts as the bridge between application flow and service layer.

Responsibilities:
- receive input from application layer
- call service methods
- expose simple perform methods such as:
  - `performComparison()`
  - `performConversion()`
  - `performAddition()`
  - `performSubtraction()`
  - `performDivision()`

This keeps the controller thin and focused only on orchestration.

---

## Step 7 – Refactor Application Entry Point

### `QuantityMeasurementApp`

The application class is simplified.

Instead of directly performing all logic, it now:

- creates repository instance
- injects repository into service
- injects service into controller
- delegates all operations through the controller

This creates a cleaner and more professional entry point.

---

## Step 8 – Standardize Data Flow

A structured flow is introduced:

```text
Application Layer
      ↓
Controller Layer
      ↓
Service Layer
      ↓
Repository Layer
```
# 🔹 Postconditions

- Application is refactored into clearly separated layers
- Monolithic design is removed
- QuantityMeasurementApp becomes a clean entry point
- Business logic is centralized in service layer
- Controller handles only orchestration
- DTOs standardize communication between layers
- Repository layer stores operation history
- Error handling is structured and centralized
  
🔗 *Code Link*

[UC15: N-Tier Architecture Refractor]((https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC15-NTierArchitectureRefactor/src))

---
