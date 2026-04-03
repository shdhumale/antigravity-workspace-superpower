# Performance Enhancement Implementation Plan

> **For Antigravity:** REQUIRED WORKFLOW: Use `.agent/workflows/execute-plan.md` to execute this plan in single-flow mode.

**Goal:** Implement pagination and caching to improve system scalability and user experience.

**Architecture:** 
- Backend: REST endpoints returns `Page<TicketEntity>`. Caching uses Spring's `ConcurrentMapCacheManager`.
- Frontend: `DashboardComponent` manages the current page state and uses a `Subject` for debounce controls.

**Tech Stack:** Spring Boot 3.x, Angular 17+ (Standalone), Spring Data JPA.

---

### Task 1: Enable Backend Caching

**Files:**
- Modify: `backend/src/main/java/com/ticketmgmt/BackendApplication.java`

**Step 1: Write the failing test**
(Skipped for configuration enabling - verified via application startup)

**Step 2: Enable Caching annotation**
Add `@EnableCaching` to the main class.

**Step 3: Run the application**
Run: `./mvnw spring-boot:run`
Expected: Application starts without errors.

**Step 4: Commit**
```bash
git add backend/src/main/java/com/ticketmgmt/BackendApplication.java
git commit -m "perf: enable spring boot caching"
```

---

### Task 2: Implement Pagination in Backend Controller

**Files:**
- Modify: `backend/src/main/java/com/ticketmgmt/controller/TicketController.java`

**Step 1: Write the failing test**
Update tests to check for `Page` response structure.

**Step 2: Update Controller method**
Update `getAllTickets` and `searchTickets` to accept `Pageable`.

**Step 3: Run tests**
Run: `./mvnw test`
Expected: PASS

**Step 4: Commit**
```bash
git add backend/src/main/java/com/ticketmgmt/controller/TicketController.java
git commit -m "perf: add pagination to ticket endpoints"
```

---

### Task 3: Implement Result Caching

**Files:**
- Modify: `backend/src/main/java/com/ticketmgmt/controller/TicketController.java`

**Step 1: Add Cache annotations**
Add `@Cacheable` to getters and `@CacheEvict` to modification methods.

**Step 2: Verify caching manually**
Check logs for database hits on repeated calls.

**Step 3: Commit**
```bash
git add backend/src/main/java/com/ticketmgmt/controller/TicketController.java
git commit -m "perf: add @Cacheable and @CacheEvict to controller"
```

---

### Task 4: Update Frontend Ticket Service

**Files:**
- Modify: `frontend/src/app/ticket.service.ts`

**Step 1: Update type definitions**
Update the service to return `Observable<PaginatedResponse<Ticket>>`.

**Step 2: Commit**
```bash
git add frontend/src/app/ticket.service.ts
git commit -m "perf: update frontend service for paginated responses"
```

---

### Task 5: Implement Search Debouncing

**Files:**
- Modify: `frontend/src/app/dashboard/dashboard.component.ts`
- Modify: `frontend/src/app/dashboard/dashboard.component.html`

**Step 1: Implement Subject and Pipe**
Use `Subject` for search input with `debounceTime(300)`.

**Step 2: Update HTML**
Bind search input to the Subject.

**Step 3: Commit**
```bash
git add frontend/src/app/dashboard/dashboard.component.ts frontend/src/app/dashboard/dashboard.component.html
git commit -m "perf: implement search debouncing"
```

---

### Task 6: Implement Pagination UI

**Files:**
- Modify: `frontend/src/app/dashboard/dashboard.component.ts`
- Modify: `frontend/src/app/dashboard/dashboard.component.html`

**Step 1: Add Pagination state and controls**
Add `currentPage`, `totalPages` and `goToPage()` method.

**Step 2: Add UI Buttons**
Add Previous/Next buttons in the template.

**Step 3: Commit**
```bash
git add frontend/src/app/dashboard/dashboard.component.ts frontend/src/app/dashboard/dashboard.component.html
git commit -m "perf: implement pagination controls in dashboard"
```
