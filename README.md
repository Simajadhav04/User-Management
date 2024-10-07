### **Spring Boot & MySQL User Management System**

This **User Management System** is a web application built using **Spring Boot** and **MySQL** that offers a secure and efficient way to manage users and their roles. The system is designed to handle user authentication, authorization, and role-based access, all backed by JWT for secure session management.

#### **Core Features:**

1. **JWT Authentication**: Provides token-based authentication, ensuring secure access to endpoints and maintaining user sessions with token refresh capability.

2. **Role-Based Permissions**: Users are categorized into roles like Admin and User, with Admins having full control over the system, including user management. Regular users are limited to their own data.

3. **CRUD Operations**: Admins can perform all CRUD operations (Create, Read, Update, Delete) on users. Each user has a unique role, and the system enforces that only one Admin exists at any time.

4. **Pagination**: Fetches users in paginated chunks to enhance performance and scalability, especially useful when dealing with large datasets.

5. **Database Integration**: Uses **Spring Data JPA** and **Hibernate** to interact seamlessly with a **MySQL** database for storing user data, roles, and permissions.

#### **Technology Stack:**

- **Spring Boot**: Provides the foundation for building a robust, production-ready application.
- **Spring Security**: Secures the application with role-based access and JWT authentication.
- **MySQL**: Stores user and role data in a relational database.
- **Spring Data JPA** & **Hibernate**: Simplifies database interaction with JPA and ORM functionality.
- **Lombok**: Reduces boilerplate code for entity classes.

This **User Management System** is ideal for applications requiring secure, role-based user management with a streamlined admin interface and strong security measures.
