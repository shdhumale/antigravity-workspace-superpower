# Design Document: Security Enhancement for Ticket Management System

**Date:** 2026-04-03
**Status:** Approved
**Topic:** API Hardening (A) & Secure Data Persistence (C)

## 1. Overview
The goal of this enhancement is to secure the Ticket Management System's API and its data persistence layer. This includes restricting API access to the frontend origin, adding standard security headers, validating inputs, encrypting sensitive fields, and implementing automated audit logging.

## 2. Architecture & Components

### 2.1 Backend (Spring Boot)
- **Spring Security**: We'll add `spring-boot-starter-security` and `spring-boot-starter-validation`.
- **Security Configuration**: A `SecurityConfig` class will define a `SecurityFilterChain` bean to:
    - Whitelist CORS for `http://localhost:4200`.
    - Add security headers (HSTS, X-Frame-Options, X-Content-Type-Options).
    - Disable CSRF for this stateless API (though it can be re-enabled if session-based auth is added later).
- **JPA Auditing**: Enable `@EnableJpaAuditing` and use `@EntityListeners(AuditingEntityListener.class)` on the `TicketEntity`.
- **Encryption Converter**: A `DescriptionEncryptionConverter` implementing `AttributeConverter<String, String>` using AES encryption for the `description` field.

### 2.2 Frontend (Angular)
- No major architectural changes are needed for the frontend, as it will continue to communicate with the fixed origin.

## 3. Data Flow

### 3.1 Request Handling
1. **Security Filter**: Incoming request is checked for CORS origin and security headers are added.
2. **Validation**: The `TicketController` uses `@Valid` on `@RequestBody`, which triggers `jakarta.validation` on the `TicketEntity`.
3. **Audit Injection**: On `save()`, `AuditingEntityListener` populates `createdAt` and `lastModifiedAt`.
4. **Encryption**: Before JDBC persistence, the `AttributeConverter` encrypts the `description`.

### 3.2 Response Handling
1. **Decryption**: On fetch, the `AttributeConverter` decrypts the `description`.
2. **Headers**: Security headers are included in the HTTP response.

## 4. Implementation Details

### TicketEntity.java Changes
- Add `createdAt` and `lastModifiedAt` with appropriate JPA annotations.
- Add validation annotations (`@NotBlank`, `@Size`).
- Add `@Convert(converter = DescriptionEncryptionConverter.class)` to `description`.

### SecurityConfig.java (New)
- `corsConfigurationSource()` bean.
- `securityFilterChain()` bean.

## 5. Testing & Validation
- **Unit Tests**: Test the encryption/decryption logic in isolation.
- **Integration Tests**: Verify that the controller returns `400 Bad Request` for invalid inputs and `403 Forbidden` for unauthorized origins (if enforced).
- **Persistence Tests**: Assert that auditing timestamps are correctly populated.
