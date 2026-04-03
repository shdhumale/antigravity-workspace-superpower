# Security Enhancement Implementation Plan

> **For Antigravity:** REQUIRED WORKFLOW: Use `.agent/workflows/execute-plan.md` to execute this plan in single-flow mode.

**Goal:** Implement API hardening and secure data persistence (encryption and auditing) for the Ticket Management System.

**Architecture:** Use Spring Security for API protection, Spring Data JPA Auditing for timestamps, and a JPA AttributeConverter for field-level encryption.

**Tech Stack:** Spring Boot 3.2.4, Spring Security, JPA, MySQL, JUnit 5.

---

### Task 1: Add Backend Dependencies

**Files:**
- Modify: `backend/pom.xml`

**Step 1: Write the failing test** (N/A for dependency addition, but we can verify classes are missing)

**Step 2: Add dependencies**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

**Step 3: Run build to verify**
Run: `mvn clean install -DskipTests` (in backend dir)
Expected: BUILD SUCCESS

**Step 4: Commit**
```bash
git add backend/pom.xml
git commit -m "build: add security and validation dependencies"
```

---

### Task 2: Enable JPA Auditing

**Files:**
- Modify: `backend/src/main/java/com/ticketmgmt/BackendApplication.java`
- Modify: `backend/src/main/java/com/ticketmgmt/entity/TicketEntity.java`

**Step 1: Write the failing test**
Create: `backend/src/test/java/com/ticketmgmt/AuditingIntegrationTest.java`
```java
@SpringBootTest
@Transactional
public class AuditingIntegrationTest {
    @Autowired private TicketRepository repository;
    @Test
    void testAuditing() {
        TicketEntity ticket = new TicketEntity();
        ticket.setName("Test"); ticket.setStatus("NEW");
        ticket = repository.save(ticket);
        assertNotNull(ticket.getCreatedAt());
    }
}
```

**Step 2: Run test to verify it fails**
Run: `mvn test -Dtest=AuditingIntegrationTest`
Expected: FAIL (getter missing or null)

**Step 3: Implement auditing**
Modify `BackendApplication.java`:
```java
@SpringBootApplication
@EnableJpaAuditing
public class BackendApplication { ... }
```
Modify `TicketEntity.java`:
```java
@EntityListeners(AuditingEntityListener.class)
public class TicketEntity {
    @CreatedDate @Column(updatable = false) private LocalDateTime createdAt;
    @LastModifiedDate private LocalDateTime updatedAt;
    // getters/setters
}
```

**Step 4: Run test to verify it passes**
Run: `mvn test -Dtest=AuditingIntegrationTest`
Expected: PASS

**Step 5: Commit**

---

### Task 3: Implement Field Encryption

**Files:**
- Create: `backend/src/main/java/com/ticketmgmt/util/EncryptionConverter.java`
- Modify: `backend/src/main/java/com/ticketmgmt/entity/TicketEntity.java`

**Step 1: Write the failing test**
Create: `backend/src/test/java/com/ticketmgmt/EncryptionTest.java`
Verify that data in DB is encrypted but readable in Entity.

**Step 2: Implement Converter** (Use AES)

**Step 3: Run test to verify it passes**

**Step 4: Commit**

---

### Task 4: Configure Spring Security (API Hardening)

**Files:**
- Create: `backend/src/main/java/com/ticketmgmt/config/SecurityConfig.java`

**Step 1: Write failing test for CORS**
Verify that a request from `http://malicious.com` is rejected.

**Step 2: Implement SecurityConfig**
Exclude CSRF for now, Configure CORS for `localhost:4200`.

**Step 3: Run test to verify it passes**

**Step 4: Commit**

---

### Task 5: Request Validation

**Files:**
- Modify: `backend/src/main/java/com/ticketmgmt/controller/TicketController.java`
- Modify: `backend/src/main/java/com/ticketmgmt/entity/TicketEntity.java`

**Step 1: Write failing test**
Send a POST request with an empty Name.

**Step 2: Add @NotBlank and @Valid**

**Step 3: Run test to verify it passes**

**Step 4: Commit**
