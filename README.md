# 📏 Quantity Measurement App
---

# ✅ UC18 – Google Authentication and User Management for Quantity Measurement

---

## 📖 Description

UC18 enhances the **Spring Backend for Quantity Measurement Application** by introducing **Authentication and User Management** using **Spring Security**, **JWT (JSON Web Token)**, and **OAuth2 (Google Authentication)**.

This use case secures the application and enables:

- User login and registration  
- Secure access to APIs  
- Token-based authentication  
- Google OAuth2 login integration  

The system now ensures that only **authenticated users** can access protected endpoints.

---

# 🔹 Preconditions

- All functionality from **UC1–UC17** is fully operational  
- Spring Boot backend is implemented  
- REST APIs are available  
- JPA and database integration is working  
- Maven dependencies are configured  
- Basic user entity structure can be introduced  

---

# 🔹 Main Flow

---

### Step 1 – Add Spring Security Dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-client</artifactId>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
</dependency>
```

### Step 2 - Create User Entity

- Stores user information
- Supports authentication

### Step 3 - Create User Repository

- Database interaction for users
- Fetch user by email

### Step 4 - Configure Spring Security

- Secures APIs
- Enables OAuth2 login

### Step 5 – Implement JWT Utility

- Generates JWT tokens
- Extracts user details

### Step 6 – Create Authentication Controller

- Handles login
- Returns JWT token

### Step 7 - Secure APIs with JWT Filter

- Validates token for each request
- Secures endpoints

### Step 8 – Configure OAuth2 (Google Login)

- Enables Google login
- Uses OAuth2 flow

### Step 9 - Role-Based Authorization

- Restricts access based on roles
- Improves security

### Step 10 - Logging Security Events

- Tracks authentication events
- Helps debugging

### Step 11 – Testing Security

- Validates login functionality
- Ensures security flow works

---

# 🔹Postconditions

- Application supports secure authentication
- JWT-based authentication implemented
- Google OAuth2 login integrated
- APIs are protected with authentication
- Role-based access control implemented
- User data is stored and managed
- Security filters validate every request
- Application becomes production-ready

---

# 🎯 Key Design Improvements
- Spring Security Integration
- JWT Authentication
- OAuth2 (Google Login)
- Role-Based Authorization
- Secure REST APIs
- User Management System
- Token-Based Authentication
- Scalable Security Architecture
- Logging and Monitoring

---

🔗 *Code Link*

[UC18: Authentication](https://github.com/KrishnaSLal/QuantityMeasurementApp/tree/feature/UC18-GoogleAuthenticationUserManagement/quantitymeasurement/src)

---

  

