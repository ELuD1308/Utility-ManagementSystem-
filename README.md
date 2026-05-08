# Utility International Backend System

## Project Overview

Utility International is a Spring Boot backend system designed to provide customers with a self-service utility management platform.

The system enables customers to:
- View current and historical bills
- Pay utility bills online
- Dispute incorrect bills
- Change utility tariff plans
- Receive future notifications and reminders

The backend system also supports:
- External billing system integration
- JMS-based payment processing
- Role-based authentication and authorization
- Call center dispute management

---

# Technology Stack

## Backend Framework
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security

## Database
- MySQL

## Messaging / Integration
- ActiveMQ (JMS)
- REST APIs

## Documentation
- Swagger / OpenAPI

## Build Tool
- Maven

## Java Version
- Java 25

---

# Project Architecture

The application follows a layered Spring Boot architecture.

## Layers

### 1. Controller Layer
Handles incoming HTTP requests and exposes REST APIs.

### 2. Service Layer
Contains business logic and application workflows.

### 3. Repository Layer
Handles database access using Spring Data JPA.

### 4. Entity Layer
Represents database models and relationships.

### 5. Integration Layer
Handles communication with external systems such as:
- Billing services
- JMS payment systems
- Notification systems

---

# Core Modules

## Authentication Module
Responsible for:
- Login
- User authentication
- Role-based access control

## Customer Module
Responsible for:
- Customer operations
- Customer profile management
- Customer account handling

## Billing Module
Responsible for:
- Bill retrieval
- Bill management
- Bill calculations

## Payment Module
Responsible for:
- Payment processing
- JMS payment integration
- Payment status updates

## Dispute Module
Responsible for:
- Bill dispute submission
- Dispute tracking
- Dispute resolution workflows

## Tariff Module
Responsible for:
- Utility plan management
- Tariff upgrades/downgrades

## Notification Module
Responsible for:
- Future SMS notifications
- Email notifications
- Payment reminders

## Integration Module
Responsible for:
- External billing system integration
- JMS communication
- External API communication

---

# Package Structure

```text
com.utilityinternational

├── config
├── controller
├── service
├── repository
├── entity
├── dto
├── security
├── integration
├── exception
├── util
└── notification