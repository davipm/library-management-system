# 📚 Library Management System

A comprehensive and secure RESTful API and a modern web interface for managing library resources, including genres, authors, and books. This system features robust JWT authentication and role-based access control to ensure data integrity and secure operations.

## ✨ Features

-   **Complete CRUD Operations**: Manage genres, authors, and books with full Create, Read, Update, and Delete capabilities.
-   **JWT-based Authentication**: Secure user access with JSON Web Tokens, ensuring only authorized users can perform actions.
-   **Role-Based Access Control**: Implement different access levels for users (e.g., admin, librarian, user) to control permissions.
-   **API Versioning**: Organized API endpoints with versioning (e.g., `/api/v1/`) for future scalability and maintainability.
-   **Swagger/OpenAPI Documentation**: Easily explore and interact with the API using built-in Swagger UI documentation.
-   **Docker Containerization**: Simplified deployment and environment consistency using Docker and Docker Compose.
-   **PostgreSQL Database**: Reliable and persistent data storage with a PostgreSQL relational database.
-   **Comprehensive Testing**: Ensures stability and correctness with a suite of unit and integration tests.
-   **Multi-environment Configuration**: Flexible configuration for development, testing, and production environments.

## 🚀 Technologies Used

### Backend
-   **Java 17**: The core programming language.
-   **Spring Boot 3.1**: Framework for building robust, stand-alone, production-grade Spring applications.
-   **Spring Security**: Provides authentication, authorization, and protection against common attacks.
-   **Spring Data JPA**: Simplifies data access with Java Persistence API.
-   **PostgreSQL**: Powerful, open-source relational database system.
-   **JWT (jjwt)**: For secure token-based authentication.
-   **Swagger/OpenAPI (springdoc-openapi)**: For automatic API documentation.
-   **Maven**: Dependency management and build automation tool.
-   **JUnit 5**: Testing framework for Java applications.

### Frontend
-   **Vue.js 3**: Progressive JavaScript framework for building user interfaces.
-   **Pinia**: Intuitive, type-safe, and modular store for Vue.js.
-   **Vue Router**: Official routing library for Vue.js.
-   **Axios**: Promise-based HTTP client for the browser and Node.js.
-   **Tailwind CSS**: A utility-first CSS framework for rapid UI development.
-   **Vite**: Next-generation frontend tooling for a fast development experience.
-   **TypeScript**: Superset of JavaScript that adds static types.

## ⚙️ Prerequisites

Before you begin, ensure you have the following installed:

-   **Java Development Kit (JDK) 17 or higher**
-   **Apache Maven 3.8 or higher**
-   **Docker and Docker Compose** (Recommended for easy setup)
-   **Node.js (LTS version, e.g., 20.x) and npm/yarn** (for frontend development)
-   **PostgreSQL** (if running the backend locally without Docker)

## ▶️ Getting Started

### Using Docker (Recommended for quick setup)

The easiest way to get the entire system up and running is by using Docker Compose.

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd library-management-system
    ```

2.  **Build and run the Docker containers:**
    This command will build the Docker images for both the backend and frontend, set up the PostgreSQL database, and start all services.
    ```bash
    docker-compose up --build
    ```
    The backend API will be available at `http://localhost:8080`.
    The frontend application will be available at `http://localhost:5173`.

3.  **Access Swagger UI:**
    Once the backend is running, you can access the API documentation at `http://localhost:8080/swagger-ui.html`.

4.  **Initial User (for testing):**
    The system will automatically create an initial admin user with the following credentials:
    -   **Username:** `admin`
    -   **Password:** `password`
    You can use these credentials to log in and test the API functionalities.

### Running Locally (Backend)

If you prefer to run the backend application directly on your machine:

1.  **Ensure PostgreSQL is running:**
    Make sure you have a PostgreSQL instance running and configured. Update `src/main/resources/application.properties` with your database connection details if necessary.

2.  **Navigate to the project root:**
    ```bash
    cd library-management-system
    ```

3.  **Build the backend application:**
    ```bash
    mvn clean install
    ```

4.  **Run the backend application:**
    ```bash
    java -jar target/library-management-system-1.0.0.jar
    ```
    The backend API will be available at `http://localhost:8080`.

### Running Locally (Frontend)

To run the frontend application locally:

1.  **Navigate to the frontend directory:**
    ```bash
    cd library-management-system/frontend
    ```

2.  **Install dependencies:**
    ```bash
    npm install
    # or yarn install
    ```

3.  **Run the development server:**
    ```bash
    npm run dev
    # or yarn dev
    ```
    The frontend application will be available at `http://localhost:3000`.

4.  **Build for production (optional):**
    ```bash
    npm run build
    # or yarn build
    ```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a pull request or open an issue.

## 📄 License

This project is licensed under the MIT License. See the `LICENSE` file for details.
