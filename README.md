# FlexCart

## 📌 Overview
This project is a backend-driven application designed to handle core business logic and data processing.  
At its current stage, the focus is on **functional correctness and logical access control**, rather than full authentication security.

---

## 🔐 Security & Access Control (Important)
⚠️ **JWT-based authentication is NOT implemented yet.**

- There is **no JWT / token-based security** at this time.
- All access restrictions are enforced using **pure backend logic** (conditional checks, role-based logic, and validations inside the service/controller layer).
- Endpoints may rely on:
  - Request parameters
  - User identifiers passed from the client
  - Backend validation logic

This approach is intentional for the current development phase and may change in future updates.

---

## 🧠 Current Restriction Logic
- Authorization decisions are handled directly in the backend code.
- Business rules determine what actions are allowed or denied.
- No external authentication provider or security framework is used yet.

---

## 🚧 Planned Improvements
Future versions of this project may include:
- JWT-based authentication
- Spring Security (or equivalent security framework)
- Role-based and permission-based access control
- Token validation and request filtering

---

## ⚠️ Disclaimer
This project **should not be considered production-ready** from a security standpoint until proper authentication and authorization mechanisms are implemented.

---

## 🛠️ Tech Stack
- Backend: Java / Spring Boot
- Database: Spring Boot

---

## 📂 Project Status
🟡 **In Development**  
Security features are planned but not yet integrated.

---

## 👤 Author
Jay Emmanuel Sandoval
