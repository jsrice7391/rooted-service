# Stay Rooted - Development Setup Guide

This guide will help you set up the Stay Rooted app for local development.

## Prerequisites

### Required Software
- **Node.js**: Version 18 or higher
  - Download from [nodejs.org](https://nodejs.org/)
  - Verify: `node --version`

- **npm or Yarn**: Package manager
  - npm comes with Node.js
  - Yarn: `npm install -g yarn`

- **Expo CLI**: For React Native development
  ```bash
  npm install -g expo-cli
  ```

- **Git**: Version control
  - Download from [git-scm.com](https://git-scm.com/)

### Mobile Development Environment

#### For iOS Development (Mac only)
- **Xcode**: Latest version from Mac App Store
- **iOS Simulator**: Installed with Xcode
- **CocoaPods**: `sudo gem install cocoapods`

#### For Android Development
- **Android Studio**: Download from [developer.android.com](https://developer.android.com/studio)
- **Android SDK**: Installed via Android Studio
- **Android Emulator**: Set up via Android Studio AVD Manager

#### For Physical Device Testing
- **Expo Go App**: Install from App Store or Google Play
- Both device and computer must be on same Wi-Fi network

## Step-by-Step Setup

### 1. Clone the Repository
```bash
git clone https://github.com/jsrice7391/rooted-service.git
cd rooted-service
```

### 2. Install Dependencies
```bash
npm install
```

This will install all required packages including:
- React Native
- Expo SDK
- React Navigation
- Supabase client
- TypeScript dependencies

### 3. Set Up Environment Variables

Create a `.env` file in the project root:
```bash
cp .env.example .env
```

You'll need to obtain these credentials:

#### Supabase Setup
1. Go to [supabase.com](https://supabase.com/)
2. Create a new project
3. Go to Project Settings > API
4. Copy your project URL and anon key
5. Update `.env`:
   ```
   SUPABASE_URL=https://your-project.supabase.co
   SUPABASE_ANON_KEY=your-anon-key
   ```

#### YouTube API Setup
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project
3. Enable YouTube Data API v3
4. Create credentials (API key)
5. Update `.env`:
   ```
   YOUTUBE_API_KEY=your-youtube-api-key
   ```

### 4. Set Up Database

Run these SQL commands in your Supabase SQL editor:

```sql
-- Users table
CREATE TABLE users (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  email TEXT UNIQUE NOT NULL,
  username TEXT UNIQUE NOT NULL,
  full_name TEXT NOT NULL,
  avatar TEXT,
  bio TEXT,
  created_at TIMESTAMP DEFAULT NOW()
);

-- Prayers table
CREATE TABLE prayers (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id UUID REFERENCES users(id) ON DELETE CASCADE,
  title TEXT NOT NULL,
  description TEXT,
  status TEXT CHECK (status IN ('pending', 'answered')) DEFAULT 'pending',
  created_at TIMESTAMP DEFAULT NOW(),
  answered_at TIMESTAMP,
  is_private BOOLEAN DEFAULT false
);

-- Add more tables as needed...
```

### 5. Start Development Server

```bash
npm start
```

This will start the Expo development server and show a QR code.

### 6. Run the App

#### On iOS Simulator (Mac only)
Press `i` in the terminal, or run:
```bash
npm run ios
```

#### On Android Emulator
Press `a` in the terminal, or run:
```bash
npm run android
```

#### On Physical Device
1. Install Expo Go from App Store/Google Play
2. Scan the QR code shown in terminal
3. App will load on your device

## Common Issues & Solutions

### Issue: "Cannot find module 'react'"
**Solution**: Delete node_modules and reinstall
```bash
rm -rf node_modules
npm install
```

### Issue: "Expo Go won't connect"
**Solution**: Ensure both devices are on same network, or try:
```bash
expo start --tunnel
```

### Issue: iOS build fails
**Solution**: Clear build cache
```bash
cd ios
pod install
cd ..
npx expo start --clear
```

### Issue: Android emulator not detected
**Solution**: Verify Android SDK path
```bash
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/emulator
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/platform-tools
```

## Development Workflow

### Hot Reloading
The app automatically reloads when you save changes. Shake device or press `Cmd/Ctrl + M` for dev menu.

### Debugging
- **React Native Debugger**: Install from [GitHub](https://github.com/jhen0409/react-native-debugger)
- **Console Logs**: View in terminal or Expo Dev Tools
- **Inspect Element**: Enable in dev menu

### Testing
```bash
npm test
```

### Linting
```bash
npm run lint
```

## Project Structure Guide

- **`/src/components`**: Reusable UI components
- **`/src/screens`**: Full-page screens
- **`/src/services`**: API and service integrations
- **`/src/types`**: TypeScript type definitions
- **`/src/theme`**: Design system (colors, fonts, spacing)
- **`/src/navigation`**: Navigation configuration

## Next Steps

1. Read through the codebase
2. Check open issues on GitHub
3. Pick a feature to work on
4. Read CONTRIBUTING.md
5. Make your first contribution!

## Need Help?

- Open an issue on GitHub
- Join our Discord community
- Email the maintainers

Happy coding! 🌱
