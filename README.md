# FlexCart

A full-stack e-commerce platform with a multi-seller product catalog, shopping cart, and order management — built with a React web frontend and a Spring Boot REST API backend.

## Overview

FlexCart models the full retail lifecycle: client (customer/seller) accounts and roles, a product catalog with categories, images, inventory tracking and discounts, a persistent shopping cart, and order processing through payment, shipment, and refunds. Product reviews are also supported, tied to a client and a product.

Any client account can act as a buyer (owns a cart, places orders, leaves reviews) and/or a seller (lists products) — there's no separate seller-only account type.

## Tech Stack

| Layer | Technology |
|---|---|
| Frontend | React (single-page web application) |
| Backend | Java, Spring Boot (Spring Web MVC) |
| Persistence | Spring Data JPA / Hibernate |
| Database | MySQL |
| Validation | Jakarta Bean Validation, handled via `@RestControllerAdvice` |
| Build tool | Maven |
| Boilerplate reduction | Lombok |
| Dev tooling | Spring Boot DevTools (hot reload) |

## Architecture

FlexCart follows a conventional layered architecture: Controllers expose REST endpoints and are annotated per-class with `@CrossOrigin` for browser access, Services (each with an interface + implementation pair) hold business logic, DTO Mapper classes convert between entities and Request/Response DTOs, and Spring Data JPA repository interfaces perform persistence. A single `ValidationExceptionHandler` (`@RestControllerAdvice`) centralizes bean-validation error formatting.

![FlexCart architecture](/FlexCart%20Documentation%20Package/docs/images/architecture.png)

*Layered architecture — see [Database Design](/FlexCart%20Documentation%20Package/docs/database-design.md) for request-flow detail.*

## Core Modules

- **Client accounts** — CRUD for customer/seller accounts, with one or more roles per client.
- **Product catalog** — products linked to a selling client, each with its own images, categories, inventory history, reviews, and discount links.
- **Discounts** — campaigns (percentage, fixed, buy-x-get-y, bulk, promotional, and more) linked to products via a join table.
- **Cart** — each client has exactly one cart; endpoints add, update, and remove line items.
- **Orders** — creation from cart contents, order history, and cancellation, tracking payment status and method.
- **Payments** — payment attempts recorded against an order.
- **Refunds** — refund requests tracked against an order.
- **Reviews** — client ratings and comments per product.

## Documentation

Full technical reference lives in `/docs`:

- 📘 **[API Reference](/FlexCart%20Documentation%20Package/docs/api-reference.md)** — every REST endpoint, grouped by module
- 🗄️ **[Database Design](/FlexCart%20Documentation%20Package/docs/database-design.md)** — entity-relationship diagram and table-by-table schema notes

## Known Issues & Roadmap

This project doesn't currently have authentication/authorization wired in — every endpoint is open. Planned/in-progress hardening:

- [ ] Add Spring Security and gate mutating endpoints behind authentication, with role checks (ADMIN/USER/SELLER)
- [ ] Hash `client.password` (currently stored as plain text) before persisting; exclude password from response DTOs
- [ ] Fix `CartController`'s CORS origin (currently pointed at the wrong port)
- [ ] Fix `deleteClient(@PathVariable Client id)` — should bind a `Long id`, not the entity type
- [ ] Move datasource credentials to environment variables instead of `application.properties`
- [ ] Replace `ddl-auto=update` with a versioned migration tool (Flyway/Liquibase)
- [ ] Add test coverage (MockMvc + `@DataJpaTest`) — currently only a context-load smoke test exists

## Getting Started

```bash
# clone and build the backend
mvn clean install

# run the backend (defaults to port 8090)
mvn spring-boot:run
```

Configure your local MySQL connection in `src/main/resources/application.properties` before running. The frontend (React) lives in a separate directory/repo and talks to the API at `http://localhost:8090/api/v1`.

## License

This is a personal / portfolio project. No license has been chosen yet — all rights reserved unless stated otherwise.
