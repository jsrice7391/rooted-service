export const theme = {
  colors: {
    primary: '#4A5D23', // Olive green - represents growth and rootedness
    primaryLight: '#6B8B3D',
    primaryDark: '#2F3D15',
    
    secondary: '#C67B3E', // Warm orange - represents community warmth
    secondaryLight: '#E09356',
    secondaryDark: '#A66431',
    
    accent: '#8B4789', // Purple - represents spiritual depth
    accentLight: '#A95EA7',
    accentDark: '#6D3569',
    
    background: '#FAFAF8',
    backgroundDark: '#1A1A1A',
    
    surface: '#FFFFFF',
    surfaceDark: '#2A2A2A',
    
    text: {
      primary: '#1F1F1F',
      secondary: '#666666',
      tertiary: '#999999',
      inverse: '#FFFFFF',
    },
    
    status: {
      success: '#4CAF50',
      warning: '#FF9800',
      error: '#F44336',
      info: '#2196F3',
    },
    
    prayer: {
      pending: '#FF9800',
      answered: '#4CAF50',
    },
    
    event: {
      outreach: '#FF6B6B',
      bibleStudy: '#4ECDC4',
      prayer: '#A95EA7',
      worship: '#FFD93D',
    },
    
    gradient: {
      primary: ['#4A5D23', '#6B8B3D'],
      secondary: ['#C67B3E', '#E09356'],
      accent: ['#8B4789', '#A95EA7'],
    },
  },
  
  spacing: {
    xs: 4,
    sm: 8,
    md: 16,
    lg: 24,
    xl: 32,
    xxl: 48,
  },
  
  borderRadius: {
    sm: 4,
    md: 8,
    lg: 12,
    xl: 16,
    full: 9999,
  },
  
  typography: {
    h1: {
      fontSize: 32,
      fontWeight: '700' as const,
      lineHeight: 40,
      letterSpacing: -0.5,
    },
    h2: {
      fontSize: 28,
      fontWeight: '700' as const,
      lineHeight: 36,
      letterSpacing: -0.3,
    },
    h3: {
      fontSize: 24,
      fontWeight: '600' as const,
      lineHeight: 32,
      letterSpacing: -0.2,
    },
    h4: {
      fontSize: 20,
      fontWeight: '600' as const,
      lineHeight: 28,
      letterSpacing: 0,
    },
    body: {
      fontSize: 16,
      fontWeight: '400' as const,
      lineHeight: 24,
      letterSpacing: 0,
    },
    bodySmall: {
      fontSize: 14,
      fontWeight: '400' as const,
      lineHeight: 20,
      letterSpacing: 0,
    },
    caption: {
      fontSize: 12,
      fontWeight: '400' as const,
      lineHeight: 16,
      letterSpacing: 0.4,
    },
    button: {
      fontSize: 16,
      fontWeight: '600' as const,
      lineHeight: 24,
      letterSpacing: 0.5,
    },
  },
  
  shadows: {
    sm: {
      shadowColor: '#000',
      shadowOffset: { width: 0, height: 1 },
      shadowOpacity: 0.1,
      shadowRadius: 2,
      elevation: 2,
    },
    md: {
      shadowColor: '#000',
      shadowOffset: { width: 0, height: 2 },
      shadowOpacity: 0.15,
      shadowRadius: 4,
      elevation: 4,
    },
    lg: {
      shadowColor: '#000',
      shadowOffset: { width: 0, height: 4 },
      shadowOpacity: 0.2,
      shadowRadius: 8,
      elevation: 8,
    },
  },
};

export type Theme = typeof theme;
