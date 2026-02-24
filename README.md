# 📏 Quantity Measurement App
---

# ✅ UC14 – Temperature Measurement with Selective Arithmetic Support

---

## 📖 Description

UC14 extends the **Quantity Measurement Application** to support **Temperature measurements** alongside Length, Weight, and Volume.
UC14 Successfully Extends the Measurement System.
Temperature integrates seamlessly while respecting its mathematical constraints.

Unlike other categories:

-  Temperature supports **Equality**
-  Temperature supports **Unit Conversion**
-  Temperature does NOT support Addition
-  Temperature does NOT support Subtraction
-  Temperature does NOT support Division

This use case refactors the `IMeasurable` interface to make arithmetic support **optional** using:

-  Default methods
-  Functional Interface
-  Lambda expressions
-  Capability-based validation

This ensures:

- Backward compatibility with UC1–UC13
- SOLID compliance (especially ISP)
- Clean separation of conversion and arithmetic capability

---

# 🔹 Preconditions

- All functionality from **UC1–UC13** is fully operational.
- Centralized arithmetic logic (UC13) exists.
- `IMeasurable` currently supports only conversion.
- Length, Weight, and Volume units implement `IMeasurable`.
- A new `TemperatureUnit` enum will be created.
- Arithmetic support must become optional.
- Cross-category comparisons remain prohibited.
- All existing test cases must pass without modification.

---

# 🔹 Main Flow

---

## Step 1 – Analyze Temperature Characteristics

### Supported:
- Equality comparison
- Unit conversion

### Unsupported:
- Addition
- Subtraction
- Division
- Multiplication

### Important Concept:
Temperature is **non-linear conversion** (offset-based).

Example:
```
°F = (°C × 9/5) + 32
```

Absolute temperatures cannot be meaningfully added or divided.

---

## Step 2️ – Refactor `IMeasurable` Interface

### Add Functional Interface

```java
@FunctionalInterface
public interface SupportsArithmetic {
    boolean isSupported();
}
```

---

### Add Default Lambda (All Units Support Arithmetic by Default)

```java
SupportsArithmetic supportsArithmetic = () -> true;
```

---

### Add Default Methods

```java
default boolean supportsArithmetic() {
    return supportsArithmetic.isSupported();
}

default void validateOperationSupport(String operation) {
    // Default: allow all operations
}
```

✔ Existing units (Length, Weight, Volume) require **no changes**  
✔ Backward compatibility preserved  

---

## Step 3️ – Create `TemperatureUnit` Enum

### Enum Constants:

- CELSIUS (Base Unit)
- FAHRENHEIT
- KELVIN

---

### Lambda Conversion Example

```java
private final Function<Double, Double> toCelsius;

CELSIUS(value -> value),
FAHRENHEIT(value -> (value - 32) * 5 / 9),
KELVIN(value -> value - 273.15);
```

---

### Arithmetic Support Disabled

```java
SupportsArithmetic supportsArithmetic = () -> false;
```

---

### Override Validation

```java
@Override
public void validateOperationSupport(String operation) {
    throw new UnsupportedOperationException(
        "Temperature does not support " + operation + " operation."
    );
}
```

---

## Step 4️ – Update `Quantity<U>` Class

### Arithmetic Methods Updated

Before performing any arithmetic:

```java
this.unit.validateOperationSupport(operation.name());
```

✔ Prevents unsupported operations early  
✔ Clear error messages  
✔ Fail-fast behavior  

---

### Conversion Method Updated

If unit is `TemperatureUnit`:
- Use temperature-specific conversion
- Handle non-linear conversion logic

Other categories:
- Continue using base-unit multiplication

---

## Step 5️ – Cross-Category Type Safety

Already handled via:

```java
if (this.unit.getClass() != other.unit.getClass()) {
    return false;
}
```

✔ Temperature cannot compare with Length  
✔ Temperature cannot compare with Weight  
✔ Temperature cannot compare with Volume  

---

## Step 6️ – Demonstration Examples

### ✅ Equality

```
new Quantity<>(0.0, CELSIUS)
    .equals(new Quantity<>(32.0, FAHRENHEIT))
→ true
```

```
new Quantity<>(273.15, KELVIN)
    .equals(new Quantity<>(0.0, CELSIUS))
→ true
```

---

### ✅ Conversion

```
100°C → 212°F
32°F → 0°C
0°C → 273.15K
-40°C → -40°F
```

---

### ❌ Unsupported Operations

```
new Quantity<>(100.0, CELSIUS)
    .add(new Quantity<>(50.0, CELSIUS))

→ UnsupportedOperationException
```

---

### ❌ Cross-Category Comparison

```
new Quantity<>(100.0, CELSIUS)
    .equals(new Quantity<>(100.0, FEET))

→ false
```

---

# 🔹 Postconditions

- TemperatureUnit supports CELSIUS, FAHRENHEIT, and KELVIN  

- Accurate non-linear conversion formulas implemented  

- Equality works across temperature units  

- Temperature arithmetic operations throw `UnsupportedOperationException`  

- `IMeasurable` now supports optional arithmetic via default methods  

- Quantity validates operation support before execution  

- All UC1–UC13 test cases pass without modification  

- Cross-category type safety preserved  

- Error messages clearly explain unsupported operations  

- Interface Segregation Principle (ISP) enforced  

- Backward compatibility maintained  

- Design scales to future constrained measurement categories  

- Non-linear conversion logic handled correctly  

- Compile-time generic safety preserved  

- Runtime validation prevents invalid arithmetic  

---

# 🎯 Key Design Improvements

- ✔ Capability-based design
- ✔ Functional Interface usage
- ✔ Lambda Expressions
- ✔ Default Methods in Interfaces
- ✔ Polymorphic error handling
- ✔ SOLID compliance
- ✔ Non-breaking interface evolution
- ✔ Strong type safety
- ✔ Clean extension of generic system

---


🔗 *Code Link*

[UC14: Temperature Measurement](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC14-TemperatureMeasurement/src)

---
