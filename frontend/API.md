# Stay Rooted API Documentation

This document outlines the expected API endpoints for the Stay Rooted backend.

## Base URL
```
https://api.stayrooted.app/v1
```

## Authentication

All authenticated endpoints require a Bearer token in the Authorization header:
```
Authorization: Bearer <token>
```

## Endpoints

### Authentication

#### POST /auth/signup
Create a new user account.

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123",
  "username": "johndoe",
  "fullName": "John Doe"
}
```

**Response:** `201 Created`
```json
{
  "user": {
    "id": "uuid",
    "email": "user@example.com",
    "username": "johndoe",
    "fullName": "John Doe"
  },
  "session": {
    "access_token": "jwt-token",
    "refresh_token": "refresh-token"
  }
}
```

#### POST /auth/signin
Sign in existing user.

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response:** `200 OK`
```json
{
  "user": { /* user object */ },
  "session": { /* session tokens */ }
}
```

### Prayers

#### GET /prayers/user/:userId
Get all prayers for a user.

**Query Parameters:**
- `status` (optional): "pending" | "answered"

**Response:** `200 OK`
```json
[
  {
    "id": "uuid",
    "userId": "uuid",
    "title": "Prayer for healing",
    "description": "Praying for my friend's recovery",
    "status": "pending",
    "createdAt": "2025-01-01T00:00:00Z",
    "tags": ["healing", "friendship"],
    "scriptureReferences": [...],
    "musicLinks": [...]
  }
]
```

#### POST /prayers
Create a new prayer.

**Request Body:**
```json
{
  "userId": "uuid",
  "title": "Prayer title",
  "description": "Prayer description",
  "tags": ["tag1", "tag2"],
  "isPrivate": false,
  "sharedWithCommunity": true
}
```

**Response:** `201 Created`

#### PATCH /prayers/:prayerId
Update a prayer.

#### PATCH /prayers/:prayerId/answer
Mark a prayer as answered.

#### DELETE /prayers/:prayerId
Delete a prayer.

### Daily Content

#### GET /content/daily
Get daily devotional content.

**Query Parameters:**
- `date` (optional): ISO date string

**Response:** `200 OK`
```json
{
  "id": "uuid",
  "date": "2025-01-01",
  "scripture": {
    "book": "John",
    "chapter": 3,
    "verseStart": 16,
    "text": "For God so loved the world...",
    "translation": "NIV"
  },
  "devotional": {
    "id": "uuid",
    "title": "God's Love",
    "description": "Understanding John 3:16",
    "content": "Full devotional text...",
    "theologian": {
      "name": "C.S. Lewis",
      "bio": "..."
    }
  },
  "reflectionPrompt": "How have you experienced God's love today?",
  "prayerPrompt": "Thank God for His love in your life"
}
```

#### GET /content/theologians
Get theologian content.

**Query Parameters:**
- `difficulty` (optional): "beginner" | "intermediate" | "advanced"
- `topics` (optional): comma-separated list

### Events

#### GET /events
Get nearby Christian events.

**Query Parameters:**
- `latitude` (optional): User latitude
- `longitude` (optional): User longitude
- `radius` (optional): Search radius in miles (default: 25)
- `type` (optional): Event type filter

**Response:** `200 OK`
```json
[
  {
    "id": "uuid",
    "title": "Community Bible Study",
    "description": "Weekly bible study",
    "eventType": "bible-study",
    "startDate": "2025-01-15T19:00:00Z",
    "endDate": "2025-01-15T21:00:00Z",
    "location": {
      "latitude": 34.0522,
      "longitude": -118.2437,
      "address": "123 Church St",
      "city": "Los Angeles",
      "state": "CA"
    },
    "organization": {
      "name": "First Community Church",
      "isVerified": true
    },
    "attendeeCount": 15,
    "coverImage": "https://..."
  }
]
```

#### GET /events/:eventId
Get event details.

#### POST /events/:eventId/register
Register for an event.

### Community

#### GET /community/feed/:userId
Get community feed for user.

**Query Parameters:**
- `page` (optional): Page number (default: 1)

**Response:** `200 OK`
```json
{
  "posts": [...],
  "pagination": {
    "page": 1,
    "totalPages": 5,
    "totalPosts": 50
  }
}
```

#### POST /community/posts
Create a community post.

**Request Body:**
```json
{
  "userId": "uuid",
  "groupId": "uuid",
  "content": "Post content",
  "postType": "prayer-request"
}
```

#### POST /community/posts/:postId/like
Like a post.

#### POST /community/posts/:postId/pray
Pray for a post.

#### POST /community/posts/:postId/comments
Add a comment to a post.

### User Progress

#### GET /users/:userId/progress
Get user progress and statistics.

**Response:** `200 OK`
```json
{
  "userId": "uuid",
  "currentStreak": 7,
  "longestStreak": 30,
  "totalDaysEngaged": 180,
  "completedDevotionals": 165,
  "prayersLogged": 89,
  "badges": [...]
}
```

#### POST /users/:userId/engagement
Record daily engagement.

## Error Responses

All errors follow this format:

```json
{
  "error": {
    "code": "ERROR_CODE",
    "message": "Human readable error message",
    "details": {}
  }
}
```

### Common Error Codes
- `400` - Bad Request
- `401` - Unauthorized
- `403` - Forbidden
- `404` - Not Found
- `500` - Internal Server Error

## Rate Limiting

- 100 requests per minute per user
- 1000 requests per hour per user

## Pagination

List endpoints support pagination:
- `page`: Page number (default: 1)
- `limit`: Items per page (default: 20, max: 100)

## Filtering & Sorting

Most list endpoints support:
- `sort`: Field to sort by
- `order`: "asc" or "desc"
- `filter`: JSON object for filtering

## Webhooks

Subscribe to events:
- `prayer.answered`
- `event.registered`
- `badge.earned`
- `streak.milestone`
