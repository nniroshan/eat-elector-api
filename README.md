# Eat Elector - Backend (Eat-Elector-API)

This backend repository is a part of the Eat Elector app, a web based application designed to help teams collectively
decide on a location for lunch.

Frontend implementation (Eat Elector - Frontend) can be found in the repository below.

[https://github.com/nniroshan/eat-elector-fe](https://github.com/nniroshan/eat-elector-fe)

## Table of Contents

- [Project Overview](#project-overview)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)

## Project Overview

This API leverages the following technologies:

- **Spring Boot**: A powerful framework for building Java applications with ease.
- **Spring Data JPA**: Simplifies data access and provides a repository pattern for working with the database.
- **H2 In-Memory Database**: A lightweight, in-memory database for development and testing.
- **Mockito**: A popular Java testing framework for unit testing and mocking dependencies.
- **Gradle**: The build tool used for this project.

## Getting Started

Follow these steps to set up and run the Eat-Elector-API on your local machine.

### Prerequisites

Before you begin, make sure you have the following software and tools installed:

- Open Java Development Kit (JDK) 17 or higher
- Gradle (for building and managing dependencies)

### Installation

1. **Clone the Repository:** Start by cloning this repository to your local machine using Git.

`git clone https://github.com/nniroshan/eat-elector-api.git`

2. **Navigate to the Project Folder:** Change your working directory to the project folder.

`cd eat-elector-api`

3. **Build the Application:** Use Gradle to build the application.

`gradle build`

4. **Run the Application:** Start the Spring Boot application.

`java -jar build/libs/eat-elector-api-0.0.1-SNAPSHOT.jar`

The API should now be up and running at http://localhost:8080.

## Usage

The Eat-Elector-API provides several endpoints you can interact with the using tools 
like Postman, cURL, or any HTTP client.

## API Endpoints

Endpoints provided by the API:

- `POST /restaurant`: Add new restaurant.
- `GET /restaurant/random`: Select random restaurant.

## Testing
The Eat-Elector-API includes unit tests to ensure the correctness of its functionality. Mockito is used for mocking 
dependencies in tests. You can run the tests using the following command:

`gradle test`

