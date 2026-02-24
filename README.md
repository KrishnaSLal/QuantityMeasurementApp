# 📏 Quantity Measurement App

# 📚 Use Cases

---

# ✅ UC1 – Feet Measurement Equality

## 📖 Description

The `QuantityMeasurementApp` class is responsible for checking the equality of two numerical values measured in **feet**.

It ensures accurate comparison while handling:
- Floating-point precision
- Null safety
- Type safety
- Equality contract rules

---

## 🔹 Preconditions

- `QuantityMeasurementApp` class is instantiated.
- Two numerical values in feet are provided.

---

## 🔹 Main Flow

1. User inputs two numerical values in feet.
2. The system validates the inputs.
3. The system compares both values using proper equality logic.
4. The result (`true` or `false`) is returned.

---

## 🔹 Postconditions

- Returns `true` if both values are equal.
- Returns `false` if values are different.

---

📂 Project Structure
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
