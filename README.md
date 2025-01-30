# products-ms



## Overview

This microservice is designed for product management, offering a variety of RESTful APIs for adding products, retrieving products by code, and fetching a list of all products.


## Technology stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.x
- **Database**: PostgreSQL
- **Build tool**: Maven
- **Containerization**: Docker
- **Init Container**: Liquibase
- **Data persistance**: JPA

## Architecture
This microservice follows a modular architecture with the following components.

### Modules
| Module              | Description                                             |
|---------------------|---------------------------------------------------------|
| **products-app**    | Contains main logic, controllers, mappers and services. 
| **products-client** | Contains dtos needed for REST web services.             |
| **products-data**   | Contains repository and entity definitions.             |

## Getting started

### Prerequisites 
- Java 21
- Maven 3+
- Docker
- PostgreSQL

To run the application, you need to have Java 21 installed on your machine. 
You can download it from [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
Furthermore, you need to have Maven 3+ installed on your machine. 
You can download it from [here](https://maven.apache.org/download.cgi).
If you want to run the application in a container, you need to have Docker installed on your machine.
Finally, you need to have PostgreSQL installed on your machine.

### Clone the Repository

To clone the repository, run the following command:
```
cd existing_repo
git remote add origin https://github.com/anaragu94/products-ms.git
git branch -M main
git push -uf origin main
```

## Database setup
Before running the application, you need to create the required table and configure the database.
All configurations for the database are located in the `application.properties` file in the `products-app` module.
You can find the file in the following path: `products-ms/products-app/src/main/resources/application.properties`.

```
spring.datasource.url= jdbc:postgresql://localhost:5432/testdb 
spring.datasource.username=user
spring.datasource.password=pass 
```
Where 'spring.datasource.url' is the URL of the database and db schema, 'spring.datasource.username' is the username 
for the database and 'spring.datasource.password' is the password for the database.

This application is prepared to use Liquibase as init container.
In liquibase folder you can find the changelog file which is used for creating the table in the database.
This file is located in the following path: `products-app/src/main/resources/liquibase/db.changelog-master.yaml`.

Liquibase also helps in testing the application with an in-memory database.

TODO: Add all configuration for integration test and use liquibase (didn't have time to finish it, but I can explain 
the idea).

## Unit tests

Unit tests are written for all classes located in the `products-app` module.
They are located in the `products-app/src/test/java` folder.

## Integration tests
**TODO**  
Disclaimer: Unfortunately, I didn't have time to write integration tests for this application.
But I can explain the idea and how I would write them:
I would use an in-memory database for testing purposes. I would use already prepared liquibase configuration and changelog files
for creating table that I would later use in tests.
Integration test would be written for all possible cases, for example, creating a product, 
getting a product by code, getting a list of products, etc.

## REST web services
In the first version of this application, the following REST web services are implemented:
All endpoints start with `/api/product`
- **POST** - Create a product
- **GET `/{code}`** - Gets a specific product fetched by code (unique value)
- **GET `/list/{price}`** - Gets a list of products that are greater or equal than given price in euros
- **GET `/available/{isAvailable}`** - Gets a list of all products that are available or not available, depending on the value of the parameter

## Call of external services

In this application when creating a product, we are calling an external service to get the currency rate of 
'USD' currency. For this purpose we are using "WebClient" HTTP client from Spring WebFlux.
Since our call is designed to be synchronous, we are using the "block()" method to block the execution 
until we get the response.
If the call fails for some reason, for our calculation we are using default currency rate.



## Project status

This project is currently in the development phase, with basic functionalities and REST web services already implemented. Future plans include expanding features and adding more REST endpoints to enhance its capabilities.
