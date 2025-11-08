# Stay Rooted API Documentation

## Overview

Stay Rooted is a faith-building application designed for Gen-Z and Millennials, targeting emerging Christians with nominal Bible literacy. The application focuses on:

- **Community Building**: Connect with other believers in your area
- **Theological Growth**: Access bite-sized content from orthodox Christian theologians
- **Daily Consistency**: Build spiritual habits through daily check-ins and streaks
- **Transformative Journey**: Mature in belief through guided content and reflection

## Base URL

```
http://localhost:3000/api
```

## Authentication

Most endpoints require JWT authentication. Include the token in the Authorization header:

```
Authorization: Bearer <your_jwt_token>
```

## Endpoints

### Authentication

#### Register User
- **POST** `/auth/register`
- **Access**: Public
- **Body**:
```json
{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "location": {
    "coordinates": [-122.4194, 37.7749],
    "city": "San Francisco",
    "state": "CA",
    "country": "USA"
  }
}
```

#### Login
- **POST** `/auth/login`
- **Access**: Public
- **Body**:
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

#### Get Current User
- **GET** `/auth/me`
- **Access**: Private

#### Update User Details
- **PUT** `/auth/updatedetails`
- **Access**: Private
- **Body**:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "bio": "Growing in faith daily",
  "faithJourney": {
    "beliefLevel": "growing",
    "denominationPreference": "Non-denominational",
    "interests": ["prayer", "scripture-study", "worship"]
  }
}
```

### Users & Community

#### Get Nearby Users
- **GET** `/users/nearby?latitude=37.7749&longitude=-122.4194&maxDistance=50000`
- **Access**: Private
- **Query Parameters**:
  - `latitude`: User's latitude
  - `longitude`: User's longitude
  - `maxDistance`: Maximum distance in meters (default: 50000)

#### Get User Profile
- **GET** `/users/:id`
- **Access**: Private

#### Add Connection
- **POST** `/users/connect/:id`
- **Access**: Private

#### Remove Connection
- **DELETE** `/users/connect/:id`
- **Access**: Private

#### Get Connections
- **GET** `/users/connections`
- **Access**: Private

### Daily Content

#### Get Today's Content
- **GET** `/content/today`
- **Access**: Private
- **Returns**: Today's devotional, teaching, or reflection content

#### Get All Content
- **GET** `/content?page=1&limit=10&contentType=devotional&difficulty=beginner`
- **Access**: Private
- **Query Parameters**:
  - `page`: Page number (default: 1)
  - `limit`: Items per page (default: 10)
  - `contentType`: Filter by type (devotional, scripture, teaching, reflection, prayer)
  - `difficulty`: Filter by difficulty (beginner, intermediate, advanced)
  - `tags`: Comma-separated tags

#### Get Content by ID
- **GET** `/content/:id`
- **Access**: Private

#### Complete Content
- **POST** `/content/:id/complete`
- **Access**: Private
- **Body**:
```json
{
  "timeSpent": 300,
  "notes": "This really helped me understand grace better",
  "reflection": "I need to focus more on showing grace to others",
  "rating": 5
}
```

#### Get Progress History
- **GET** `/content/progress/history?page=1&limit=20`
- **Access**: Private

### Community Posts

#### Create Post
- **POST** `/community/posts`
- **Access**: Private
- **Body**:
```json
{
  "content": "Please pray for my family during this difficult time",
  "type": "prayer-request",
  "tags": ["prayer", "family"],
  "isPublic": true
}
```
- **Post Types**: `prayer-request`, `testimony`, `reflection`, `question`, `encouragement`

#### Get Posts
- **GET** `/community/posts?page=1&limit=20&type=prayer-request`
- **Access**: Private
- **Query Parameters**:
  - `page`: Page number
  - `limit`: Items per page
  - `type`: Filter by post type
  - `tags`: Comma-separated tags

#### Get Post by ID
- **GET** `/community/posts/:id`
- **Access**: Private

#### Like/Unlike Post
- **POST** `/community/posts/:id/like`
- **Access**: Private

#### Add Comment
- **POST** `/community/posts/:id/comments`
- **Access**: Private
- **Body**:
```json
{
  "content": "Praying for you and your family!"
}
```

#### Delete Post
- **DELETE** `/community/posts/:id`
- **Access**: Private (Author only)

### Health Check

#### API Health
- **GET** `/health`
- **Access**: Public

## Data Models

### User
- `username`: Unique username
- `email`: User's email
- `firstName`, `lastName`: User's name
- `bio`: Personal bio (max 500 chars)
- `location`: Geo-location with coordinates, city, state, country
- `faithJourney`: Belief level, denomination preference, interests
- `connections`: Array of connected user IDs
- `dailyStreak`: Current daily check-in streak
- `lastCheckIn`: Last check-in date
- `totalCheckIns`: Total number of check-ins

### DailyContent
- `title`: Content title
- `content`: Main content text
- `contentType`: Type (devotional, scripture, teaching, reflection, prayer)
- `theologian`: Information about the theologian/author
- `scripture`: Related scripture reference and text
- `reflection`: Reflection questions and prompts
- `tags`: Content tags
- `difficulty`: Difficulty level
- `estimatedReadTime`: Time in minutes

### CommunityPost
- `author`: User who created the post
- `content`: Post content
- `type`: Post type
- `likes`: Array of user IDs who liked
- `comments`: Array of comments with user and content
- `tags`: Post tags

## Environment Variables

Required environment variables (see `.env.example`):

```
PORT=3000
NODE_ENV=development
MONGODB_URI=mongodb://localhost:27017/rooted-service
JWT_SECRET=your-super-secret-jwt-key
JWT_EXPIRE=7d
CORS_ORIGIN=http://localhost:3000
```

## Getting Started

1. Clone the repository
2. Install dependencies: `npm install`
3. Copy `.env.example` to `.env` and configure
4. Start MongoDB
5. Run the server: `npm run dev`

## Development

- `npm run dev`: Start development server with nodemon
- `npm start`: Start production server
- `npm test`: Run tests
- `npm run lint`: Run ESLint

## Features Roadmap

- [x] User authentication and authorization
- [x] Geo-location based user discovery
- [x] Daily content system
- [x] Progress tracking and streaks
- [x] Community posts and interactions
- [x] User connections
- [ ] Push notifications for daily content
- [ ] Group discussions
- [ ] Prayer request tracking
- [ ] Church/community group integration
- [ ] Content recommendations based on faith journey
