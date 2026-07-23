# 🔐 JWT Refresh Token & Blacklist

A secure authentication system built with **Spring Boot** that demonstrates how modern applications manage **JWT Access Tokens**, **Refresh Tokens**, **Token Rotation**, **Logout**, and **Access Token Blacklisting**.

This project focuses on implementing a production-inspired refresh token flow while following clean architecture and Spring Security best practices.

---

## 🚀 Features

### Authentication & Authorization
- User Registration
- User Login
- JWT Access Token Authentication
- Role-Based Access Control (RBAC)
- Spring Security Integration

### Refresh Token
- Secure Refresh Token generation using `SecureRandom`
- Refresh Tokens stored as **hashed values**
- Refresh Token Rotation
- Multiple active Refresh Tokens per user
- Refresh Token Revocation on Logout
- Refresh Token Expiration

### Access Token Blacklisting
- Database-backed Access Token Blacklist
- Logout invalidates the current Access Token
- Refresh Token Rotation blacklists the previous Access Token
- Automatic cleanup of expired blacklisted tokens using Spring Scheduler

### Task Management API
- Create Task
- Get All Tasks
- Search Task by Name
- Update Task
- Delete Task

### Resource Ownership
- Users can only access their own tasks
- Users cannot update another user's tasks
- Users cannot delete another user's tasks

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT (JJWT)
- MySQL
- Docker

---

## 📂 Project Structure

```
src
├── auth
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   ├── security
│   └── service
│
├── task
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
│
└── common
    ├── constants
    ├── enums
    ├── exception
    └── response
```

---

## 🔄 Authentication Flow

```text
Register
      │
      ▼
Login
      │
      ▼
Access Token + Refresh Token
      │
      ▼
Access Protected APIs
      │
      ▼
Access Token Expired
      │
      ▼
Refresh Token Endpoint
      │
      ▼
Old Refresh Token Revoked
      │
      ▼
Old Access Token Blacklisted
      │
      ▼
New Access Token
+
New Refresh Token
```

---

## 🔐 Refresh Token Lifecycle

- A new Refresh Token is generated during login.
- Refresh Tokens are stored in the database as **hashed values**.
- Every Refresh Token has:
  - Created Time
  - Last Used Time
  - Expiration Time
  - Revoked Status
- During refresh:
  - The old Refresh Token is revoked.
  - A new Refresh Token is issued.
  - The previous Access Token is blacklisted (if still valid).

---

## 🚪 Logout Flow

When a user logs out:

- Refresh Token is revoked.
- Current Access Token is added to the blacklist.
- Blacklisted tokens remain valid only until their expiration time.
- A scheduled job automatically removes expired blacklisted tokens from the database.

---

## 🔒 Security Features

- BCrypt Password Hashing
- Stateless Authentication
- JWT Access Tokens
- Secure Refresh Tokens
- Refresh Token Rotation
- Token Revocation
- Access Token Blacklisting
- Role-Based Access Control (RBAC)
- Resource Ownership Validation

---

## 📌 Future Improvements

- Redis-based Access Token Blacklist
- Device-based Session Management
- Email Verification
- Password Reset
- Audit Logging
- API Documentation with Swagger/OpenAPI
- Integration Tests
- Rate Limiting

---

## 📖 What I Learned

Through this project, I gained a deeper understanding of:

- JWT Authentication
- Refresh Token Lifecycle
- Refresh Token Rotation
- Token Revocation
- Logout in Stateless Authentication
- Access Token Blacklisting
- Spring Security
- Role-Based Access Control (RBAC)
- Secure REST API Design

---

## 👨‍💻 Author

**Sakibul Hasan Sakib**

Backend Developer | Java & Spring Boot Enthusiast

If you have any suggestions or feedback, feel free to reach out or open an issue.
