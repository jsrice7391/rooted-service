# Setup Instructions

## Initial Setup Steps

### 1. Generate Gradle Wrapper (if not present)

If you have Gradle installed:
```bash
cd backend
gradle wrapper --gradle-version 8.5
```

Or let your IDE (IntelliJ IDEA) generate it for you when you open the project.

### 2. Install PostgreSQL with PostGIS

#### macOS
```bash
brew install postgresql postgis
brew services start postgresql
```

#### Ubuntu/Debian
```bash
sudo apt-get update
sudo apt-get install postgresql postgresql-contrib postgis
sudo systemctl start postgresql
```

#### Windows
Download and install from: https://www.postgresql.org/download/windows/
Then install PostGIS from: https://postgis.net/windows_downloads/

### 3. Create Database

```bash
# Connect to PostgreSQL
psql -U postgres

# Run these commands:
CREATE DATABASE stayrooted;
\c stayrooted
CREATE EXTENSION postgis;
\q
```

### 4. Configure Environment

```bash
cd backend
cp .env.example .env
# Edit .env with your database credentials
```

### 5. Build and Run

```bash
./gradlew bootRun
```

## IDE Setup (IntelliJ IDEA Recommended)

1. Open the `backend` folder in IntelliJ IDEA
2. IDEA will automatically detect it as a Gradle project
3. Wait for dependencies to download
4. Right-click `StayRootedApplication.kt` and select "Run"

## Troubleshooting

### Database Connection Issues
- Ensure PostgreSQL is running
- Verify database credentials in `.env`
- Check that the database and PostGIS extension exist

### Gradle Issues
- Make sure Java 17+ is installed: `java -version`
- If wrapper is missing, use IDE to import the Gradle project

### Port Already in Use
- Change `SERVER_PORT` in `.env` file
- Or stop the application using port 8080
