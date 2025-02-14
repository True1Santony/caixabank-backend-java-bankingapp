# 🏦 CaixaBank Java Backend Challenge ☕️

Category   ➡️   Software

Subcategory   ➡️   Java Backend

### Enlace al Challenge
- [Leaderboard del desafio](https://nuwe.io/es/hackathons/caixabank-tech-challenges-round-1?section=leaderboard)

---
# Instalación

## Para ejecutar y trabajar en este proyecto, sigue estos pasos:

### Requisitos previos

Asegúrate de tener instalado:
- Java JDK 11 o superior.
- Maven para gestionar las dependencias del proyecto.
- Docker para poder construir y ejecutar el contenedor de la aplicación.

### Clonar el repositorio

Clona el repositorio del proyecto en tu máquina local:

```bash
git clone <URL_DEL_REPOSITORIO>
cd caixabank-backend-java-bankingapp
```

### Construir el proyecto con Maven

Ejecuta el siguiente comando para compilar el proyecto y descargar las dependencias necesarias:

```bash
./mvnw clean install
```

### Configurar Docker

> ⚠ **No modifiques el archivo `docker-compose.yml` ni el `Dockerfile`.**

Construye la imagen Docker usando:

```bash
docker-compose up --build
```

Esto iniciará el contenedor en el puerto `3000`.

### Verificar que la aplicación esté funcionando

Accede al endpoint de salud (`/api/health`) para confirmar que todo está bien:

```bash
curl http://localhost:3000/api/health
```

Deberías recibir una respuesta indicando que el servicio está activo.

---

## Enlace a la Documentación Técnica

A continuación, algunos enlaces útiles para resolver el desafío:

### Java Spring Boot Documentation
- [Spring Boot Official Guide](https://spring.io/projects/spring-boot)
- [Spring Security Documentation](https://spring.io/projects/spring-security)

### JWT Authentication
- [JSON Web Token (JWT) Introduction](https://jwt.io/introduction/)
- [Spring Boot JWT Integration](https://www.baeldung.com/spring-security-oauth-jwt)

### Docker
- [Docker Official Documentation](https://docs.docker.com/)
- [Docker Compose Basics](https://docs.docker.com/compose/)

### Postman
- [Postman Getting Started Guide](https://learning.postman.com/docs/getting-started/introduction/)
- [Testing APIs with Postman](https://learning.postman.com/docs/sending-requests/requests/)

### BCrypt Password Hashing
- [BCrypt in Java](https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt)

### API Testing
- [Testing REST APIs with Postman](https://learning.postman.com/docs/writing-scripts/test-scripts/)
- [Writing Unit Tests for Spring Boot](https://www.baeldung.com/spring-boot-testing)

---

## Cosas que He Aprendido

### Configuración de Docker
- Aprendí cómo usar un `Dockerfile` y `docker-compose.yml` para empaquetar y ejecutar aplicaciones Java en contenedores.

### Autenticación JWT
- Descubrí cómo implementar autenticación segura utilizando tokens JWT en una API RESTful.
- Aprendí a generar y validar tokens JWT en Java con bibliotecas como `jjwt`.

### Validación de Datos
- Practiqué la validación de campos obligatorios, formatos de correo electrónico y reglas de contraseñas complejas.
- Usé anotaciones de Hibernate Validator como `@NotNull`, `@Email`, y expresiones regulares personalizadas.

### Manejo de Transacciones Financieras
- Implementé lógica para depósitos, retiros y transferencias, considerando casos especiales como tarifas bancarias y fraudes.
- Aprendí a manejar estados de transacciones (`PENDING`, `APPROVED`, `FRAUD`) y registrarlos en la base de datos.

### Interés Compuesto en Cuentas de Inversión
- Desarrollé una funcionalidad para calcular interés compuesto en cuentas de inversión cada 10 segundos.
- Utilicé hilos o programación asíncrona para actualizar balances automáticamente.

### Pruebas de API con Postman
- Aprendí a utilizar Postman para probar endpoints HTTP y verificar respuestas JSON.
- Configuré entornos y variables globales en Postman para automatizar pruebas.

### Control de Errores y Mensajes Claros
- Implementé manejo de errores robusto para devolver mensajes claros y códigos de estado HTTP adecuados.
- Por ejemplo, `"Bad credentials"` para login fallido o `"Access Denied"` para endpoints privados sin autenticación.

### Calidad del Código
- Me enfoqué en escribir código limpio, legible y escalable.
- Seguí buenas prácticas como la separación de responsabilidades (`controllers`, `services`, `repositories`) y el uso de DTOs para transferir datos.

## 🌐 Background

As digital finance evolves, CaixaBank is dedicated to pushing the boundaries of secure and innovative banking solutions. In this challenge, you will step into the role of a developer tasked with building essential features for a financial services application, focusing on the practical implementation of robust account management, transaction security, and operational efficiency. This project simulates real-world scenarios and requirements, reflecting CaixaBank’s commitment to delivering reliable and user-friendly solutions in an increasingly digital-first world.

## 📂 Repository Structure

```bash
caixabank-backend-java-bankingapp/
├── docker-compose.yml
├── Dockerfile
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── hackathon
    │   │           └── finservice
    │   │               ├── Config
    │   │               │   └── CorsConfig.java
    │   │               ├── Controllers
    │   │               │   └── HealthCheckController.java
    │   │               ├── DTO
    │   │               ├── Entities
    │   │               │   ├── Account.java
    │   │               │   ├── Token.java
    │   │               │   ├── Transaction.java
    │   │               │   ├── TransactionStatus.java
    │   │               │   ├── TransactionType.java
    │   │               │   └── User.java
    │   │               ├── Exception
    │   │               ├── FinserviceApplication.java
    │   │               ├── Repositories
    │   │               ├── Security
    │   │               ├── Service
    │   │               └── Util
    │   │                   └── JsonUtil.java
    │   └── resources
    │       └── application.properties
    └── test
        └── java
            └── com
                └── hackathon
                    └── finservice
```

## 🎯 Tasks

This challenge is about implementing basic registration endpoints, protected endpoints whilst implementing the logic of a small codebase. 

1. **Task 1**: Dockerfile & Health Check
2. **Task 2**: User Actions
3. **Task 3**: Account Transactions, Monitoring & validations
4. **Task 4**: Invest Accounts
5. **Task 5**: Security & Error Handling

**PLEASE READ THE ENTIRE README CAREFULLY BEFORE STARTING, AS WELL AS ALL THE RESOURCES PROVIDED.**

### 📑 Detailed information about tasks

#### Task 1: Dockerfile

The first thing to do is to configure the Dockerfile to be able to test the application in containers.

A health check endpoint is provided to which a first request will be sent to check that the container is working properly.

Before doing the first push, you should make sure that this file works correctly, as all other tasks will be tested by attacking the endpoint generated by this container on port 3000.

The contents of the `/target` folder must not be used for this task.

#### Task 2: User Actions

This task focuses on basic user-related actions such as registering a new user, logging in, retrieving user and account details, and logging out. For these actions, you will need to interact with several endpoints, some of which require authentication.

- **User Registration**: Implement the functionality to register a user by sending the required information like name, email and password. This registration should return the account number, which will be used for future operations. By default, the automatically created account will be of the type `Main`.
    Request body:
    ```json
    {
        "name":"Nuwe Test",
        "password":"NuweTest1$",
        "email":"nuwe@nuwe.com"
    }
    ```
    Response:
    ```json
    {
        "name": "Nuwe Test",
        "email": "nuwe@nuwe.com",
        "accountNumber": "19b332",
        "accountType": "Main",
        "hashedPassword": "$2a$10$vYWBxACqEIPeoT0O5b0faOHp4ITAHSBvoHDzBePW7tPqzpvqKLi6G"
    }
    ```
    The accountNumber must be created and assigned to the account automatically by the app and be a UUID.
    
    Checks should include:
    - No empty fields.
    - The email format must be valid.
    - Password rules to be detailed later.
    - Check if the email already exists.


- **User Login**: Implement a login mechanism using either an email along with a password. After successful authentication, the system should return a JWT token, which will be used for all protected endpoints.
    Request body:
    ```json
    {
        "identifier":"nuwe@nuwe.com",
        "password":"NuweTest1$"
    }
    ```
    Response:
    ```json
    {
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxOWIzMzIiLCJpYXQiOjE3Mjk1NzEzNzUsImV4cCI6MTcyOTY1Nzc3NX0.6qLQi50B1StobsUusfxCSqLdKeKOYdBZ3qj5Lw5G9eAdqoV1Juz3jyh2xwWByG7iJtusrhYPb_I62ycptcH4MA"
    }
    ```

    If the identifier is invalid it returns the following with Status Code 400:

    ```
    User not found for the given identifier: nuwee@nuwe.com 
    ```

    If the password is invalid it returns the following with Status Code 401:

    ```
    Bad credentials
    ```

- **Get User Info**: Once logged in, use the JWT token to retrieve detailed user information (e.g., name, email, account number). This always returns the `Main` account data.
    Response:
    ```json
    {
        "name": "Nuwe Test",
        "email": "nuwe@nuwe.com",
        "accountNumber": "e62fa2",
        "accountType": "Main",
        "hashedPassword": "$2a$10$VNEntB38mHY.dJ9iDkgrjud2EZ/pWCC9IisqyKqL3cLjEM0L0zSZS"
    }
    ```
- **Get Account Info**: Fetch account information such as the account balance. You must be logged in. This always returns the `Main` account data.
    Response:
    ```json
    {
        "accountNumber": "e62fa2",
        "balance": 0.0,
        "accountType": "Main"
    }
    ```

- **Create new Account**: Users can create new accounts by indicating the type of account they want to create. To assign the new account to the user it will be necessary to use the `Main` account as a reference.
    Request:
    ```json
    {
        "accountNumber": "e62fa2",
        "accountType": "Invest"
    }
    ```
    Response:
    ```
    New account added successfully for user
    ```

- **Get specific Account Info**: Gets information for a specific account using an index. For example if for the endpoint /api/dashboard/account/0 you get the `Main` account and for /api/dashboard/account/1 you get the first account created by the user.
    Request: http://localhost:3000/api/dashboard/account/0
    Response:
    ```json
    {
    "accountNumber": "e62fa2",
    "balance": 0.0,
    "accountType": "Main"
    }
    ```
    Request: http://localhost:3000/api/dashboard/account/1
    ```json
    {
    "accountNumber": "a14813",
    "balance": 0.0,
    "accountType": "Invest"
    }
    ```

- **Logout**: Implement a logout system that invalidates the JWT token, ensuring that users cannot access protected endpoints anymore.

#### Task 3: Account Transactions, Validation & Monitoring

This task involves implementing basic financial transactions such as deposits, withdrawals, and fund transfers. Additionally, it includes viewing transaction history, transaction monitoring, anti-fraud rules and bank fees for certain transactions.

All transactions should be marked as `PENDING` by default until the monitoring tool has checked and validated them. Once validated, transactions will appear as `APPROVED`. If fraud is detected, transactions will be flagged as `FRAUD`.

For any transaction, it must be verified that there are sufficient funds. If there are not sufficient funds, the **text** must be displayed: Insufficient balance

All transactions must require a JWT.

- **Deposit Money**: Create functionality that allows users to deposit money into their `Main` account. 
    Request body:
    ```json
    {
        "amount":100000.0
    }
    ```
    Response:
    ```json
    {
        "msg": "Cash deposited successfully"
    }
    ```
    - Check that the funds have been correctly added to the database.
    - For deposits over 50000, the bank applies a fee of 2%. For example, if a deposit of 100000 is made, the bank will keep 2000, leaving 98000 in the account.
    - The initial status is `PENDING` and when monitoring checks the values and applies the bank rates, its status will change to `APPROVED`.

- **Withdraw Money**: Implement a system where users can withdraw money from their `Main` account.
    Request body:
    ```json
    {
        "amount":20000.0
    }
    ```
    Response:
    ```json
    {
        "msg": "Cash withdrawn successfully"
    }
    ```
    - Check that the funds have been correctly withdrawn to the database.
    - For withdrawals over 10000, the bank will apply a fee of 1%. For example, if a withdrawal of 20000 is made, the bank will keep 200 which must be withdrawn extra from the account.
    - The initial status is `PENDING` and when monitoring checks the values and applies the bank rates, its status will change to `APPROVED`.

- **Fund Transfer**: Enable the transfer of funds from one account to another using account numbers.
    Request body:
    ```json
    {
        "amount": 1000.0,
        "targetAccountNumber": "ed9050"
    }
    ```
    Response:
    ```json
    {
        "msg": "Fund transferred successfully"
    }
    ```
    - All transfers over 80000 must be flagged as fraud.
    - If more than 4 transfers are made to the same account in less than 5 seconds, these transactions should be considered fraud.

- **Transaction History**: Implement a feature that allows users to view the complete history of transactions related to their account.
    Response:
    ```json
    [
        {
            "id": 14,
            "amount": 500.0,
            "transactionType": "CASH_TRANSFER",
            "transactionStatus": "PENDING",
            "transactionDate": 1731547476117,
            "sourceAccountNumber": "e62fa2",
            "targetAccountNumber": "a14813"
        },
        {
            "id": 13,
            "amount": 100.0,
            "transactionType": "CASH_TRANSFER",
            "transactionStatus": "FRAUD",
            "transactionDate": 1731547459984,
            "sourceAccountNumber": "e62fa2",
            "targetAccountNumber": "a14813"
        },
        {
            "id": 12,
            "amount": 100.0,
            "transactionType": "CASH_TRANSFER",
            "transactionStatus": "FRAUD",
            "transactionDate": 1731547459499,
            "sourceAccountNumber": "e62fa2",
            "targetAccountNumber": "a14813"
        },
        {
            "id": 11,
            "amount": 100.0,
            "transactionType": "CASH_TRANSFER",
            "transactionStatus": "FRAUD",
            "transactionDate": 1731547459004,
            "sourceAccountNumber": "e62fa2",
            "targetAccountNumber": "a14813"
        },
        {
            "id": 10,
            "amount": 100.0,
            "transactionType": "CASH_TRANSFER",
            "transactionStatus": "FRAUD",
            "transactionDate": 1731547457158,
            "sourceAccountNumber": "e62fa2",
            "targetAccountNumber": "a14813"
        },
        {
            "id": 9,
            "amount": 90000.0,
            "transactionType": "CASH_TRANSFER",
            "transactionStatus": "FRAUD",
            "transactionDate": 1731547427264,
            "sourceAccountNumber": "e62fa2",
            "targetAccountNumber": "a14813"
        },
        {
            "id": 8,
            "amount": 1000.0,
            "transactionType": "CASH_TRANSFER",
            "transactionStatus": "PENDING",
            "transactionDate": 1731547317779,
            "sourceAccountNumber": "e62fa2",
            "targetAccountNumber": "a14813"
        },
        {
            "id": 7,
            "amount": 90000.0,
            "transactionType": "CASH_DEPOSIT",
            "transactionStatus": "APPROVED",
            "transactionDate": 1731547210334,
            "sourceAccountNumber": "e62fa2",
            "targetAccountNumber": "N/A"
        },
        {
            "id": 6,
            "amount": 35000.0,
            "transactionType": "CASH_WITHDRAWAL",
            "transactionStatus": "APPROVED",
            "transactionDate": 1731546185725,
            "sourceAccountNumber": "e62fa2",
            "targetAccountNumber": "N/A"
        },
        {
            "id": 5,
            "amount": 100000.0,
            "transactionType": "CASH_WITHDRAWAL",
            "transactionStatus": "APPROVED",
            "transactionDate": 1731546163900,
            "sourceAccountNumber": "e62fa2",
            "targetAccountNumber": "N/A"
        },
        {
            "id": 4,
            "amount": 20000.0,
            "transactionType": "CASH_WITHDRAWAL",
            "transactionStatus": "APPROVED",
            "transactionDate": 1731546137315,
            "sourceAccountNumber": "e62fa2",
            "targetAccountNumber": "N/A"
        },
        {
            "id": 3,
            "amount": 20000.0,
            "transactionType": "CASH_WITHDRAWAL",
            "transactionStatus": "APPROVED",
            "transactionDate": 1731546061156,
            "sourceAccountNumber": "e62fa2",
            "targetAccountNumber": "N/A"
        },
        {
            "id": 2,
            "amount": 90000.0,
            "transactionType": "CASH_DEPOSIT",
            "transactionStatus": "APPROVED",
            "transactionDate": 1731545950697,
            "sourceAccountNumber": "e62fa2",
            "targetAccountNumber": "N/A"
        },
        {
            "id": 1,
            "amount": 90000.0,
            "transactionType": "CASH_DEPOSIT",
            "transactionStatus": "APPROVED",
            "transactionDate": 1731545933441,
            "sourceAccountNumber": "e62fa2",
            "targetAccountNumber": "N/A"
        }
    ]
    ```
    The types of transactions that must be supported by the app are:
    - CASH_WITHDRAWAL
    - CASH_DEPOSIT
    - CASH_TRANSFER

    The transaction date and transactio status must also be recorded in the database.

#### Task 4: Investment accounts

These accounts can be created by the user on demand, but do not allow direct login. They can only receive money from transfers made by the Main account.
When created, they must be created with the accuntType `Invest`.

What the application should do is to check if such an account exists in the database and apply a 10% interest rate to the current balance of the account.

In this case, to simulate months, this interest can be applied every `10 seconds`.

The application does not require any activation for this, it should automatically detect if such an account is created and apply interest as soon as a transfer is made, as well as create a log indicating each action.


Example of what the DB looks like:
```
+----+----------------+--------------+---------+---------+
| id | account_number | account_type | balance | user_id |
+----+----------------+--------------+---------+---------+
|  1 | e62fa2         | Main         | 26350   |       1 |
|  2 | a14813         | Invest       | 0       |       1 |
+----+----------------+--------------+---------+---------+
```

The account receives 100:
```
+----+----------------+--------------+---------+---------+
| id | account_number | account_type | balance | user_id |
+----+----------------+--------------+---------+---------+
|  1 | e62fa2         | Main         | 26350   |       1 |
|  2 | a14813         | Invest       | 100     |       1 |
+----+----------------+--------------+---------+---------+
```

From now on, interest will be charged every 10 seconds:
```
+----+----------------+--------------+---------+---------+
| id | account_number | account_type | balance | user_id |
+----+----------------+--------------+---------+---------+
|  1 | e62fa2         | Main         | 26350   |       1 |
|  2 | a14813         | Invest       | 110     |       1 |
+----+----------------+--------------+---------+---------+
```

After 10 seconds:

```
+----+----------------+--------------+---------+---------+
| id | account_number | account_type | balance | user_id |
+----+----------------+--------------+---------+---------+
|  1 | e62fa2         | Main         | 26350   |       1 |
|  2 | a14813         | Invest       | 121     |       1 |
+----+----------------+--------------+---------+---------+
```

After 10 seconds:
```
+----+----------------+--------------+---------+---------+
| id | account_number | account_type | balance | user_id |
+----+----------------+--------------+---------+---------+
|  1 | e62fa2         | Main         | 26350   |       1 |
|  2 | a14813         | Invest       | 133.1   |       1 |
+----+----------------+--------------+---------+---------+
```

#### Task 5: Security & Error handling

This task checks the security of the API by verifying access control for public and private endpoints and ensures that the application handles errors gracefully and provides appropriate feedback to the user.

- **Public Endpoints**: Ensure that public endpoints like login and registration are accessible without authentication.
- **Private Endpoints Without Authentication**: Verify that private endpoints return a 401 or 403 error if accessed without authentication. "Access denied" message should be displayed.
- **Private Endpoints With Authentication**: Ensure that private endpoints are accessible with a valid JWT token and perform the intended actions.
- **Password Security**: The password must be stored hashed using BCrypt.

- **Duplicate Email or Phone Number**: Ensure that attempting to register a user with an existing email or phone number results in a 400 error and an appropriate message.
- **Invalid Login Credentials**: Test that invalid login attempts (e.g., wrong email or password) return a 401 status with a "Bad credentials" message.
- **Password Validation**: Implement strong password validation rules and return specific error messages for violations.
    Requests bodies and responses:
    ```json
    {
        "name":"Nuwe Test",
        "password":"nuwetest1$",
        "email":"nuwe@nuwe.com"
    }

    Response: Password must contain at least one uppercase letter

    {
        "name":"Nuwe Test",
        "password":"Nuwetest",
        "email":"nuwe@nuwe.com"
    }

    Response: Password must contain at least one digit and one special character

    {
        "name":"Nuwe Test",
        "password":"Nuwetest1",
        "email":"nuweeee@nuwe.com"
    }

    Response: Password must contain at least one special character

    {
        "name":"Nuwe Test",
        "password":"Nuwetest1 ",
        "email":"nuweeee@nuwe.com"
    }

    Response: Password cannot contain whitespace

    {
        "name":"Nuwe Test",
        "password":"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa1$",
        "email":"nuweeee@nuwe.com"
    }

    Response: Password must be less than 128 characters long

    {
        "name":"Nuwe Test",
        "password":"Test1$",
        "email":"nuweeee@nuwe.com"
    }

    Response: Password must be at least 8 characters long
    ```

- **Email Format Validation**: Implement strong email format validation rules.
    Request body:
    ```json
    {
        "name":"Nuwe Test",
        "password":"TestTest1$",
        "email":"nuwenuwe"
    }
    ```
    Response:
    ```
    Invalid email: nuwenuwe
    ```

## 💫 Guides


### Endpoints Table

| Endpoint                                    | Method | Params/Body                                        | Requires Auth | Response Codes                              | Description                                                                                 |
|---------------------------------------------|--------|----------------------------------------------------|---------------|---------------------------------------------|---------------------------------------------------------------------------------------------|
| `/api/users/register`                       | POST   | `{ name, password, email }`                        | No            | 200, 400 ("Email already exists")           | Registers a new user.                                                                       |
| `/api/users/login`                          | POST   | `{ identifier, password }`                         | No            | 200, 401 ("Bad credentials")                | Logs in the user and returns a JWT token.                                                   |
| `/api/dashboard/user`                       | GET    | N/A                                               | Yes           | 200, 401 ("Access Denied")                  | Retrieves the logged-in user's details.                                                     |
| `/api/dashboard/account`                    | GET    | N/A                                               | Yes           | 200, 401 ("Access Denied")                  | Retrieves the main account information, including balance.                                  |
| `/api/dashboard/account/{index}`            | GET    | `{ index }` (Path Parameter)                      | Yes           | 200, 401, 404                               | Retrieves account information for a specific account by its index (e.g., 0 for main account).|
| `/api/account/create`                       | POST   | `{ accountNumber, accountType }`                  | Yes           | 200, 400                                   | Creates a new account for the user using the main account number and specifying account type.|
| `/api/account/deposit`                      | POST   | `{ amount }`                                      | Yes           | 200, 401                               | Deposits a specific amount into the user's account, with applicable fees.                   |
| `/api/account/withdraw`                     | POST   | `{ amount }`                                      | Yes           | 200, 401                               | Withdraws a specific amount from the user's account, with applicable fees.                  |
| `/api/account/fund-transfer`                | POST   | `{ targetAccountNumber, amount }`                 | Yes           | 200, 401                               | Transfers funds to another account, with fraud detection if applicable.                     |
| `/api/account/transactions`                 | GET    | N/A                                               | Yes           | 200, 401                                   | Retrieves the user's transaction history, including any flagged as fraud.                   |
| `/api/users/logout`                         | GET    | N/A                                               | Yes           | 200, 401 ("Access Denied")                  | Logs out the user and invalidates the JWT token.                                            |


---

### More information

The [application.properties](src/main/resources/application.properties) file contains the configuration necessary for the correct functioning of the application. 

**The tests will simulate the interaction of a user directly with the API running in a container and exposed on port 3000**


## 📤 Submission

1. Solve the proposed tasks.
2. Continuously push the changes you have made.
3. Wait for the results.
4. Click submit challenge when you have reached your maximum score.

## 📊 Evaluation

The final score will be given according to whether or not the objectives have been met.

In this case, the challenge will be evaluated on 1750 (1350 for tasks and 400 for code quality) points which are distributed as follows:

- **Task 1**: 100 points
- **Task 2**: 400 points
- **Task 3**: 400 points
- **Task 4**: 300 points
- **Task 5**: 150 points
- **Code quality**: 400 points

## ❓ Additional information

Only the files proposed in the objectives should be modified. You are not allowed to modify anything other than the proposed files.

**Q1: What happens if I modify files that are not in scope?**

A1: The correction will fail because those changes will not be taken into account.

**Q2: Can I add resources that are not in pom.xml?**

A2: No, everything needed to complete the challenge is included.

**Q3: Can I modify docker-compose?**

A3: No, modifying this file may cause the application to malfunction.
















