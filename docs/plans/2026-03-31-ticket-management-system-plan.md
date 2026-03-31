# Ticket Management System Implementation Plan

> **For Antigravity:** REQUIRED WORKFLOW: Use `.agent/workflows/execute-plan.md` to execute this plan in single-flow mode.

**Goal:** Build a simple, decoupled Ticket Management System.

**Architecture:** A standalone Angular frontend and a RESTful Spring Boot backend utilizing MySQL for persistence.

**Tech Stack:** Java 17+, Spring Boot, MySQL, Angular, HTML5, Vanilla CSS.

---

### Task 1: Initialize Spring Boot Backend

**Step 1: Scaffold the project**
Run the Spring Initializr command to generate the base project.
```bash
# Note: we will use spring init or curl to generate a standard project with web, data-jpa, and mysql dependencies.
```

**Step 2: Configure MySQL properties**
Modify `src/main/resources/application.properties` with the baseline DB configuration.
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ticketdb
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

**Step 3: Commit**
```bash
git add .
git commit -m "chore: initialize spring boot backend"
```

### Task 2: Backend - TicketEntity & Repository (TDD)

**Files:**
- Create: `backend/src/main/java/com/ticketmgmt/TicketEntity.java`
- Create: `backend/src/main/java/com/ticketmgmt/TicketRepository.java`
- Test: `backend/src/test/java/com/ticketmgmt/TicketRepositoryTest.java`

**Step 1: Write the failing test**
Create `TicketRepositoryTest.java` that tests saving a ticket.

**Step 2: Run test to verify it fails**
Run test suite. Expected: compilation failure or missing entity.

**Step 3: Write minimal implementation**
Create `TicketEntity` with fields `id`, `name`, `description`, `status`.
Create `TicketRepository` extending `JpaRepository`.

**Step 4: Run test to verify it passes**
Run test. Expected: PASS.

**Step 5: Commit**
```bash
git add backend/
git commit -m "feat: add TicketEntity and Repository"
```

### Task 3: Backend - TicketController (TDD)

**Files:**
- Create: `backend/src/main/java/com/ticketmgmt/TicketController.java`
- Test: `backend/src/test/java/com/ticketmgmt/TicketControllerTest.java`

**Step 1: Write the failing test**
Create `TicketControllerTest.java` using `@WebMvcTest` to test `GET /api/v1/tickets`.

**Step 2: Run test to verify it fails**
Run tests expecting 404 or missing class.

**Step 3: Write minimal implementation**
Implement the Controller with endpoints for GET all, POST create, and GET search.

**Step 4: Run tests**
Ensure all Controller tests pass.

**Step 5: Commit**
```bash
git add backend/
git commit -m "feat: add TicketController REST API"
```

### Task 4: Initialize Angular Frontend

**Step 1: Scaffold Angular App**
```bash
npx -y @angular/cli new frontend --routing=false --style=css --interactive=false
```

**Step 2: Commit**
```bash
git add frontend/
git commit -m "chore: initialize angular frontend"
```

### Task 5: Frontend - Services and Dashboard Component

**Files:**
- Create: `frontend/src/app/ticket.service.ts`
- Create: `frontend/src/app/dashboard/dashboard.component.ts`

**Step 1: Write minimal test**
Add a failing unit test in `ticket.service.spec.ts` for fetching tickets.

**Step 2: Implement Service**
Implement `TicketService` using `HttpClient` to call `http://localhost:8080/api/v1/tickets`.

**Step 3: Implement component**
Add table markup in `dashboard.component.html` and logic in `dashboard.component.ts`.

**Step 4: Test & Commit**
```bash
git add frontend/
git commit -m "feat: add frontend service and dashboard"
```

*(Additional tasks for Create and Search components will follow the same pattern during execution flow.)*
