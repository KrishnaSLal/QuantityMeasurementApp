# 📏 Quantity Measurement App
---
# ✅ UC6: Addition of Two Length Units (Same Category) 

---

## 📖 Description

UC6 extends UC5 by introducing addition operations between length measurements.

This use case enables the `QuantityLength` API to add two lengths of potentially 
different units (but same category — length) and return the result in the unit 
of the first operand.

Example:
1 foot + 12 inches = 2 feet  
12 inches + 1 foot = 24 inches  

---

## 🔹 Preconditions

- `QuantityLength` class exists (from UC3/UC4/UC5).
- `LengthUnit` enum includes:
  - FEET
  - INCHES
  - YARDS
  - CENTIMETERS
- Each unit defines a conversion factor relative to a common base unit.
- Two `QuantityLength` objects are provided.
- All units belong to the same measurement category (Length).
- The result is returned in the unit of the first operand.

---


## 🔹 Main Flow
1. Client calls

2. Validate:
- length1 is not null
- length2 is not null
- Units are valid
- Values are finite (`Double.isFinite`)

3. Convert both operands to a common base unit.

4. Add the normalized values.

5. Convert the result back to the unit of the first operand.

6. Return a new `QuantityLength` instance.

---

## 🔹 Postconditions

- A new `QuantityLength` object is returned.
- Result is expressed in the unit of the first operand.
- Original objects remain unchanged (immutability preserved).
- Addition is mathematically accurate within floating-point tolerance.
- Addition respects commutative property.
- Invalid inputs throw `IllegalArgumentException`.

---

🔗*Code Link*

[UC6: Addition of Two Length Units](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC6-Addition-Of-Two-Length-Units/src)
---

---
