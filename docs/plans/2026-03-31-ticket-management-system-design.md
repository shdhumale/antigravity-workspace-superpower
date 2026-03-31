# Ticket Management System - Design Document

**Date:** March 31, 2026

## 1. Executive Summary
This document outlines the architectural design for a Ticket Management System, combining the original PRD's business requirements (Dashboard, Create Ticket, Search) with a modern Full Stack stack: Angular for the frontend UI, Spring Boot for the backend API, and MySQL for data persistence.

## 2. Architecture
We will use a **Decoupled Client-Server Architecture**.
- **Frontend:** Angular CLI project, acting purely as an interactive SPA (Single Page Application) that consumes backend REST APIs.
- **Backend:** Spring Boot application exposing RESTful APIs, connected to a MySQL database using Spring Data JPA.
- Both projects will be maintained in separate directories within the root workspace.

## 3. Data Model & Backend Components
- **TicketEntity (MySQL Table: tickets)**:
  - `id`: Long (Primary Key, Auto-increment)
  - `name`: String
  - `description`: Text
  - `status`: Enum String ('NEW', 'ASSIGNED', 'DONE', 'ESCALATE')
- **TicketRepository**: Extends `JpaRepository<TicketEntity, Long>`, providing built-in CRUD and custom search (`findByNameContainingOrDescriptionContaining`).
- **TicketService**: Core business logic wrapping the repository.
- **TicketController**: Exposes endpoints:
  - `GET /api/v1/tickets`: Retrieve all tickets for the dashboard.
  - `POST /api/v1/tickets`: Create a new ticket.
  - `GET /api/v1/tickets/search?query={string}`: Fetch tickets matching the query string.

## 4. Frontend Components (Angular)
- **TicketService**: Handles all `HttpClient` calls to the backend APIs.
- **DashboardComponent**: Reads `GET /api/v1/tickets` and renders an HTML5 table.
- **CreateTicketComponent**: Displays a form with input validation, submitting via `POST /api/v1/tickets`.
- **SearchComponent**: Contains a search bar, triggering `GET /api/v1/tickets/search` and rendering dynamic results.
- **Styling**: Minimalist and clean Vanilla CSS (no Tailwind per strict instructions unless asked) to handle layout, typography, and borders.

## 5. Data Flow
1. **Load Dashboard:** Angular calls `GET /api/v1/tickets` on init, rendering the response into the UI.
2. **Submit Create Form:** Angular constructs the JSON payload and sends `POST /api/v1/tickets`. The backend validates, persists to MySQL, and responds with `201 Created`. The UI refreshes with the new ticket.
3. **Search Execution:** Angular sends the query to `GET /api/v1/tickets/search?query=`, the backend executes `LIKE` SQL queries on the DB, returning filtered JSON.

## 6. Error Handling
- **Backend**: Uses `@ControllerAdvice` to catch validation constraint violations or database errors, returning structured `{ "error": "...", "status": 400 }` JSON payloads.
- **Frontend**: Wraps `HttpClient` requests with `catchError`, pushing user-friendly minimalist alerts to the UI instead of failing silently.

## 7. Testing Strategy
- **Backend**: JUnit 5 and Mockito for unit testing the Service layer. Integration tests using an embedded DB or Testcontainers for Data and Web-MVC layers.
- **Frontend**: Jasmine / Karma tests for basic component rendering and mocking `HttpClient` behaviors.
- TDD approach will be adopted, prioritizing test creation before implementation logic.
