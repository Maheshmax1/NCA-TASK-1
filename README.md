# Person API – Spring Boot

## 📌 Project Overview

The Person API is a RESTful backend application developed using Java, Spring Boot, Spring Data JPA, and MySQL.

It allows users to register, log in, and manage person details through REST API endpoints. JWT authentication and role-based authorization are used to secure the application.

## 🚀 Technologies Used

- **Java** – Backend programming language
- **Spring Boot** – Backend application framework
- **Spring Web** – REST API development
- **Spring Data JPA** – Database operations
- **MySQL** – Relational database
- **Spring Security** – Authentication and authorization
- **JWT (JSON Web Token)** – Token-based authentication
- **BCrypt** – Password hashing
- **Bruno** – API testing
- **Maven** – Dependency management and build tool

## 📂 Project Structure

```text
demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ncaHelloWorld/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── entity/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       ├── service/
│   │   │       └── DemoApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── .gitignore
├── pom.xml
└── README.md
```

## ✨ Features

- Person registration
- User login
- JWT token generation and validation
- Role-based access control for USER and ADMIN
- Password hashing using BCrypt
- Create, read, update, and delete person records
- Duplicate email validation during registration
- REST API testing using Bruno

## 🔐 Authentication and Authorization

The application uses JWT-based authentication.

1. A user registers with the required details.
2. The user logs in with their email and password.
3. After successful authentication, the application generates a JWT.
4. The client sends the token in the Authorization header for protected requests.
5. The application validates the token and checks the user's permissions.

### Authorization Rules

- **USER:** Can access their own person details when the endpoint's authorization rules enforce ownership.
- **ADMIN:** Can access all person records when permitted by the security configuration.
- **Unauthenticated requests:** Protected endpoints reject requests without valid authentication.

## 📡 API Endpoints

Base path: `/api/v1`

### Authentication APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Log in and receive a JWT |

### Person APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/addPerson` | Add a person |
| GET | `/getAllPersons` | Retrieve all persons |
| GET | `/getSinglePerson/{name}` | Retrieve a person by name |
| PUT | `/updatePerson/{name}` | Update a person's details |
| DELETE | `/deletePerson/{name}` | Delete a person |

Access to these endpoints depends on the configured authentication and authorization rules.

## ⚙️ Setup and Installation

### Prerequisites

- Java JDK
- Maven or the included Maven Wrapper
- MySQL Server
- IntelliJ IDEA or another Java IDE
- Bruno or Postman for API testing

### 1. Clone the Repository

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
cd demo
```

Replace `YOUR_GITHUB_REPOSITORY_URL` with your actual GitHub repository URL.

### 2. Create the Database

Open MySQL and execute:

```sql
CREATE DATABASE nca_person_db;
```

### 3. Configure Database Connection

Update `src/main/resources/application.properties` with your local database settings.

Configure the MySQL URL, username, password, and any required JWT settings according to your application.

**Security:** Do not commit actual database passwords, JWT secrets, or other credentials to GitHub.

### 4. Run the Application

On Windows, run:

```bash
mvnw.cmd spring-boot:run
```

Alternatively, run `DemoApplication.java` from IntelliJ IDEA.

## 🧪 API Testing

Use Bruno to test the authentication and person APIs.

For protected endpoints, include the JWT in the request header:

```http
Authorization: Bearer YOUR_JWT_TOKEN
```

Verify successful responses, validation errors, authentication failures, and authorization restrictions.

## 👨‍💻 Author

**Mahesh Mariappan**

GitHub: [Maheshmax1](https://github.com/Maheshmax1)

LinkedIn: [Mahesh Mariappan](https://www.linkedin.com/in/mahesh-mariappan-740377397/)

---

*This project was developed for learning and practicing REST API development, database integration, JWT authentication, and role-based authorization using Spring Boot.*
