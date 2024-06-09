# Stock Market REST Service

### Overview
Rest API that uses both SQL and NoSql databases.

## Quickstart running App with Docker
1. Download docker-compose.yml file.
2. Run docker-compose file: `docker-compose up`.
3. Docker will automatically download all required images and start them up.
4. To shut down, execute `docker-compose down`.

## Working with Application
- After start up, application will listen to `8080` host. API prefix is `/api/v1`
- Access the application endpoints using an API testing tool (e.g. Postman) or web browser to view documentation.
- Application implements basic authentication. Users with `ADMIN` role can do any request; `USER` can do only GET requests.
- To view API documentation browse `/swagger-ui` (without API prefix). Or you can get raw documentation using `/api-docs` path. Anonymous users can also view the documentation.
- By default, there is sample data of companies and reports. To work with report details, it must be first created using the appropriate endpoints.

### Default users:
| Username | Password | Role  |
|----------|----------|-------|
| admin    | admin    | ADMIN |
| user     | user     | USER  |

## Project Structure
- **Project Type:** Maven
- **Java Version:** 11
- **Framework:** Spring Boot 2.7.7

## Additional Notes
- **Packaging:** The application is packaged as a JAR file.
- **Artifact ID:** stock-market-rest-service
- **Group ID:** com.myproject
- **Version:** 0.0.1-SNAPSHOT

## Requirements 
- **Java Development Kit (JDK):** JDK 11 or later should be installed.
- **Maven:** Maven should be installed for building and managing the project dependencies.
- **Database** The application requires PostgreSQL and MongoDB to be installed and running. The minimum supported version is PostgreSQL 15.3 and MongoDB 4.4
- **IDE:** An Integrated Development Environment (IDE) such as IntelliJ IDEA, Eclipse, or NetBeans can be used for development.

## Running the Project
1. Clone the project from the repository.
2. Import the project into your preferred IDE.
3. Database configuration details such as URL, username, password for Postgres and URI for Mongo should be provided in the `application.properties` file.
4. Build the project using Maven: `mvn clean install`.
5. Run the application: `java -jar target/stock-market-rest-service-0.0.1-SNAPSHOT.jar`. Or change packaging to `war` and deploy it manually to Tomcat Server.