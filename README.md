# Stay Rooted - Faith Building Application

A backend service for the "Stay Rooted" application, designed to help Gen-Z and Millennials deepen their faith through daily spiritual practices, community connection, and bite-sized theological content.

## 🌱 Mission

Stay Rooted helps emerging Christians with nominal Bible literacy to:
- **Unify in Community**: Connect with other believers in your local area
- **Build Spiritual Muscle**: Access bite-sized content from orthodox Christian theologians
- **Maintain Daily Consistency**: Build habits through daily check-ins and streaks
- **Mature in Belief**: Transform faith through guided content and reflection

## 🎯 Target Audience

- Gen-Z and Millennials
- Emerging Christians
- Those seeking to deepen their faith
- Individuals with nominal Bible literacy looking to grow

## ✨ Key Features

### Authentication & User Management
- Secure JWT-based authentication
- User profile management
- Faith journey tracking (belief levels: exploring, new-believer, growing, mature)
- Location-based features for community building

### Community Connection
- Geo-location based user discovery
- User connections (friend system)
- Community posts (prayer requests, testimonies, reflections, questions, encouragement)
- Interactive features (likes, comments)

### Daily Content System
- Bite-sized theological content
- Multiple content types: devotionals, scripture studies, teachings, reflections, prayers
- Content from orthodox Christian theologians
- Difficulty levels (beginner, intermediate, advanced)
- Scripture references with multiple Bible versions

### Progress Tracking
- Daily check-in system
- Streak tracking for consistency
- Personal progress history
- Reflection and note-taking capabilities
- Time tracking for content engagement

## 🚀 Getting Started

### Prerequisites
- Node.js (v14 or higher)
- MongoDB (v4.4 or higher)
- npm or yarn

### Installation

1. Clone the repository:
```bash
git clone https://github.com/jsrice7391/rooted-service.git
cd rooted-service
```

2. Install dependencies:
```bash
npm install
```

3. Configure environment variables:
```bash
cp .env.example .env
```

Edit `.env` with your configuration:
```env
PORT=3000
NODE_ENV=development
MONGODB_URI=mongodb://localhost:27017/rooted-service
JWT_SECRET=your-super-secret-jwt-key-change-this-in-production
JWT_EXPIRE=7d
CORS_ORIGIN=http://localhost:3000
```

4. Start MongoDB:
```bash
# macOS with Homebrew
brew services start mongodb-community

# Linux
sudo systemctl start mongod

# Windows
net start MongoDB
```

5. Run the application:
```bash
# Development mode with auto-reload
npm run dev

# Production mode
npm start
```

The API will be available at `http://localhost:3000`

## 📚 API Documentation

Comprehensive API documentation is available in [API.md](./API.md).

### Quick Start Example

1. Register a new user:
```bash
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "faithseeker",
    "email": "user@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

2. Login and get token:
```bash
curl -X POST http://localhost:3000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

3. Get today's content:
```bash
curl -X GET http://localhost:3000/api/content/today \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 🏗️ Project Structure

```
rooted-service/
├── src/
│   ├── config/           # Configuration files
│   │   └── database.js   # Database connection
│   ├── controllers/      # Route controllers
│   │   ├── authController.js
│   │   ├── userController.js
│   │   ├── contentController.js
│   │   └── communityController.js
│   ├── middleware/       # Custom middleware
│   │   └── auth.js       # Authentication middleware
│   ├── models/           # Mongoose models
│   │   ├── User.js
│   │   ├── DailyContent.js
│   │   ├── UserProgress.js
│   │   └── CommunityPost.js
│   ├── routes/           # API routes
│   │   ├── auth.js
│   │   ├── users.js
│   │   ├── content.js
│   │   └── community.js
│   ├── utils/            # Utility functions
│   │   └── auth.js       # JWT utilities
│   └── index.js          # Application entry point
├── tests/                # Test files
├── .env.example          # Example environment variables
├── .gitignore
├── package.json
├── API.md                # API documentation
└── README.md
```

## 🧪 Testing

```bash
# Run all tests
npm test

# Run tests in watch mode
npm run test:watch

# Run tests with coverage
npm test -- --coverage
```

## 🔧 Development

```bash
# Run with auto-reload
npm run dev

# Lint code
npm run lint
```

## 📖 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user
- `GET /api/auth/me` - Get current user
- `PUT /api/auth/updatedetails` - Update user details

### Users & Community
- `GET /api/users/nearby` - Find nearby users
- `GET /api/users/:id` - Get user profile
- `POST /api/users/connect/:id` - Add connection
- `DELETE /api/users/connect/:id` - Remove connection
- `GET /api/users/connections` - Get user connections

### Daily Content
- `GET /api/content/today` - Get today's content
- `GET /api/content` - Get all content (with filters)
- `GET /api/content/:id` - Get specific content
- `POST /api/content/:id/complete` - Mark content as completed
- `GET /api/content/progress/history` - Get progress history

### Community Posts
- `POST /api/community/posts` - Create post
- `GET /api/community/posts` - Get all posts
- `GET /api/community/posts/:id` - Get specific post
- `POST /api/community/posts/:id/like` - Like/unlike post
- `POST /api/community/posts/:id/comments` - Add comment
- `DELETE /api/community/posts/:id` - Delete post

See [API.md](./API.md) for detailed documentation.

## 🛠️ Technology Stack

- **Runtime**: Node.js
- **Framework**: Express.js
- **Database**: MongoDB with Mongoose ODM
- **Authentication**: JWT (jsonwebtoken)
- **Password Hashing**: bcryptjs
- **CORS**: cors middleware
- **Environment Variables**: dotenv
- **Validation**: express-validator
- **Testing**: Jest & Supertest
- **Development**: Nodemon
- **Code Quality**: ESLint

## 🗺️ Roadmap

- [x] User authentication and authorization
- [x] Geo-location based user discovery
- [x] Daily content system
- [x] Progress tracking and streaks
- [x] Community posts and interactions
- [x] User connections
- [ ] Push notifications for daily content
- [ ] Group discussions and Bible studies
- [ ] Prayer request tracking and reminders
- [ ] Church/community group integration
- [ ] Content recommendations based on faith journey
- [ ] Mobile app integration
- [ ] Admin panel for content management

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

ISC License

## 📧 Contact

For questions or support, please open an issue on GitHub.
