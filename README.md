# spring-boot-swagger-ui-keycloak

[![keep_growing logo](readme-images/logo_250x60.png)](https://keepgrowing.in/)

## About this project

This Spring Boot project shows an example configuration of Springdoc and Keycloak Spring Boot adapter that ensures that 
only authenticated users can call secured endpoints available through Swagger UI:

![swagger ui with keycloak auth for endpoints screenshot](readme-images/swagger-ui-with-keycloak-auth-for-endpoints.png)

## Getting started

First, [clone](https://docs.github.com/en/github/creating-cloning-and-archiving-repositories/cloning-a-repository-from-github/cloning-a-repository)
this repository.

Then, build it locally with:

```shell
mvn clean install
```

You can run the app in a command line with the following command:

```shell
mvn spring-boot:run
```

You can run the `keycloak` container with the following commands:
```shell
cd docker
docker-compose up -d
```

### Running tests

You can run tests with:

```shell
mvn test
```

The MVC tests use Spring Boot Security not Keycloak. The `HttpSecurity` configuration stays the same.

### Credentials

#### For the Keycloak admin

* username: `admin`
* password: `admin`

#### For example users of this app

* available usernames: `christina`, `hanna`, `carlo`, `noel`
* password: `test`
* `christina` has the `chief-operating-officer` realm role that is required to call the `POST: /api/products` endpoint

The `keycloak` service starts with the default realm imported from the
[docker/keycloak/realms/realm-export.json](docker/keycloak/realms/realm-export.json) file that specifies all the default users.

### Visit API documentation

Make sure that the app is running.

* Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) 
(click the `Authorize` button and log in as an example user of the `spring-boot-example-app` client to test secured endpoints)
* OpenAPI specification: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Visit Keycloak

Make sure that the `keycloak` container is up.

* Admin panel: [http://localhost:8024/auth](http://localhost:8024/auth) (log in as the Keycloak admin [`admin:admin`])
* As an admin you can see a list of users associated with the `Spring-Boot-Example` realm by clicking the `View all users` button on the 
[http://localhost:8024/auth/admin/master/console/#/realms/Spring-Boot-Example/users](http://localhost:8024/auth/admin/master/console/#/realms/Spring-Boot-Example/users) page.
* What's more, you can log in as any user associated with the `Spring-Boot-Example` realm by clicking the `Sign in` button on the
[http://localhost:8024/auth/realms/Spring-Boot-Example/account](http://localhost:8024/auth/realms/Spring-Boot-Example/account) page.
* The realm roles are available under the [http://localhost:8024/auth/admin/master/console/#/realms/Spring-Boot-Example/roles](http://localhost:8024/auth/admin/master/console/#/realms/Spring-Boot-Example/roles) url.

## Features

* Application secured with Keycloak
* Swagger UI available for everyone but only authenticated users can call secured endpoints
* Only users with `chief-operating-officer` realm role can call the `POST: /api/products` endpoint 
(this role is assigned to `christina` by default)
* OpenAPI 3 specification

## Built With

* [Spring Boot v2.5+](https://spring.io/projects/spring-boot)
* [Maven](https://maven.apache.org/)
* [Keycloak](https://www.keycloak.org/)
* [Keycloak Spring Boot adapter](https://www.keycloak.org/docs/latest/securing_apps/#_spring_boot_adapter)
* [springdoc-openapi](https://springdoc.org/)
* [Docker Compose](https://docs.docker.com/compose/)
* [Dummy4j](https://daniel-frak.github.io/dummy4j/)
