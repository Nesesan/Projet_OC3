

# ChaTop - Backend
# Description
- ChaTop is a backend application that manages users, rentals, and associated messages. This project uses Spring Boot and various services like Cloudinary for image management and JWT for authentication. It is designed to interact with a frontend via a RESTful API.

# Prerequisites
- Before you begin, make sure the following are installed on your machine:
•	Java 17 or later
•	Maven
•	MySQL or any MySQL-compatible database
•	Node.js and npm for the frontend (if required)

# Installation and Running the Project
# 1. Clone the repository
# Clone the repository to your local machine:
    git clone git@github.com:Nesesan/Projet_OC3.git
    cd Projet_OC3

# 2. Configure Environment Variables
# Create a .env file at the root of the project and add the following variables:
    DATABASE_URL=jdbc:mysql://localhost:3306/cha_top
    DATABASE_USERNAME=your_username
    DATABASE_PASSWORD=your_password
    JWT_SECRET=your_jwt_secret
    CLOUDINARY_CLOUD_NAME=your_cloud_name
    CLOUDINARY_API_KEY=your_api_key
    CLOUDINARY_API_SECRET=your_api_secret


# 3. Install Dependencies
   - Once the project is cloned, install the necessary Maven dependencies by running the following command:
   mvn install

# 4. Run the Project
#   To start the project, use the following command:
    mvn spring-boot:run
- The backend will be available at the following URL: http://localhost:3001.

#   Database Installation Procedure
# 1. Create the Database
   - Log into your MySQL server and create a new database for the application:

    CREATE DATABASE cha_top;

#   2. Apply SQL Scripts
# Run the provided SQL scripts to create the necessary tables:
    CREATE TABLE `USERS` (
    `id` integer PRIMARY KEY AUTO_INCREMENT,
    `email` varchar(255),
    `name` varchar(255),
    `password` varchar(255),
    `created_at` timestamp,
    `updated_at` timestamp
    );

    CREATE TABLE `RENTALS` (
    `id` integer PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255),
    `surface` numeric,
    `price` numeric,
    `picture` varchar(255),
    `description` varchar(2000),
    `owner_id` integer NOT NULL,
    `created_at` timestamp,
    `updated_at` timestamp
    );
    
    CREATE TABLE `MESSAGES` (
    `id` integer PRIMARY KEY AUTO_INCREMENT,
    `rental_id` integer,
    `user_id` integer,
    `message` varchar(2000),
    `created_at` timestamp,
    `updated_at` timestamp
    );
    
    CREATE UNIQUE INDEX `USERS_index` ON `USERS` (`email`);
    
    ALTER TABLE `RENTALS` ADD FOREIGN KEY (`owner_id`) REFERENCES `USERS` (`id`);
    
    ALTER TABLE `MESSAGES` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
    
    ALTER TABLE `MESSAGES` ADD FOREIGN KEY (`rental_id`) REFERENCES `RENTALS` (`id`);


# 3. Verify the Connection
   - Once the database is set up and the backend is running, you can test the database connection through the backend. Ensure that all the tables are correctly created.
   Swagger UI
   - The backend exposes an interactive API documentation via Swagger UI. 
   - You can access it at the following URL:
    http://localhost:3001/swagger-ui.html
   - Here, you will find all the API routes along with detailed information on how to use the different endpoints.

-   Ensure that all environment variables are properly set and that the MySQL server is running before starting the application.
- If you have any questions, feel free to consult the Spring Boot and Swagger documentation for more details.

