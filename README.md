# Device Management API
This repository contains a RESTful API developed using core Java to manage IoT devices. It supports full CRUD (Create, Read, Update, Delete) operations and is designed to demonstrate fundamental backend development skills without relying on external databases.

# Key Features
* CRUD operations - Design and implementation of a RESTful API that allows users to perform Create, Read, Update, and Delete operations on IoT devices.
* Data Persistence - Uses an in-memory data structure (HashMap) to store and manage device information and ensures thread safety during concurrent access and modification of the data (Synchronized method).
* Unit Testing - Includes unit tests for API endpoints to verify functionality, ensure correctness, and maintain reliability.

  Example for Unit testing
  ![image](https://github.com/user-attachments/assets/4963cfc5-5c37-418d-bd0e-b3a0cb3882ba)

# Technology Stack 
* Core java
* Junit
* In memory database.

# How to build and Run 

I have used Intellij idea to build this project i will guide according to that. you can use any idea to build and run this project.

## Prerequisites
* Java 8 or higher (uses com.sun.net.httpserver)
* IntelliJ IDEA (Community or Ultimate Edition)
* Git (optional, for version control)

## Setup and Build Instructions
1. Clone the project.
2. then open the project in idea.
3. downlod the dependancies in the pom.xml file (if not download automatically).
4. go to the main class and right click and run the project.
5. you can configure project sdk in project structure form files. (if you haven't configure them).

# Endpoint testing using postman.

Create a new Device
![image](https://github.com/user-attachments/assets/198fce3d-e2ae-417e-a094-df4e9f9209b0)

Get All Devices
![image](https://github.com/user-attachments/assets/c3311301-b4d7-4e0a-b543-59c5eaa67a57)

Get Device by Id
![image](https://github.com/user-attachments/assets/0ee8bbba-8538-458b-9800-53ecde5c3b45)

Update Device
![image](https://github.com/user-attachments/assets/501d3c95-0423-42a0-8486-693996fea2ec)

Delete Device
![image](https://github.com/user-attachments/assets/ce5f7b08-c4f1-462f-a7c6-29e18916e80b)




