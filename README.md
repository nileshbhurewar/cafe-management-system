# Cafe Management System

## Project Overview

The **Cafe Management System** is a web-based application built using **Spring Boot, Thymeleaf, and MySQL**.
It allows customers to order food online, employees to manage orders and dishes, and administrators to manage employees and system roles.

The application follows the **MVC architecture** and includes **role-based authentication, server-side validation, pagination, and global exception handling**.

---

# Tech Stack

* HTML
* CSS
* JavaScript
* Spring Boot
* Thymeleaf
* MySQL
* Docker
* Docker Compose
* Kubernetes (Minikube)

---

# Features

### Authentication & Security

* Role-based authentication
* Secure login and registration
* Access restrictions based on roles

### MVC Architecture

* Separation of controller, service, and repository layers
* Maintainable and scalable code structure

### Exception Handling

* Global exception handling using Spring Boot

### Pagination

* Efficient handling of large datasets

### Server-side Validation

* Input validation implemented using Spring Boot validation

---

# Modules

## Customer Module

Customers can:

* Register and login
* Browse dishes
* Order food online
* Make payments

## Employee Module

Employees can:

* Handle dine-in orders
* Manage dishes (CRUD operations)
* Update their profile

## Admin Module

Administrators can:

* Manage employees (CRUD)
* Assign roles
* Update profile

---

# Project Highlights

* Secure login system for customers
* Role-based access control
* Only **Admin** can manage employees
* Only **Employees** can manage dishes
* Responsive user interface for better user experience

---

# Prerequisites

Ensure the following tools are installed on your server.

```
Docker
Docker Compose
Git
Java 17
kubectl
Minikube
```

---

# Install Docker (Ubuntu)

```
sudo apt update
sudo apt install docker.io -y
sudo systemctl start docker
sudo systemctl enable docker
```

Allow docker for ubuntu user

```
sudo usermod -aG docker ubuntu
newgrp docker
```

Verify installation

```
docker --version
```

---

# Install Git

```
sudo apt install git -y
```

Verify installation

```
git --version
```

---

# Clone the Repository

```
git clone https://github.com/nileshbhurewar/cafe-management-system.git
cd cafe-management-system
```

---

# Method 1: Running Application Using Docker Network (Manual Approach)

This method manually creates containers and connects them using a **Docker network**.

---

## Step 1: Create Docker Network

```
docker network create cafe-network
```

Verify network

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

## Step 3: Configure Database in Spring Boot

Edit the file:

```
src/main/resources/application.properties
```

Add the following configuration:

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

is the **MySQL container hostname inside the Docker network**.

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

Expected output:

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

Docker Compose allows running **multi-container applications using a single configuration file**.

---

## Step 1: Create docker-compose.yml

Create file

```
docker-compose.yml
```

Add the following configuration

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

## Step 2: Start Application

```
docker compose up -d
```

This command will automatically:

* Create Docker network
* Start MySQL container
* Build Spring Boot image
* Start application container

---

## Step 3: Verify Containers

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

# Deploy Application on Kubernetes Using Minikube

Minikube is a **local Kubernetes cluster** used for development and testing.

---

# Step 1: Start Minikube Cluster

```
minikube start --driver=docker
```

Verify cluster

```
kubectl get nodes
```

Expected output

```
NAME       STATUS   ROLES           AGE
minikube   Ready    control-plane
```

---

# Step 2: Build Docker Image Inside Minikube

Configure Docker environment

```
eval $(minikube docker-env)
```

Build application image

```
docker build -t cafe-app .
```

Verify

```
docker images
```

---

# Step 3: Create Kubernetes Directory

```
mkdir k8s
cd k8s
```

---

# Step 4: Create MySQL Deployment

File:

```
mysql-deployment.yaml
```

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mysql
spec:
  replicas: 1
  selector:
    matchLabels:
      app: mysql
  template:
    metadata:
      labels:
        app: mysql
    spec:
      containers:
      - name: mysql
        image: mysql:8
        ports:
        - containerPort: 3306
        env:
        - name: MYSQL_ROOT_PASSWORD
          value: root
        - name: MYSQL_DATABASE
          value: cafe_db
```

---

# Step 5: Create MySQL Service

File:

```
mysql-service.yaml
```

```
apiVersion: v1
kind: Service
metadata:
  name: mysql-service
spec:
  selector:
    app: mysql
  ports:
  - port: 3306
    targetPort: 3306
  type: ClusterIP
```

---

# Step 6: Create Application Deployment

File:

```
app-deployment.yaml
```

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: cafe-app
spec:
  replicas: 2
  selector:
    matchLabels:
      app: cafe-app
  template:
    metadata:
      labels:
        app: cafe-app
    spec:
      containers:
      - name: cafe-app
        image: cafe-app
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_DATASOURCE_URL
          value: jdbc:mysql://mysql-service:3306/cafe_db
        - name: SPRING_DATASOURCE_USERNAME
          value: root
        - name: SPRING_DATASOURCE_PASSWORD
          value: root
```

---

# Step 7: Create Application Service

File:

```
app-service.yaml
```

```
apiVersion: v1
kind: Service
metadata:
  name: cafe-service
spec:
  selector:
    app: cafe-app
  ports:
  - port: 8080
    targetPort: 8080
  type: ClusterIP
```

ClusterIP is used because we will access the application using **port forwarding**.

---

# Step 8: Deploy Resources

```
kubectl apply -f mysql-deployment.yaml
kubectl apply -f mysql-service.yaml

kubectl apply -f app-deployment.yaml
kubectl apply -f app-service.yaml
```

---

# Step 9: Verify Deployment

Check pods

```
kubectl get pods
```

Check services

```
kubectl get svc
```

---

# Step 10: Access Application

Use port forwarding

```
kubectl port-forward service/cafe-service 8080:8080
```

Open browser

```
http://localhost:8080
```

---

# Kubernetes Debugging Commands

View pod logs

```
kubectl logs <pod-name>
```

Describe pod

```
kubectl describe pod <pod-name>
```

Check cluster events

```
kubectl get events
```

---

# Author

**Nilesh Bhurewar**

GitHub
https://github.com/nileshbhurewar

LinkedIn
https://www.linkedin.com/in/nilesh-rajesh-bhurewar/
