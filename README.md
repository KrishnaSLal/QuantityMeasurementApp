# 📏 Quantity Measurement App

# ✅ UC2 – Feet and Inches Measurement Equality

## 📖 Description

This use case extends UC1 by introducing **Inches** measurement equality in addition to Feet.

⚠ Important:
Feet and Inches are treated as separate entities.
This use case does NOT compare feet with inches.
It only compares:
- Feet ↔ Feet
- Inches ↔ Inches

---

## 🔹 Preconditions

- Two numerical values in feet (hard-coded)
- Two numerical values in inches (hard-coded)

---

## 🔹 Main Flow

1. Main method calls static method for Feet equality.
2. Main method calls static method for Inches equality.
3. Each method:
   - Instantiates respective class (Feet / Inches)
   - Calls overridden `equals()` method
   - Returns comparison result

---

## 🔹 Postconditions

- Returns `true` if two Feet values are equal.
- Returns `false` if two Feet values are different.
- Returns `true` if two Inches values are equal.
- Returns `false` if two Inches values are different.
- Feet and Inches are treated as separate types and are not considered equal to each other.

---

🔗*Code Link*

[UC2: InchEquality](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC2-InchEquality/src)
---
