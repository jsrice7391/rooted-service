import axios from 'axios';
import Constants from 'expo-constants';
import { Prayer, Event, DailyContent, CommunityPost, TheologianContent } from '../types';

const API_BASE_URL = Constants.expoConfig?.extra?.apiBaseUrl || 'http://localhost:3000/api';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add auth token to requests
export const setAuthToken = (token: string | null) => {
  if (token) {
    apiClient.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  } else {
    delete apiClient.defaults.headers.common['Authorization'];
  }
};

// Prayer API
export const getPrayers = async (userId: string, status?: 'pending' | 'answered') => {
  const params = status ? { status } : {};
  const response = await apiClient.get(`/prayers/user/${userId}`, { params });
  return response.data;
};

export const getPrayerById = async (prayerId: string) => {
  const response = await apiClient.get(`/prayers/${prayerId}`);
  return response.data;
};

export const createPrayer = async (prayer: Partial<Prayer>) => {
  const response = await apiClient.post('/prayers', prayer);
  return response.data;
};

export const updatePrayer = async (prayerId: string, updates: Partial<Prayer>) => {
  const response = await apiClient.patch(`/prayers/${prayerId}`, updates);
  return response.data;
};

export const deletePrayer = async (prayerId: string) => {
  await apiClient.delete(`/prayers/${prayerId}`);
};

export const markPrayerAnswered = async (prayerId: string) => {
  const response = await apiClient.patch(`/prayers/${prayerId}/answer`);
  return response.data;
};

// Events API
export const getEvents = async (
  latitude?: number,
  longitude?: number,
  radius?: number,
  eventType?: string
) => {
  const params: any = {};
  if (latitude && longitude) {
    params.latitude = latitude;
    params.longitude = longitude;
  }
  if (radius) params.radius = radius;
  if (eventType) params.type = eventType;
  
  const response = await apiClient.get('/events', { params });
  return response.data;
};

export const getEventById = async (eventId: string) => {
  const response = await apiClient.get(`/events/${eventId}`);
  return response.data;
};

export const registerForEvent = async (eventId: string, userId: string) => {
  const response = await apiClient.post(`/events/${eventId}/register`, { userId });
  return response.data;
};

// Daily Content API
export const getDailyContent = async (date?: Date) => {
  const params = date ? { date: date.toISOString() } : {};
  const response = await apiClient.get('/content/daily', { params });
  return response.data;
};

export const getTheologianContent = async (
  difficulty?: string,
  topics?: string[]
) => {
  const params: any = {};
  if (difficulty) params.difficulty = difficulty;
  if (topics && topics.length > 0) params.topics = topics.join(',');
  
  const response = await apiClient.get('/content/theologians', { params });
  return response.data;
};

// Community API
export const getCommunityFeed = async (userId: string, page: number = 1) => {
  const response = await apiClient.get(`/community/feed/${userId}`, {
    params: { page },
  });
  return response.data;
};

export const createCommunityPost = async (post: Partial<CommunityPost>) => {
  const response = await apiClient.post('/community/posts', post);
  return response.data;
};

export const likePost = async (postId: string, userId: string) => {
  const response = await apiClient.post(`/community/posts/${postId}/like`, { userId });
  return response.data;
};

export const prayForPost = async (postId: string, userId: string) => {
  const response = await apiClient.post(`/community/posts/${postId}/pray`, { userId });
  return response.data;
};

export const commentOnPost = async (postId: string, userId: string, content: string) => {
  const response = await apiClient.post(`/community/posts/${postId}/comments`, {
    userId,
    content,
  });
  return response.data;
};

// User Progress API
export const getUserProgress = async (userId: string) => {
  const response = await apiClient.get(`/users/${userId}/progress`);
  return response.data;
};

export const recordDailyEngagement = async (userId: string) => {
  const response = await apiClient.post(`/users/${userId}/engagement`);
  return response.data;
};
