# Performance Enhancement Design - Ticket Management System

**Date**: 2026-04-03
**Status**: Approved

## 1. Overview
The goal is to enhance the performance of the Ticket Management System by reducing database load, minimizing data transfer between backend and frontend, and improving user experience during searches and browsing.

## 2. Proposed Changes

### 2.1 Backend (Spring Boot)

#### 2.1.1 Pagination and Sorting
- **Endpoints**: `/api/v1/tickets` and `/api/v1/tickets/search`
- **Implementation**: 
    - Accept `Pageable` parameters (default: page=0, size=10).
    - Return `Page<TicketEntity>` instead of `List<TicketEntity>`.
- **Reasoning**: Reduces memory usage and response payload size for large ticket sets.

#### 2.1.2 Caching Strategy
- **Mechanism**: Spring Boot Default Cache Manager (In-memory, `ConcurrentHashMap`).
- **Annotations**:
    - `@EnableCaching`: On the main application class.
    - `@Cacheable(value = "tickets", key = "'page_' + #pageable.pageNumber + '_size_' + #pageable.pageSize")`: On fetch methods.
    - `@CacheEvict(value = "tickets", allEntries = true)`: On create, update, and delete methods.
- **Reasoning**: Significantly speeds up repeated searches and page re-visits by bypassing the database.

### 2.2 Frontend (Angular)

#### 2.2.1 Pagination Controls
- **Component**: `DashboardComponent`
- **Fields**: `currentPage: number = 0`, `totalPages: number = 0`, `pageSize: number = 10`.
- **UI**: Added "Previous" and "Next" buttons at the bottom of the ticket list.
- **Reasoning**: Allows users to navigate large data sets comfortably without loading everything at once.

#### 2.2.2 Search Debouncing
- **Implementation**: Use a `Subject<string>` for the search input.
- **Operator**: `.pipe(debounceTime(300), distinctUntilChanged(), switchMap(...))`
- **Reasoning**: Prevents rapid-fire API calls for every keystroke, reducing network overhead and backend processing.

## 3. Data Flow
1. **Search**: 
    `Input Keystroke` -> `Subject.next()` -> `Search Service` (Wait 300ms) -> `Spring Controller` (Cache Check) -> `Database` (if cache miss) -> `Result`.
2. **Browse**: 
    `Page Click` -> `Subject.next()` -> `Search Service` -> `Spring Controller` (Check Cache) -> `Database` (if cache miss) -> `Result`.

## 4. Testing Plan
- **Unit Tests**:
    - Backend: Verify `TicketController` returns 10 records by default.
    - Backend: Verify cache eviction works by performing a save and then a reload.
- **Integration Tests**:
    - End-to-end: Search for a keyword and check that only one API request is made after the debounce period.
- **Manual Verification**: 
    - Check the browser "Network" tab to confirm page sizes and debounce timing.

## 5. Success Criteria
- Time-to-first-byte (TTFB) for repeated fetches reduced by 50%+.
- Large datasets (1k+ records) render smoothly on the frontend via pagination.
- Only one API search request is sent per logical search query.
