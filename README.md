# 📏 Quantity Measurement App

---
# 📏 Quantity Measurement App
---

# ✅ UC17 – Spring Backend for Quantity Measurement

---

## 📖 Description

UC17 transforms the **Quantity Measurement Application** into a **Spring Boot-based backend system** by leveraging the powerful Spring ecosystem.

This use case modernizes the application by:

- Converting it into a **RESTful web service**
- Replacing manual wiring with **Spring Dependency Injection**
- Replacing JDBC boilerplate with **Spring Data JPA**
- Introducing **Spring MVC architecture**

The application now supports:

- REST APIs for all quantity operations  
- Automatic configuration using Spring Boot  
- Clean layered architecture using Spring conventions  

---

# 🔹 Preconditions

- All functionality from **UC1–UC16** is fully operational  
- N-Tier architecture is implemented  
- JDBC-based persistence is working  
- Maven project structure is available  
- Entities, DTOs, Repository, and Service layers exist  

---

# 🔹 Main Flow

---

### Step 1 – Create Spring Boot Application

```java
@SpringBootApplication
public class QuantityMeasurementApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuantityMeasurementApplication.class, args);
    }
}

```
---

### Step 2 – Convert Controller to REST Controller

```java
@RestController
@RequestMapping("/api/quantity")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    @PostMapping("/add")
    public double add(@RequestBody QuantityDTO dto1,
                      @RequestBody QuantityDTO dto2) {
        return service.add(dto1, dto2);
    }
}
```

### Step 3 - Implement Service Layer with Spring

- Dependency injection used
- Business logic is managed by Spring

### Step 4 - Create JPA Repository

- Eliminates boilerplate code
- Provides CRUD operations automatically

### Step 5 - Convert Entity to JPA Entity

- Maps java objects to database table
- Uses ORM(Object Relational Mapping)

### Step 6 - Configure application.properties

- Enables database configuration
- Auto schema generation

### Step 7 - Add Dependency Injection

- Provides loose coupling & better testability

### Step 8 - Add Global Exception Handling

- Centralized error handling
- Clean API response

### Step 9 - Add Validation

### Step 10 - Expose REST APIs

### Step 11 - Logging with SLF4J

### Step 12 - Testing with Spring Boot

---

# 🔹Postconditions

- Application is converted into a Spring Boot backend
- REST APIs are available for all operations
- Dependency Injection replaces manual object creation
- JPA replaces JDBC boilerplate
- Global exception handling implemented
- Validation ensures correct inputs
- Logging improves observability
- Embedded server runs application
- Application is ready for frontend integration


---

# 🎯 Key Design Improvements

- Spring Boot Integration
- REST API Architecture
- Dependency Injection
- Spring MVC Pattern
- Spring Data JPA
- Global Exception Handling
- Validation with Annotations
- Embedded Server (Tomcat)
- Logging with SLF4J
- Scalable Backend Design

---

🔗 *Code Link*

[UC17: SpringBackend](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC17-SpringBackend/src)

---

  
