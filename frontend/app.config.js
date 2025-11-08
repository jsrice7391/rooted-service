module.exports = {
  expo: {
    name: "Stay Rooted",
    slug: "stay-rooted",
    version: "1.0.0",
    orientation: "portrait",
    icon: "./assets/icon.png",
    userInterfaceStyle: "light",
    splash: {
      image: "./assets/splash.png",
      resizeMode: "contain",
      backgroundColor: "#4A5D23"
    },
    assetBundlePatterns: ["**/*"],
    extra: {
      supabaseUrl: process.env.SUPABASE_URL,
      supabaseAnonKey: process.env.SUPABASE_ANON_KEY,
      youtubeApiKey: process.env.YOUTUBE_API_KEY,
      apiBaseUrl: process.env.API_BASE_URL
    },
    ios: {
      supportsTablet: true,
      bundleIdentifier: "com.faithtech.stayrooted",
      infoPlist: {
        NSLocationWhenInUseUsageDescription: "Stay Rooted uses your location to show nearby Christian events and connect you with local community."
      }
    },
    android: {
      adaptiveIcon: {
        foregroundImage: "./assets/adaptive-icon.png",
        backgroundColor: "#4A5D23"
      },
      package: "com.faithtech.stayrooted",
      permissions: [
        "ACCESS_FINE_LOCATION",
        "ACCESS_COARSE_LOCATION"
      ]
    },
    web: {
      favicon: "./assets/favicon.png"
    },
    plugins: [
      "expo-location",
      "expo-font"
    ]
  }
};
