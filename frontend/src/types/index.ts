// User Types
export interface User {
  id: string;
  email: string;
  username: string;
  fullName: string;
  avatar?: string;
  bio?: string;
  joinedDate: Date;
  streakDays: number;
  bibleLiteracyLevel: 'beginner' | 'intermediate' | 'advanced';
  location?: {
    latitude: number;
    longitude: number;
    city?: string;
    state?: string;
  };
}

// Prayer Types
export interface Prayer {
  id: string;
  userId: string;
  title: string;
  description: string;
  status: 'pending' | 'answered';
  createdAt: Date;
  answeredAt?: Date;
  tags: string[];
  isPrivate: boolean;
  scriptureReferences: ScriptureReference[];
  musicLinks: MusicLink[];
  sharedWithCommunity: boolean;
}

export interface ScriptureReference {
  id: string;
  book: string;
  chapter: number;
  verseStart: number;
  verseEnd?: number;
  text: string;
  translation: 'NIV' | 'ESV' | 'KJV' | 'NKJV' | 'NLT' | 'MSG';
}

export interface MusicLink {
  id: string;
  youtubeId: string;
  title: string;
  artist: string;
  thumbnailUrl: string;
  duration: number;
}

// Theologian & Content Types
export interface TheologianContent {
  id: string;
  theologian: Theologian;
  contentType: 'article' | 'video' | 'audio' | 'quote';
  title: string;
  description: string;
  content: string;
  duration?: number; // in minutes
  difficulty: 'beginner' | 'intermediate' | 'advanced';
  topics: string[];
  scriptureReferences: ScriptureReference[];
  publishedDate: Date;
  thumbnailUrl?: string;
  mediaUrl?: string;
}

export interface Theologian {
  id: string;
  name: string;
  bio: string;
  denomination?: string;
  avatar: string;
  specialties: string[];
  isOrthodox: boolean;
}

// Daily Content
export interface DailyContent {
  id: string;
  date: Date;
  scripture: ScriptureReference;
  devotional: TheologianContent;
  reflectionPrompt: string;
  prayerPrompt: string;
}

// Community Types
export interface CommunityGroup {
  id: string;
  name: string;
  description: string;
  memberCount: number;
  isPrivate: boolean;
  coverImage?: string;
  createdBy: string;
  createdAt: Date;
  tags: string[];
}

export interface CommunityPost {
  id: string;
  userId: string;
  user: User;
  groupId?: string;
  content: string;
  postType: 'prayer-request' | 'testimony' | 'question' | 'encouragement';
  createdAt: Date;
  likes: number;
  comments: Comment[];
  prayerCount: number;
}

export interface Comment {
  id: string;
  userId: string;
  user: User;
  content: string;
  createdAt: Date;
  likes: number;
}

// Event Types
export interface Event {
  id: string;
  organizationId: string;
  organization: Organization;
  title: string;
  description: string;
  eventType: 'evangelistic-outreach' | 'bible-study' | 'prayer-meeting' | 'worship-night';
  startDate: Date;
  endDate: Date;
  location: {
    latitude: number;
    longitude: number;
    address: string;
    city: string;
    state: string;
    zipCode: string;
  };
  isVirtual: boolean;
  virtualLink?: string;
  attendeeCount: number;
  maxAttendees?: number;
  coverImage?: string;
  tags: string[];
}

export interface Organization {
  id: string;
  name: string;
  description: string;
  logo?: string;
  website?: string;
  contactEmail: string;
  denomination?: string;
  isVerified: boolean;
}

// Streak & Progress Types
export interface UserProgress {
  userId: string;
  currentStreak: number;
  longestStreak: number;
  totalDaysEngaged: number;
  lastEngagementDate: Date;
  completedDevotionals: number;
  prayersLogged: number;
  communityInteractions: number;
  eventsAttended: number;
  badges: Badge[];
}

export interface Badge {
  id: string;
  name: string;
  description: string;
  icon: string;
  earnedDate: Date;
  category: 'prayer' | 'community' | 'learning' | 'consistency';
}

// Navigation Types
export type RootStackParamList = {
  Splash: undefined;
  Onboarding: undefined;
  Login: undefined;
  Signup: undefined;
  Main: undefined;
};

export type MainTabParamList = {
  Home: undefined;
  Community: undefined;
  PrayerJournal: undefined;
  Events: undefined;
  Profile: undefined;
};

export type HomeStackParamList = {
  HomeScreen: undefined;
  DailyDevotional: { contentId: string };
  ScriptureDetail: { scriptureId: string };
  TheologianProfile: { theologianId: string };
};

export type CommunityStackParamList = {
  CommunityFeed: undefined;
  GroupDetail: { groupId: string };
  CreatePost: { groupId?: string };
  UserProfile: { userId: string };
};

export type PrayerStackParamList = {
  PrayerList: undefined;
  PrayerDetail: { prayerId: string };
  CreatePrayer: undefined;
  EditPrayer: { prayerId: string };
  ConnectScripture: { prayerId: string };
  ConnectMusic: { prayerId: string };
};

export type EventsStackParamList = {
  EventsList: undefined;
  EventDetail: { eventId: string };
  EventMap: undefined;
  OrganizationProfile: { organizationId: string };
};

export type ProfileStackParamList = {
  ProfileScreen: undefined;
  EditProfile: undefined;
  Settings: undefined;
  StreakHistory: undefined;
  Badges: undefined;
};
