URL Shortener (Spring Boot)

A backend-focused URL Shortener application built using Spring Boot, MySQL, and Redis.
The project demonstrates core backend concepts such as REST API design, caching, database integration, and system scalability.

🚀 Features
Generate short URLs from long URLs
Redirect short URLs to original URLs
Persistent storage using MySQL
Redis caching for faster redirection
Asynchronous processing support
RESTful API architecture
Simple frontend served via Spring Boot static resources

🛠 Tech Stack
Language: Java
Framework: Spring Boot
Database: MySQL
Cache: Redis
Build Tool: Maven

How the System Works ------

User submits a long URL
Application generates a unique short key
URL mapping is stored in MySQL
Short URL is cached in Redis
On access, Redis is checked first
If cache miss, data is fetched from MySQL
User is redirected to the original URL

Running Locally

Clone the repository
Start MySQL and Redis locally
Update application.properties with local credentials

Run the application:
mvn spring-boot:run



Author
Vignesh
B.Tech CSE
Backend Developer (Java, Spring Boot, MySQL, Redis)
