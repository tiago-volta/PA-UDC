# Advanced Programming (PA) — PA Cinema

![Java](https://img.shields.io/badge/Backend-Java%20%7C%20Spring%20Boot-007396?style=flat-square&logo=openjdk&logoColor=white)
![React](https://img.shields.io/badge/Frontend-React%20%7C%20Vite-61DAFB?style=flat-square&logo=react&logoColor=black)
![REST](https://img.shields.io/badge/API-RESTful%20Services-34A853?style=flat-square)
![Database](https://img.shields.io/badge/Database-MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)
![E2E](https://img.shields.io/badge/E2E-Selenium%204-43B02A?style=flat-square&logo=selenium&logoColor=white)
![Score](https://img.shields.io/badge/Project%20Score-8.9%2F10-success?style=flat-square)
![Tutored](https://img.shields.io/badge/Tutored%20Work-10%2F10-lightgrey?style=flat-square)

## 🎯 Overview

This repository contains the coursework project for the **Advanced Programming (PA)** subject at the **Universidade da Coruña (UDC)**.

**PA Cinema** is a full-stack web application for browsing movie sessions, purchasing tickets, and managing ticket delivery. The system is split into three main parts:

- A **Spring Boot REST API** with JWT authentication and role-based access (`backend`)
- A **React single-page application** with Redux, React Router, and internationalization (`frontend`)
- An **end-to-end (E2E) test suite** using Selenium WebDriver and JUnit 5 (`e2e-tests`)

The project emphasizes **layered architecture**, **RESTful service design**, **form validation**, and **automated UI testing**.


## 🛠️ Technical Highlights

### 🔧 Backend (Spring Boot)

**Location:** `backend/`

- **Java 21** + **Maven** + **Spring Boot 3** (Web, Data JPA, Security, Validation).
- **MySQL** persistence with schema and seed data in:
  - `backend/src/sql/1-MySQLCreateTables.sql`
  - `backend/src/sql/2-MySQLCreateData.sql`
- **JWT-based authentication** with two roles: `SPECTATOR` and `TICKET_SELLER`.
- REST controllers for users, catalog (billboard, movies, sessions), purchases, and ticket delivery.
- Unit and integration tests under `backend/src/test/`.

### 🎨 Frontend (React)

**Location:** `frontend/`

- **React 19** + **Vite** + **Redux Toolkit** + **React Bootstrap**.
- **react-intl** for Spanish, Galician, and English messages.
- Feature modules: `app`, `users`, `catalog`, `shopping`, `common`.
- Environment-based API URL via `.env.development` / `.env.production` (`VITE_BACKEND_URL`).

### 🧪 E2E Tests (Selenium)

**Location:** `e2e-tests/`

- **Selenium WebDriver 4.39.0** + **JUnit 5** + **Java 21**.
- Automated browser tests in `AppTest.java`:
  - Login (`testLogin`)
  - Session details (`testSessionDetails`)
  - Ticket purchase (`testBuyTickets`)
  - Ticket delivery (`testDeliverTickets`)
- Runs against `http://localhost:5173` with the backend on port **8080**.

## 📂 Repository Structure

```text
pa-02/
├── backend/                      # Spring Boot REST API
│   └── src/
│       ├── main/java/            # Application, model, services, REST layer
│       ├── main/resources/       # application.yml, i18n
│       └── sql/                  # MySQL DDL and seed data
├── frontend/                     # React SPA (Vite)
│   └── src/
│       ├── backend/              # API client (appFetch, services)
│       ├── modules/              # Feature modules (catalog, shopping, users…)
│       └── i18n/                 # Locale message bundles
└── e2e-tests/                    # Selenium E2E test project (Maven)
    └── src/test/java/            # AppTest and browser automation
```

## 🧱 Development Environment

The project assumes:

- **JDK 21**
- **Maven 3.x**
- **Node.js** (LTS) and **npm**
- **MySQL** server with a `paproject` database (created by the SQL scripts)
- **Google Chrome** + compatible **ChromeDriver** (for E2E tests)

Configure database credentials in `backend/pom.xml` (Maven profiles / `dataSource` properties) to match your local MySQL setup.

## 🗄️ Initializing the Database & Running the Backend

From the `backend` directory:

```bash
cd backend
mvn sql:execute spring-boot:run
```

This will:

- Execute the SQL scripts to (re)create tables and load seed data.
- Start the Spring Boot application on port **8080** (default).

## 🚀 Running the Frontend

In a separate terminal:

```bash
cd frontend
npm install
npm run dev
```

The development server typically listens on **http://localhost:5173**.

Ensure `frontend/.env.development` points to the backend:

```text
VITE_BACKEND_URL=http://localhost:8080
```

## 🧪 Running E2E Tests

With **backend** and **frontend** already running:

```bash
cd e2e-tests
mvn test
```

To run a single test:

```bash
mvn test -Dtest=AppTest#testBuyTickets
```

## 👤 Test Users (seed data)

| Username         | Password | Role            |
|------------------|----------|-----------------|
| `viewer`         | `pa2526` | Spectator       |
| `ticketseller`   | `pa2526` | Ticket seller   |
| `testviewer`     | `pa2526` | Spectator (E2E) |
| `testticketseller` | `pa2526` | Ticket seller (E2E) |

## 📌 Main Features

- User registration, login, profile update, and password change
- Movie billboard with date filter and session listing
- Session details and ticket purchase (spectators)
- Purchase history (spectators)
- Ticket delivery by purchase ID and card (ticket sellers)
- Internationalized UI (ES / GL / EN)

## 📄 License

Academic coursework project — Universidade da Coruña (UDC).
