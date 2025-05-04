# Library_management_system

# Library Management System

This is a simple **Library Management System** built with **Spring Boot**. The application allows users to manage books in the library. It includes operations like adding, updating, deleting, and retrieving books. The project is secured with **Spring Security** and provides **RESTful APIs** for interacting with the system.

## Features

- **CRUD Operations** for books:
  - Add a new book
  - Retrieve all books or a specific book by ID
  - Update book details
  - Delete a book
- **Spring Security** for API security using **Basic Authentication**  
- ** Login id and password for Basic Authentication is username : admin   and password: admin for PostMan and Swagger 
- **Swagger** integration for API documentation
- **MySQL** as the database for storing book data
- **Validation** for incoming data using 

## Technologies Used

- **Spring Boot 3.x**
- **Spring Security** (for Basic Authentication)
- **Spring Data JPA** (for database interaction)
- **MySQL** (as the database)
- **Hibernate Validator** (for validation)
- **Swagger** (for API documentation)
- **JUnit 5** and **Mockito** (for unit testing)
- **H2 Database** (for testing)

## Prerequisites

- Java 17
- MySQL Database
- Maven
- PostMan


## database Connection
- Go to application.properties and set the password, username, and URL based on your setup

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/panchanidivu/Library_management_system-.git
cd library-management


## swagger url

http://localhost:8080/swagger-ui/index.html#/
