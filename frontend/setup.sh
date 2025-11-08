#!/bin/bash

# Stay Rooted - Quick Start Script
# This script helps you get started with the Stay Rooted app

echo "🌱 Welcome to Stay Rooted Setup!"
echo "================================"
echo ""

# Check Node.js
echo "Checking Node.js installation..."
if command -v node &> /dev/null
then
    NODE_VERSION=$(node -v)
    echo "✅ Node.js $NODE_VERSION is installed"
else
    echo "❌ Node.js is not installed"
    echo "Please install Node.js from https://nodejs.org/"
    exit 1
fi

# Check npm
echo "Checking npm installation..."
if command -v npm &> /dev/null
then
    NPM_VERSION=$(npm -v)
    echo "✅ npm $NPM_VERSION is installed"
else
    echo "❌ npm is not installed"
    exit 1
fi

# Check Expo CLI
echo "Checking Expo CLI..."
if command -v expo &> /dev/null
then
    echo "✅ Expo CLI is installed"
else
    echo "⚠️  Expo CLI not found. Installing globally..."
    npm install -g expo-cli
fi

echo ""
echo "Installing project dependencies..."
npm install

echo ""
echo "Checking for .env file..."
if [ -f .env ]; then
    echo "✅ .env file exists"
else
    echo "⚠️  .env file not found. Creating from template..."
    cp .env.example .env
    echo "📝 Please edit .env with your API keys"
fi

echo ""
echo "================================"
echo "✅ Setup complete!"
echo ""
echo "Next steps:"
echo "1. Edit .env file with your API keys:"
echo "   - Supabase URL and Key"
echo "   - YouTube API Key"
echo ""
echo "2. Start the development server:"
echo "   npm start"
echo ""
echo "3. Press 'i' for iOS or 'a' for Android"
echo ""
echo "For detailed setup instructions, see SETUP.md"
echo "================================"
echo "🌱 Happy coding!"
