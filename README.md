# 📏 Quantity Measurement App
---

# ✅ UC13 – Centralized Arithmetic Logic to Enforce DRY in Quantity Operations

---

## 📖 Description

UC13 refactors the arithmetic operations implemented in UC12 to eliminate code duplication and strictly enforce the **DRY (Don't Repeat Yourself) Principle**.

Instead of repeating validation, base-unit normalization, and arithmetic execution logic across:

-  `add()`
-  `subtract()`
-  `divide()`

UC13 introduces:

-  A centralized private validation helper  
-  A centralized base arithmetic helper  
-  An `ArithmeticOperation` enum for operation dispatch  

All public method signatures remain unchanged.  
All behaviors from UC12 are preserved exactly.  
This is an internal architectural refactor only.

---

## ❌ Disadvantages of UC12 Implementation

### 1 Code Duplication Across Arithmetic Methods

- Null checks repeated in every method
- Category compatibility checks repeated
- Finiteness validation duplicated
- Base-unit conversion duplicated
- Target unit handling duplicated
- Arithmetic execution logic repeated

Future operations like MULTIPLY or MODULO would further increase duplication.

---

### 2 DRY Principle Violation

- Validation logic copied verbatim
- Error handling scattered across methods
- Conversion logic repeated
- Updates require changes in multiple places
- Risk of inconsistent behavior between operations

---

### 3 Increased Maintenance Burden

- Bug fixes must be applied in multiple methods
- High risk of partial updates
- Refactoring becomes complex
- Code review effort increases

---

### 4 Reduced Readability

- Public arithmetic methods are long
- Boilerplate validation hides core arithmetic logic
- Intent of method is not immediately clear

---

### 5 Scalability Issues

- Adding new arithmetic operations increases duplication
- Codebase grows without architectural improvement
- Complexity increases unnecessarily

---

## 🔹 Preconditions

- ✅ UC12 add(), subtract(), divide() are fully implemented
- ✅ All arithmetic operations are tested and passing
- ✅ All unit enums implement `IMeasurable`
- ✅ Base-unit conversion logic works correctly
- ✅ Public API must remain unchanged
- ✅ Existing test cases must pass without modification
- ✅ Refactoring must not alter arithmetic results
- ✅ Centralized helper methods will be introduced internally

---

## 🔹  Main Flow

---

### Step 1 – Identify Common Logic

Shared validation logic:

- Null operand check
- Unit category compatibility check
- Finiteness validation for numeric values
- Optional target unit validation (for add/subtract)

Shared conversion logic:

- Convert this quantity to base unit
- Convert other quantity to base unit
- Perform arithmetic operation
- Convert result back to target unit (if required)

---

### Step 2 – Create ArithmeticOperation Enum

Create a private enum inside `Quantity`:

- `ADD`
- `SUBTRACT`
- `DIVIDE`
- `MULTIPLY` (future-ready)

Two possible implementations:

#### 1️⃣ Abstract Method Approach

Each enum constant overrides:

### 2️⃣ Lambda Expression Approach

Using `DoubleBinaryOperator`:

ADD((a, b) -> a + b)

- Lambda uses:

  - Functional Interface: DoubleBinaryOperator

  - Method: applyAsDouble(double left, double right)

This enables type-safe arithmetic dispatch.

#### Step 3 – Create Centralized Validation Helper

```java
private void validateArithmeticOperands(
    Quantity<U> other,
    U targetUnit,
    boolean targetUnitRequired
)
```

Validates:

- Operand is not null

- Unit categories match

- Numeric values are finite

- Target unit is not null (when required)

All validation logic exists only in this method.

#### Step 4 – Create Core Arithmetic Helper Method

```java
private double performBaseArithmetic(
    Quantity<U> other,
    ArithmeticOperation operation
)
```
- Flow:

  - Convert both quantities to base unit

  - Execute arithmetic using enum dispatch

  - Return base-unit result

No conversion to target unit inside this method.

#### Step 5 – Refactor Public Arithmetic Methods

- add(Quantity<U> other)

- subtract(Quantity<U> other)

- divide(Quantity<U> other)

#### Step 6 – Ensure Backward Compatibility

Public method signatures unchanged

Arithmetic results identical to UC12

Error messages consistent

All UC12 test cases pass without modification

No behavioral regression introduced

🔹 Postconditions

-  All arithmetic operations delegate to centralized helper methods

-  Validation logic exists only in validateArithmeticOperands()

-  Conversion logic exists only in performBaseArithmetic()

-  No duplication across add, subtract, divide

-  Public API remains unchanged

-  Arithmetic behavior identical to UC12

-  All UC12 test cases pass

-  Error handling consistent across all operations

-  DRY principle fully enforced

-  Maintainability significantly improved

-  Code readability improved

-  Immutability preserved

- Subtraction remains non-commutative

-  Division remains non-commutative

-  Future operations (MULTIPLY, MODULO, etc.) can reuse the same pattern

-  Centralized validation ensures consistent failure behavior

-  Codebase complexity reduced

-  Scalability across all measurement categories preserved

---

🔗 *Code Link*

[UC13: Centralized Arithemaatic Logic](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC13-Centralized-Arithmetic-Logic/src)

---
