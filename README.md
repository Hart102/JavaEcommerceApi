# Ecommerce REST Api using Spring Boot

This project is a Spring Boot-based e-commerce application designed to provide a comprehensive shopping experience. 
It includes features for user authentication, product management, cart management, and order processing.

## Features

### User Management
- **Authentication and Authorization**: Secure user authentication using Spring Security with role-based access control.

### Product Management
- **Create Product**: Add new products with support for multiple images.
- **Edit Product**: Update product details including images, name, price, brand, and category.
- **Get Product**: Retrieve products by:
    - ID
    - Brand Name
    - Category
    - Name
- **Delete Product**: Remove products from the catalog.

### Cart Management
- **Add to Cart**: Add products to the user's cart.
- **Remove from Cart**: Remove specific items from the cart.
- **Update Cart**: Modify the quantity of items in the cart.

### Order Management
- **Place Orders**: Checkout the cart to create orders.

### General Features
- **Get All Products**: Fetch a list of all available products.

## Technologies Used
- **Spring Boot**: Backend framework for building the application.
- **Spring Security**: For handling authentication and authorization.
- **Hibernate & JPA**: For ORM and database interactions.
- **MySQL**: Database for storing application data.

## Getting Started

### Prerequisites
- **Java 17** or later
- **Maven** for dependency management
- **MySQL** database server

