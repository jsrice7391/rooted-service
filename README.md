# Stay Rooted 🌱

A full-stack mobile application helping emerging Christians (Gen Z & Millennials) build spiritual muscle through daily consistency, orthodox teachings, community connection, and local event discovery.

## 📁 Project Structure

```
rooted-service/
├── frontend/          # React Native mobile app (Expo)
├── backend/           # Kotlin/Spring Boot API server
└── README.md         # This file
```

## 🏗 Architecture Overview

```
┌─────────────────────────────────────────┐
│   React Native Mobile App (Frontend)    │
│   - iOS & Android                       │
│   - Expo Framework                      │
│   - TypeScript                          │
└──────────────┬──────────────────────────┘
               │ REST API
               │
┌──────────────▼──────────────────────────┐
│   Kotlin Backend (Spring Boot)          │
│   - RESTful API                         │
│   - PostgreSQL Database                 │
│   - JWT Authentication                  │
└──────────────┬──────────────────────────┘
               │
    ┌──────────┴──────────┐
    │                     │
┌───▼──────┐      ┌──────▼────┐
│ Supabase │      │  YouTube  │
│  (Auth)  │      │    API    │
└──────────┘      └───────────┘
```

---

## 📱 Frontend - React Native App

### Tech Stack
- **Framework**: React Native (Expo SDK 51)
- **Language**: TypeScript
- **Navigation**: React Navigation 6
- **State Management**: React Hooks
- **Authentication**: Supabase Auth
- **UI Components**: Custom component library

### Features
- 🔐 User authentication (signup/login)
- 📖 Daily devotionals from theologians
- 🙏 Prayer journal with scripture connections
- 📍 Local Christian events discovery
- 👥 Community feed and interactions
- 📊 Streak tracking and progress
- 🎵 YouTube worship music integration

### Getting Started - Frontend

#### Prerequisites
- Node.js 18+
- npm or yarn
- Expo CLI
- iOS Simulator (Mac) or Android Emulator

#### Installation

```bash
cd frontend
npm install
```

#### Configuration

Create `.env` file in the frontend directory:
```bash
cp .env.example .env
```

Add your credentials:
```env
SUPABASE_URL=your_supabase_url
SUPABASE_ANON_KEY=your_supabase_anon_key
YOUTUBE_API_KEY=your_youtube_api_key
API_BASE_URL=http://localhost:8080/api
```

#### Run Frontend

```bash
# Start Expo dev server
npm start

# Run on iOS
npm run ios

# Run on Android
npm run android

# Run on web
npm run web
```

### Frontend Documentation
- [Setup Guide](frontend/SETUP.md)
- [API Documentation](frontend/API.md)
- [Contributing](frontend/CONTRIBUTING.md)
- [Project Overview](frontend/PROJECT_OVERVIEW.md)

---

## ⚙️ Backend - Kotlin Spring Boot API

### Tech Stack
- **Language**: Kotlin
- **Framework**: Spring Boot 3.x
- **Database**: PostgreSQL
- **ORM**: JPA/Hibernate
- **Authentication**: JWT
- **Build Tool**: Gradle

### Features
- RESTful API endpoints
- User authentication & authorization
- Prayer management
- Event management
- Daily content delivery
- Community features
- Geolocation services

### Getting Started - Backend

#### Prerequisites
- JDK 17+
- Kotlin 1.9+
- Gradle 8+
- PostgreSQL 14+

#### Installation

```bash
cd backend
./gradlew build
```

#### Configuration

Create `application.properties` in `backend/src/main/resources/`:
```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/rooted
spring.datasource.username=postgres
spring.datasource.password=your_password

# JWT
jwt.secret=your_jwt_secret_key
jwt.expiration=86400000

# Supabase
supabase.url=your_supabase_url
supabase.key=your_supabase_key

# Server
server.port=8080
```

#### Database Setup

```bash
# Create database
createdb rooted

# Run migrations (if using Flyway/Liquibase)
./gradlew flywayMigrate
```

#### Run Backend

```bash
# Development mode
./gradlew bootRun

# Build production JAR
./gradlew build

# Run JAR
java -jar build/libs/rooted-backend.jar
```

### API Endpoints

#### Prayers
- `GET /api/prayers` - Get user's prayers
- `POST /api/prayers` - Create prayer
- `PATCH /api/prayers/{id}` - Update prayer
- `DELETE /api/prayers/{id}` - Delete prayer

#### Events
- `GET /api/events` - Get nearby events
- `GET /api/events/{id}` - Get event details
- `POST /api/events/{id}/register` - RSVP to event

#### Daily Content
- `GET /api/content/daily` - Get daily devotional
- `GET /api/content/theologians` - Get theologian content

See full [API Documentation](frontend/API.md) for detailed endpoint specs.

---

## 🚀 Full Stack Setup

### Quick Start (Development)

1. **Start PostgreSQL**
```bash
# Using Docker
docker run -d \
  --name rooted-postgres \
  -e POSTGRES_DB=rooted \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  postgres:14
```

2. **Start Backend**
```bash
cd backend
./gradlew bootRun
```

3. **Start Frontend**
```bash
cd frontend
npm start
# Press 'i' for iOS or 'a' for Android
```

### Production Deployment

#### Backend Deployment
- Build Docker image
- Deploy to AWS/GCP/Heroku
- Configure environment variables
- Set up PostgreSQL instance
- Configure SSL/HTTPS

#### Frontend Deployment
- Build iOS app: `eas build --platform ios`
- Build Android app: `eas build --platform android`
- Submit to App Store / Google Play

---

## 🗄 Database Schema

### Users Table
```sql
CREATE TABLE users (
  id UUID PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  username VARCHAR(100) UNIQUE NOT NULL,
  full_name VARCHAR(255),
  created_at TIMESTAMP DEFAULT NOW()
);
```

### Prayers Table
```sql
CREATE TABLE prayers (
  id UUID PRIMARY KEY,
  user_id UUID REFERENCES users(id),
  title VARCHAR(255) NOT NULL,
  description TEXT,
  status VARCHAR(20) CHECK (status IN ('pending', 'answered')),
  created_at TIMESTAMP DEFAULT NOW(),
  answered_at TIMESTAMP
);
```

See full schema documentation in `backend/docs/schema.md`

---

## 🧪 Testing

### Frontend Tests
```bash
cd frontend
npm test
```

### Backend Tests
```bash
cd backend
./gradlew test
```

---

## 📚 Additional Documentation

- [Frontend Setup Guide](frontend/SETUP.md)
- [Backend API Docs](backend/API.md)
- [Contributing Guidelines](CONTRIBUTING.md)
- [Code of Conduct](CODE_OF_CONDUCT.md)
- [License](LICENSE.md)

---

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md).

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Write/update tests
5. Submit a pull request

---

## 📄 License

This project is licensed under the MIT License - see [LICENSE](LICENSE) for details.

---

## 👥 Team

- **Project Lead**: FaithTech Community
- **Frontend**: React Native Team
- **Backend**: Kotlin Team

---

## 📞 Support

- **Issues**: [GitHub Issues](https://github.com/jsrice7391/rooted-service/issues)
- **Email**: support@stayrooted.app
- **Discord**: [Join our community](#)

---

## 🙏 Acknowledgments

Built with love for the body of Christ.

*"But blessed is the one who trusts in the Lord, whose confidence is in him. They will be like a tree planted by the water that sends out its roots by the stream." - Jeremiah 17:7-8*

---

**Stay Rooted** - Growing together in faith 🌱
