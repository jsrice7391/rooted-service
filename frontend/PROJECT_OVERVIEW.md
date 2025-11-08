# Stay Rooted - Project Overview

## 🎯 Mission Statement
Empowering emerging Christians (Gen Z & Millennials) to build spiritual muscle through daily consistency, orthodox teaching, and authentic community.

## 📱 App Concept
A mobile-first platform that combines:
- **Daily devotionals** from trusted theologians
- **Prayer journaling** with scripture connections
- **Community building** with other believers  
- **Local event discovery** for Christian activities
- **Progress tracking** to maintain spiritual consistency

## 🎨 Design Philosophy

### Visual Identity
- **Primary Color**: Olive Green (#4A5D23) - Represents growth and being rooted
- **Secondary Color**: Warm Orange (#C67B3E) - Represents community warmth
- **Accent Color**: Purple (#8B4789) - Represents spiritual depth

### User Experience
- **Clean & Modern**: Gen Z/Millennial aesthetic
- **Simple Navigation**: Intuitive bottom tab bar
- **Bite-sized Content**: Easy to consume in 5-10 minutes
- **Encouraging Feedback**: Celebrate streaks and progress
- **Safe Community**: Moderated, supportive environment

## 📐 Architecture

```
┌─────────────────────────────────────────┐
│           Mobile App (React Native)      │
│  ┌─────────────────────────────────┐   │
│  │  Screens (Login, Home, Prayers) │   │
│  └──────────────┬──────────────────┘   │
│  ┌──────────────▼──────────────────┐   │
│  │  Components (Cards, Buttons)    │   │
│  └──────────────┬──────────────────┘   │
│  ┌──────────────▼──────────────────┐   │
│  │  Services (Auth, API, Location) │   │
│  └──────────────┬──────────────────┘   │
└─────────────────┼────────────────────────┘
                  │
         ┌────────┼────────┐
         │        │        │
    ┌────▼───┐ ┌─▼──┐ ┌──▼─────┐
    │Supabase│ │YT  │ │Location│
    │(Auth & │ │API │ │Service │
    │  DB)   │ └────┘ └────────┘
    └────────┘
```

## 🗂 File Structure

```
rooted-service/
├── 📱 App.tsx                    # Root component
├── 📄 package.json               # Dependencies
├── ⚙️  app.json                  # Expo config
├── 🔧 tsconfig.json              # TypeScript config
├── 📚 README.md                  # Main documentation
├── 🚀 SETUP.md                   # Setup guide
├── 📋 NEXT_STEPS.md              # Implementation roadmap
├── 🌐 API.md                     # API documentation
├── 🤝 CONTRIBUTING.md            # Contribution guide
├── 🔐 .env.example               # Environment template
├── 🚫 .gitignore                 # Git ignore rules
│
├── src/
│   ├── 📱 index.tsx              # Entry point
│   │
│   ├── 📁 types/                 # TypeScript definitions
│   │   └── index.ts              # All type definitions
│   │
│   ├── 🎨 theme/                 # Design system
│   │   └── index.ts              # Colors, fonts, spacing
│   │
│   ├── 🧩 components/            # Reusable components
│   │   ├── Button.tsx            # Custom button
│   │   ├── PrayerCard.tsx        # Prayer display card
│   │   └── EventCard.tsx         # Event display card
│   │
│   ├── 📺 screens/               # Full screens
│   │   ├── LoginScreen.tsx       # Login/auth
│   │   ├── SignupScreen.tsx      # Registration
│   │   ├── HomeScreen.tsx        # Daily content
│   │   ├── PrayerJournalScreen.tsx
│   │   └── EventsScreen.tsx      # Local events
│   │
│   ├── 🧭 navigation/            # Navigation setup
│   │   └── AppNavigator.tsx     # Nav configuration
│   │
│   └── 🔌 services/              # External integrations
│       ├── authService.ts        # Supabase auth
│       ├── apiService.ts         # Backend API
│       ├── locationService.ts    # Geolocation
│       └── youtubeService.ts     # YouTube API
```

## 🔄 User Flows

### First-Time User
```
Download → Splash → Onboarding → Signup → 
Profile Setup → Tutorial → Home Dashboard
```

### Daily Engagement
```
Open App → View Streak → Read Devotional → 
Read Scripture → Log Prayer → Check Community → 
Browse Events
```

### Prayer Journey
```
Create Prayer → Connect Scripture → Add Music → 
Monitor Status → Mark Answered → Share Testimony
```

### Community Interaction
```
Browse Feed → View Post → Pray for Request → 
Comment/Like → Join Group → Create Post
```

## 🎯 Success Metrics

### User Engagement
- Daily Active Users (DAU)
- 7-day streak retention
- 30-day streak achievement
- Average session duration

### Content Consumption
- Devotionals completed
- Prayers logged
- Scriptures read
- Community posts

### Community Health
- Active community members
- Posts per day
- Prayer support interactions
- Event RSVPs

### Spiritual Growth (Self-Reported)
- Bible literacy improvement
- Faith maturity ratings
- Community connection
- Answered prayer testimonies

## 🛡 Safety & Moderation

### Content Moderation
- Flagging system for inappropriate content
- Human moderator review queue
- AI-assisted content filtering
- Clear community guidelines

### User Safety
- Report/block functionality
- Private prayer option
- Age verification
- Terms of service compliance

### Theological Accuracy
- Verified theologian profiles
- Orthodox content curation
- Peer review process
- Historical creed alignment

## 💰 Monetization Strategy (Future)

### Free Tier
- Daily devotionals
- Prayer journal
- Community features
- Local events

### Premium Tier ($4.99/month)
- Advanced content library
- Offline access
- Ad-free experience
- Priority support
- Exclusive events

### Church/Organization
- Custom branding
- Event promotion
- Analytics dashboard
- Multi-location support

## 🗺 Roadmap

### Q1 2025 - MVP Launch
- Core features functional
- iOS & Android apps live
- 1,000 registered users
- 50 daily devotionals
- 10 theologian partners

### Q2 2025 - Community Growth
- Push notifications
- Group prayer circles
- Enhanced events
- 10,000 users
- 100 devotionals

### Q3 2025 - Platform Expansion
- Web app launch
- Premium features
- Bible reading plans
- 50,000 users
- Church partnerships

### Q4 2025 - Innovation
- AR experiences
- Live worship streaming
- Mentor matching
- International expansion
- 100,000 users

## 🤝 Partnerships

### Content Partners
- Theologians and pastors
- Christian publishers
- Seminary professors
- Worship music artists

### Organization Partners
- Local churches
- Christian nonprofits
- Campus ministries
- Mission organizations

### Technology Partners
- Supabase (backend)
- YouTube (music)
- Maps/Location services
- Push notification services

## 📞 Contact & Support

### For Users
- In-app support chat
- Email: support@stayrooted.app
- FAQ/Help center

### For Partners
- Email: partnerships@stayrooted.app
- Partnership application form

### For Developers
- GitHub: github.com/jsrice7391/rooted-service
- Discord community
- Email: dev@stayrooted.app

## 📜 License & Legal

- Code: MIT License
- Content: Rights retained by creators
- Privacy: GDPR/CCPA compliant
- Terms: Standard app ToS

## 🙏 Acknowledgments

Built with love for the body of Christ.

Special thanks to:
- FaithTech community
- Contributing theologians
- Beta testers
- Open source community

---

**Stay Rooted** - Growing together in faith 🌱

*"But blessed is the one who trusts in the Lord, whose confidence is in him. They will be like a tree planted by the water that sends out its roots by the stream." - Jeremiah 17:7-8*
