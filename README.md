# Profile Service

Spring Boot microservice for profile management and authentication.

## Features
- Profile CRUD APIs
- Signup API
- Signin API

## APIs
- `GET /api/v1/profiles`
- `GET /api/v1/profiles/{id}`
- `POST /api/v1/profiles`
- `PUT /api/v1/profiles/{id}`
- `DELETE /api/v1/profiles/{id}`
- `POST /api/v1/auth/signup`
- `POST /api/v1/auth/signin`

## Run
```bash
./gradlew clean build
./gradlew bootRun
```

Default port: `8083`
Swagger: `http://localhost:8083/swagger-ui.html`
