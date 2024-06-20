# Cagnotte Microservice

## Introduction

The Cagnotte microservice is designed to handle client transactions and manage a "cagnotte" (jackpot) for each client.
Each time a client makes a purchase, the merchant adds a certain amount to the client's cagnotte. Clients can check
their cagnotte balance and availability at any time. A cagnotte is considered available if the client has made at least
three purchases and the balance is at least 10 euros.

## Table of Contents

- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Postman Requests](#postman-requests)
- [Features](#features)
- [Dependencies](#dependencies)
- [Test Coverage Report](#test-coverage-report)
- [Links](#links)

## Requirements

- [Maven](https://maven.apache.org/download.cgi) installed on your machine
- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) installed on your machine
- [Docker](https://www.docker.com/products/docker-desktop) installed on your machine
- [DBeaver](https://dbeaver.io/download/) or [pgAdmin](https://www.pgadmin.org/download/) for connecting to the
  PostgreSQL database

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/Deathwong/cagnotte.git
    ```
2. Navigate to the project directory:
    ```sh
    cd cagnotte
    ```
3. Start the database using Docker Compose:
    ```sh
    docker-compose up
    ```
   The Docker Compose file can be found [here](./docker-compose.yml). If you do not have Docker, you can download
   it [here](https://www.docker.com/products/docker-desktop).


4. Set up the PostgreSQL database by executing the provided SQL scripts:
    ```sql
    CREATE SEQUENCE client_sequence START 1 INCREMENT 1;
    CREATE SEQUENCE transaction_sequence START 1 INCREMENT 1;

    CREATE TABLE clients (
        id BIGINT PRIMARY KEY DEFAULT nextval('client_sequence'),
        name VARCHAR(100) NOT NULL,
        email VARCHAR(100) UNIQUE NOT NULL
    );

    CREATE TABLE transactions (
        id BIGINT PRIMARY KEY DEFAULT nextval('transaction_sequence'),
        client_id BIGINT NOT NULL,
        amount DECIMAL(10, 2) CHECK (amount > 0) NOT NULL,
        transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        FOREIGN KEY (client_id) REFERENCES clients(id)
    );

    CREATE TABLE cagnotte (
        client_id BIGINT PRIMARY KEY,
        total_amount DECIMAL(10, 2) NOT NULL,
        transaction_count INT CHECK (transaction_count >= 0) NOT NULL,
        FOREIGN KEY (client_id) REFERENCES clients(id)
    );

    INSERT INTO public.clients (id, "name", email) VALUES (nextval('client_sequence'::regclass), 'bForBank', 'jefride.mensah@bforebank.fr');
    INSERT INTO public.clients (id, "name", email) VALUES (nextval('client_sequence'::regclass), 'bForBank two', 'jefrido.mensah@bforebank.fr');

    INSERT INTO public.transactions (id, client_id, amount, transaction_date) VALUES (nextval('transaction_sequence'::regclass), 2, 50, CURRENT_TIMESTAMP);

    INSERT INTO public.cagnotte (client_id, total_amount, transaction_count) VALUES (2, 5, 1);
    INSERT INTO public.cagnotte (client_id, total_amount, transaction_count) VALUES (1, 50, 5);
    ```

   This project uses a PostgreSQL database. You can find the database connection settings in
   the `application.properties` file. The configuration is also available in the Docker Compose
   file (`docker-compose.yml`).

   You can use software like [DBeaver](https://dbeaver.io/download/) or [pgAdmin](https://www.pgadmin.org/download/) to
   connect to the database.


5. Build the project using Maven:
    ```sh
    mvn clean install
    ```
6. Start the application:
    ```sh
    mvn spring-boot:run
    ```

## Usage

1. Start the application as described in the [Installation](#installation) section.
2. Use the provided Postman collection to test the API endpoints. Import the collection into Postman to get started. The
   Postman collection can be found [here](./Cagnotte.postman_collection.json). If you do not have Postman, you can
   download it [here](https://www.postman.com/downloads/).

### Example Requests

- Add a transaction:
    ```http
    POST http://localhost:8080/api/v1/client/1/transactions?amount=50
    ```
- Get client's cagnotte:
    ```http
    GET http://localhost:8080/api/v1/client/1/cagnotte
    ```
- Check if cagnotte is available:
    ```http
    GET http://localhost:8080/api/v1/client/1/cagnotte/available
    ```

## Postman Requests

The Postman collection includes the following requests:

1. **Transaction**
    - Add transaction success
    - Add transaction with wrong client ID
    - Add transaction with wrong amount
2. **Jackpot**
    - Get cagnotte
    - Get cagnotte with wrong client
    - Check if cagnotte is available
    - Check if cagnotte is not available

## Features

- Add transactions for clients.
- Retrieve client's cagnotte details.
- Check if a client's cagnotte is available.

## Dependencies

- Spring Boot
- Spring Data JPA
- PostgreSQL
- Lombok
- MapStruct

For a full list of dependencies, refer to the `pom.xml` file.

## Test Coverage Report

Unit test coverage reports are available in the `testCoverageReport` directory. To view the coverage report, open
the [index.html](./testCoverageReport/index.html) file in your web browser.

## Links

- [Download Maven](https://maven.apache.org/download.cgi)
- [Download Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Download Docker](https://www.docker.com/products/docker-desktop)
- [Download DBeaver](https://dbeaver.io/download/)
- [Download pgAdmin](https://www.pgadmin.org/download/)
- [Download Postman](https://www.postman.com/downloads/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
