Cafe Management System
Tech Stack: HTML, CSS, JavaScript, Spring Boot, Thymeleaf, MySQL
Features: Role-based authentication, MVC design pattern, global exception handling, pagination, and server-side validation.
Modules:
Customer: Register/Login, order food online, and make payments.
Employee: Handle dine-in orders, manage dishes (CRUD), and update profile.
Admin: Manage employees (CRUD), assign roles, and update profile.
Highlights:
Secure login for customers to place orders.
Restricted actions: Only Admin can add employees, and only Employees can manage dishes.
Responsive UI with enhanced user experience.


# Prerequisites

Make sure the following are installed on the server.

```
Docker
Docker Compose
Git
Java 17
```

Install Docker (Ubuntu):

```
sudo apt update
sudo apt install docker.io -y
sudo systemctl start docker
sudo systemctl enable docker
```

Allow docker for ubuntu user:

```
sudo usermod -aG docker ubuntu
newgrp docker
```

Install Git:

```
sudo apt install git -y
```

---

# Clone the Repository

```
git clone https://github.com/nileshbhurewar/cafe-management-system.git
cd cafe-management-system
```

---

# Method 1: Running Application Using Docker Network (Manual Way)

This method manually creates containers and connects them using a Docker network.

---

## Step 1: Create Docker Network

```
docker network create cafe-network
```

Check networks

```
docker network ls
```

---

## Step 2: Run MySQL Container

```
docker run -d \
--name mysql-db \
--network cafe-network \
-e MYSQL_ROOT_PASSWORD=root \
-e MYSQL_DATABASE=cafe_db \
-p 3306:3306 \
mysql:8
```

---

## Step 3: Configure Spring Boot Database

Update file:

```
src/main/resources/application.properties
```

```
spring.datasource.url=jdbc:mysql://mysql-db:3306/cafe_db
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Note:

```
mysql-db
```

is the **container name used as hostname**.

---

## Step 4: Build Docker Image

```
docker build -t cafe-app .
```

Verify image

```
docker images
```

---

## Step 5: Run Spring Boot Container

```
docker run -d \
--name cafe-container \
--network cafe-network \
-p 8080:8080 \
cafe-app
```

---

## Step 6: Verify Running Containers

```
docker ps
```

You should see

```
mysql-db
cafe-container
```

---

## Step 7: Access Application

Open browser

```
http://EC2-PUBLIC-IP:8080
```

Example

```
http://13.232.120.55:8080
```

---

# Method 2: Running Application Using Docker Compose (Recommended)

Docker Compose runs **multiple containers using a single configuration file**.

---

## Step 1: Create docker-compose.yml

Create file:

```
docker-compose.yml
```

Add the following configuration.

```
version: '3.8'

services:

  mysql-db:
    image: mysql:8
    container_name: mysql-db
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: cafe_db
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql

  cafe-app:
    build: .
    container_name: cafe-app
    restart: always
    ports:
      - "8080:8080"
    depends_on:
      - mysql-db
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql-db:3306/cafe_db
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root

volumes:
  mysql-data:
```

---

## Step 2: Run Docker Compose

```
docker compose up -d
```

This command will automatically

* Create network
* Start MySQL container
* Build Spring Boot image
* Start application container

---

## Step 3: Check Running Containers

```
docker ps
```

Expected output

```
mysql-db
cafe-app
```

---

## Step 4: Access Application

```
http://EC2-PUBLIC-IP:8080
```

Example

```
http://13.232.120.55:8080
```

---

# Docker Compose Useful Commands

Start containers

```
docker compose up -d
```

Stop containers

```
docker compose down
```

View logs

```
docker compose logs
```

Rebuild containers

```
docker compose up --build
```

---

# Docker Useful Commands

Check containers

```
docker ps
```

Stop container

```
docker stop container_name
```

Remove container

```
docker rm container_name
```

Check images

```
docker images
```

---

# Author

Nilesh Bhurewar

GitHub
https://github.com/nileshbhurewar

LinkedIn
https://www.linkedin.com/in/nilesh-rajesh-bhurewar/
