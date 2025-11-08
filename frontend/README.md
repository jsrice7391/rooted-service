# Stay Rooted 🌱

A React Native mobile app designed to help emerging Christians (Gen Z and Millennials) build spiritual muscle through daily consistency with God, community connection, and orthodox Christian teachings.

## 🎯 Target Audience
- Gen Z and Millennials
- Emerging Christians with nominal bible literacy
- People seeking to mature in their faith
- Those looking for Christian community and accountability

## ✨ Core Features

### 🏠 Daily Spiritual Growth
- **Daily Devotionals**: Bite-sized theological content from orthodox Christian theologians
- **Scripture of the Day**: Daily Bible verses with multiple translations
- **Consistency Tracking**: Build and maintain prayer/study streaks
- **Reflection Prompts**: Guided questions for deeper spiritual reflection

### 🙏 Prayer Journal
- **Prayer Tracking**: Catalog pending and answered prayers
- **Scripture Connection**: Link prayers to relevant Bible verses
- **Worship Music**: Connect prayers to YouTube worship music
- **Prayer History**: Track your spiritual journey over time

### 👥 Community Connection
- **User Profiles**: Connect with other believers
- **Community Groups**: Join and participate in faith-based groups
- **Shared Prayers**: Support others through shared prayer requests
- **Testimonies**: Share and celebrate answered prayers

### 📍 Local Events
- **Geo-Location Based**: Find nearby Christian events
- **Event Types**:
  - Evangelistic street outreach
  - Bible study groups
  - Prayer meetings
  - Worship nights
- **Event Discovery**: Filter by type and distance
- **RSVP & Attendance**: Track your community involvement

### 📚 Theological Content
- **Orthodox Teachers**: Content from verified Christian theologians
- **Difficulty Levels**: Beginner, Intermediate, Advanced
- **Multiple Formats**: Articles, videos, audio, quotes
- **Topic-Based**: Organized by theological topics

## 🛠 Tech Stack

- **Framework**: React Native (Expo)
- **Language**: TypeScript
- **Navigation**: React Navigation 6
- **State Management**: React Hooks
- **Authentication**: Supabase Auth
- **Backend**: Supabase (PostgreSQL)
- **APIs**:
  - YouTube Data API (worship music)
  - Expo Location (geolocation)
- **UI Components**: Custom components with modern design

## 📁 Project Structure

```
rooted-service/
├── App.tsx                    # Main app component
├── app.json                   # Expo configuration
├── package.json               # Dependencies
├── tsconfig.json             # TypeScript configuration
├── src/
│   ├── index.tsx             # App entry point
│   ├── types/                # TypeScript type definitions
│   │   └── index.ts          # Core types and interfaces
│   ├── theme/                # Design system
│   │   └── index.ts          # Colors, typography, spacing
│   ├── components/           # Reusable UI components
│   │   ├── Button.tsx
│   │   ├── PrayerCard.tsx
│   │   └── EventCard.tsx
│   ├── screens/              # App screens
│   │   ├── LoginScreen.tsx
│   │   ├── SignupScreen.tsx
│   │   ├── HomeScreen.tsx
│   │   ├── PrayerJournalScreen.tsx
│   │   └── EventsScreen.tsx
│   ├── navigation/           # Navigation configuration
│   │   └── AppNavigator.tsx
│   └── services/             # API and service integrations
│       ├── authService.ts    # Authentication
│       ├── apiService.ts     # Backend API calls
│       ├── locationService.ts # Geolocation
│       └── youtubeService.ts # YouTube integration
```

## 🚀 Getting Started

### Prerequisites
- Node.js 18+ and npm/yarn
- Expo CLI (`npm install -g expo-cli`)
- iOS Simulator (Mac) or Android Emulator
- Supabase account
- YouTube Data API key

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/rooted-service.git
cd rooted-service
```

2. **Install dependencies**
```bash
npm install
```

3. **Set up environment variables**
```bash
cp .env.example .env
```

Edit `.env` with your credentials:
```
SUPABASE_URL=your_supabase_url
SUPABASE_ANON_KEY=your_supabase_anon_key
YOUTUBE_API_KEY=your_youtube_api_key
API_BASE_URL=your_api_base_url
```

4. **Start the development server**
```bash
npm start
```

5. **Run on device/simulator**
- Press `i` for iOS simulator
- Press `a` for Android emulator
- Scan QR code with Expo Go app on your phone

## 🗄 Database Setup

### Supabase Tables

You'll need to create the following tables in Supabase:

**users**
- id (uuid, primary key)
- email (text)
- username (text)
- full_name (text)
- avatar (text)
- bio (text)
- created_at (timestamp)

**prayers**
- id (uuid, primary key)
- user_id (uuid, foreign key)
- title (text)
- description (text)
- status (enum: pending, answered)
- created_at (timestamp)
- answered_at (timestamp)
- is_private (boolean)

**events**
- id (uuid, primary key)
- organization_id (uuid)
- title (text)
- description (text)
- event_type (enum)
- start_date (timestamp)
- location (json)
- created_at (timestamp)

**daily_content**
- id (uuid, primary key)
- date (date)
- scripture_id (uuid)
- devotional_id (uuid)
- reflection_prompt (text)
- prayer_prompt (text)

## 🎨 Design System

### Colors
- **Primary**: `#4A5D23` (Olive green - growth & rootedness)
- **Secondary**: `#C67B3E` (Warm orange - community)
- **Accent**: `#8B4789` (Purple - spiritual depth)

### Typography
Modern, readable fonts optimized for Gen Z/Millennial audience with clear hierarchy.

### Components
Consistent, accessible UI components following Material Design principles with a spiritual, warm aesthetic.

## 📱 Features Roadmap

### Phase 1 (Current)
- ✅ Authentication
- ✅ Daily devotionals
- ✅ Prayer journal
- ✅ Local events discovery
- ✅ Basic community features

### Phase 2 (Planned)
- [ ] Push notifications for daily content
- [ ] Group prayer requests
- [ ] Scripture memory tools
- [ ] Offline mode
- [ ] Social sharing

### Phase 3 (Future)
- [ ] Live worship streaming
- [ ] Mentor matching
- [ ] Bible reading plans
- [ ] Audio devotionals
- [ ] AR experience for events

## 🤝 Contributing

We welcome contributions from the community! Please read our contributing guidelines before submitting PRs.

## 📄 License

This project is licensed under the MIT License.

## 🙏 Acknowledgments

- Orthodox Christian theologians who provide content
- The FaithTech community
- Open source contributors

## 📧 Contact

For questions or support, contact: [your-email@example.com]

---

**Stay Rooted** - Growing together in faith 🌱
