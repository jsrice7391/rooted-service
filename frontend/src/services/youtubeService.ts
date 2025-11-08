import axios from 'axios';
import Constants from 'expo-constants';

const YOUTUBE_API_KEY = Constants.expoConfig?.extra?.youtubeApiKey || '';
const YOUTUBE_API_BASE_URL = 'https://www.googleapis.com/youtube/v3';

export interface YouTubeVideo {
  id: string;
  title: string;
  description: string;
  thumbnailUrl: string;
  channelTitle: string;
  duration: string;
}

export const searchWorshipMusic = async (
  query: string,
  maxResults: number = 10
): Promise<YouTubeVideo[]> => {
  try {
    const searchResponse = await axios.get(`${YOUTUBE_API_BASE_URL}/search`, {
      params: {
        part: 'snippet',
        q: `${query} christian worship music`,
        type: 'video',
        maxResults,
        key: YOUTUBE_API_KEY,
        videoCategoryId: '10', // Music category
      },
    });

    const videoIds = searchResponse.data.items
      .map((item: any) => item.id.videoId)
      .join(',');

    const detailsResponse = await axios.get(`${YOUTUBE_API_BASE_URL}/videos`, {
      params: {
        part: 'contentDetails,snippet',
        id: videoIds,
        key: YOUTUBE_API_KEY,
      },
    });

    return detailsResponse.data.items.map((item: any) => ({
      id: item.id,
      title: item.snippet.title,
      description: item.snippet.description,
      thumbnailUrl: item.snippet.thumbnails.medium.url,
      channelTitle: item.snippet.channelTitle,
      duration: item.contentDetails.duration,
    }));
  } catch (error) {
    console.error('Error searching YouTube:', error);
    return [];
  }
};

export const searchMusicByScripture = async (
  book: string,
  chapter: number,
  verse: number
): Promise<YouTubeVideo[]> => {
  const query = `${book} ${chapter}:${verse}`;
  return searchWorshipMusic(query, 5);
};

export const getVideoDetails = async (videoId: string): Promise<YouTubeVideo | null> => {
  try {
    const response = await axios.get(`${YOUTUBE_API_BASE_URL}/videos`, {
      params: {
        part: 'contentDetails,snippet',
        id: videoId,
        key: YOUTUBE_API_KEY,
      },
    });

    if (response.data.items.length === 0) {
      return null;
    }

    const item = response.data.items[0];
    return {
      id: item.id,
      title: item.snippet.title,
      description: item.snippet.description,
      thumbnailUrl: item.snippet.thumbnails.medium.url,
      channelTitle: item.snippet.channelTitle,
      duration: item.contentDetails.duration,
    };
  } catch (error) {
    console.error('Error getting video details:', error);
    return null;
  }
};

export const parseDuration = (duration: string): number => {
  // Parse ISO 8601 duration (e.g., PT4M33S)
  const match = duration.match(/PT(?:(\d+)H)?(?:(\d+)M)?(?:(\d+)S)?/);
  if (!match) return 0;

  const hours = parseInt(match[1] || '0', 10);
  const minutes = parseInt(match[2] || '0', 10);
  const seconds = parseInt(match[3] || '0', 10);

  return hours * 3600 + minutes * 60 + seconds;
};
