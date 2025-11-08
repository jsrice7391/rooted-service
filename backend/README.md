# StayRooted Backend API

Backend service for the StayRooted mobile application - helping Christians stay rooted in their faith every day.

## Features

### Prayer Module
- Create private, community, or public prayers
- Follow prayers to pray alongside others
- Comment words of encouragement
- Post prayer updates
- Mark prayers as answered (praise reports)

### Community Module
- Organizations (churches/ministries) management
- Event creation and management
- Location-based event discovery
- Event search and filtering

## Tech Stack

- **Language**: Kotlin
- **Framework**: Spring Boot 3.2
- **Database**: PostgreSQL with PostGIS (geospatial)
- **Authentication**: JWT (JSON Web Tokens)
- **Database Migrations**: Flyway
- **Build Tool**: Gradle (Kotlin DSL)

## Prerequisites

- Java 17 or higher
- PostgreSQL 14+ with PostGIS extension
- Gradle 8.x (or use the wrapper)

## Getting Started

### 1. Database Setup

#### Option A: Using Docker (Recommended)

Start PostgreSQL with Docker Compose:

```bash
cd backend
docker-compose up -d
```

This will start PostgreSQL with PostGIS on port 5432 with the following credentials:
- **Host**: localhost
- **Port**: 5432
- **Database**: stayrooted
- **User**: postgres
- **Password**: postgres

**Docker Commands**:
```bash
# Stop the database
docker-compose stop

# Start the database again
docker-compose start

# Stop and remove the container
docker-compose down

# View database logs
docker logs stayrooted-postgres
```

#### Option B: Local PostgreSQL Installation

Install PostgreSQL and create the database:

```bash
# Connect to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE stayrooted;

# Enable PostGIS extension
\c stayrooted
CREATE EXTENSION postgis;
```

### 2. Environment Configuration

Copy the example environment file and configure it:

```bash
cp .env.example .env
```

Edit `.env` with your database credentials and other settings.

### 3. Build and Run

Using Gradle wrapper:

```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun
```

Or if you have Gradle installed:

```bash
gradle build
gradle bootRun
```

The API will start on `http://localhost:8080`

**Connecting to the Database**:

The application will automatically connect to the PostgreSQL database using the configuration in `application.yml` or your `.env` file. If using Docker Compose with the default settings, no additional configuration is needed - the app will connect to `localhost:5432` with the credentials specified in `docker-compose.yml`.

### 4. Verify Installation

Check the health endpoint:

```bash
curl http://localhost:8080/api/health
```

Expected response:
```json
{
  "status": "UP",
  "service": "StayRooted API",
  "timestamp": "2025-01-08T..."
}
```

## Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── kotlin/com/stayrooted/
│   │   │   ├── StayRootedApplication.kt  # Main application
│   │   │   ├── config/                    # Configuration classes
│   │   │   ├── controller/                # REST controllers
│   │   │   ├── domain/                    # Entity models
│   │   │   ├── dto/                       # Data Transfer Objects
│   │   │   ├── exception/                 # Exception handlers
│   │   │   ├── repository/                # Data access layer
│   │   │   ├── security/                  # Security & JWT
│   │   │   └── service/                   # Business logic
│   │   └── resources/
│   │       ├── application.yml            # Main config
│   │       ├── application-dev.yml        # Dev config
│   │       └── db/migration/              # Flyway migrations
│   └── test/                              # Test files
├── build.gradle.kts                       # Build configuration
└── README.md                              # This file
```

## Development

### Running in Development Mode

Set the active profile to `dev`:

```bash
export SPRING_PROFILES_ACTIVE=dev
./gradlew bootRun
```

Or in your `.env`:
```
SPRING_PROFILES_ACTIVE=dev
```

### Running Tests

```bash
./gradlew test
```

### Database Migrations

Flyway migrations are located in `src/main/resources/db/migration/`.

Migration naming convention:
- `V1__initial_schema.sql`
- `V2__add_prayer_tables.sql`
- etc.

Migrations run automatically on application startup.

## API Documentation

The API follows RESTful conventions with the following base structure:

- Base URL: `http://localhost:8080/api`
- Authentication: Bearer token in `Authorization` header

### Endpoints (Coming Soon)

- `/api/auth` - Authentication (register, login)
- `/api/users` - User management
- `/api/prayers` - Prayer CRUD operations
- `/api/prayers/{id}/follow` - Follow prayers
- `/api/prayers/{id}/comments` - Prayer comments
- `/api/prayers/{id}/updates` - Prayer updates
- `/api/organizations` - Organization management
- `/api/events` - Event management
- `/api/events/nearby` - Location-based event search

## Configuration

Key configuration properties in `application.yml`:

| Property | Description | Default |
|----------|-------------|---------|
| `server.port` | Server port | 8080 |
| `spring.datasource.url` | Database URL | jdbc:postgresql://localhost:5432/stayrooted |
| `application.security.jwt.secret-key` | JWT signing key | (set in .env) |
| `application.security.jwt.expiration` | Token expiration (ms) | 86400000 (24h) |

## Security

- Passwords are hashed using BCrypt
- API endpoints are protected with JWT authentication
- CORS is configured for mobile app origins
- Input validation on all endpoints

## Contributing

1. Create a feature branch from `main`
2. Make your changes
3. Write tests for new functionality
4. Submit a pull request

## License

Private - All rights reserved
