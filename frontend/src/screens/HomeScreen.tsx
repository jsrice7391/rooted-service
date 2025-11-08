import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  RefreshControl,
} from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { theme } from '../theme';
import { getDailyContent, recordDailyEngagement } from '../services/apiService';
import { DailyContent } from '../types';

export const HomeScreen = ({ navigation }: any) => {
  const [dailyContent, setDailyContent] = useState<DailyContent | null>(null);
  const [loading, setLoading] = useState(true);
  const [streak, setStreak] = useState(0);

  useEffect(() => {
    loadDailyContent();
  }, []);

  const loadDailyContent = async () => {
    try {
      const content = await getDailyContent();
      setDailyContent(content);
    } catch (error) {
      console.error('Error loading daily content:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleEngagement = async () => {
    try {
      // Record engagement when user interacts
      await recordDailyEngagement('user-id'); // Replace with actual user ID
    } catch (error) {
      console.error('Error recording engagement:', error);
    }
  };

  return (
    <ScrollView
      style={styles.container}
      refreshControl={
        <RefreshControl refreshing={loading} onRefresh={loadDailyContent} />
      }
    >
      <View style={styles.header}>
        <View>
          <Text style={styles.greeting}>Good morning! 🌱</Text>
          <Text style={styles.subGreeting}>Let's grow in faith today</Text>
        </View>
        
        <View style={styles.streakContainer}>
          <Ionicons name="flame" size={24} color={theme.colors.secondary} />
          <View>
            <Text style={styles.streakNumber}>{streak}</Text>
            <Text style={styles.streakLabel}>Day Streak</Text>
          </View>
        </View>
      </View>

      {dailyContent && (
        <>
          <TouchableOpacity
            style={styles.card}
            onPress={() => {
              handleEngagement();
              navigation.navigate('DailyDevotional', { contentId: dailyContent.devotional.id });
            }}
          >
            <View style={styles.cardHeader}>
              <Ionicons name="book-outline" size={24} color={theme.colors.primary} />
              <Text style={styles.cardTitle}>Today's Devotional</Text>
            </View>
            <Text style={styles.cardSubtitle}>{dailyContent.devotional.title}</Text>
            <Text style={styles.cardDescription} numberOfLines={3}>
              {dailyContent.devotional.description}
            </Text>
            <View style={styles.cardFooter}>
              <Text style={styles.theologianName}>
                by {dailyContent.devotional.theologian.name}
              </Text>
              <Ionicons name="chevron-forward" size={20} color={theme.colors.text.secondary} />
            </View>
          </TouchableOpacity>

          <View style={styles.card}>
            <View style={styles.cardHeader}>
              <Ionicons name="bookmarks-outline" size={24} color={theme.colors.accent} />
              <Text style={styles.cardTitle}>Scripture of the Day</Text>
            </View>
            <Text style={styles.scriptureReference}>
              {dailyContent.scripture.book} {dailyContent.scripture.chapter}:
              {dailyContent.scripture.verseStart}
            </Text>
            <Text style={styles.scriptureText}>{dailyContent.scripture.text}</Text>
            <Text style={styles.translation}>
              - {dailyContent.scripture.translation}
            </Text>
          </View>

          <View style={styles.card}>
            <View style={styles.cardHeader}>
              <Ionicons name="chatbubbles-outline" size={24} color={theme.colors.secondary} />
              <Text style={styles.cardTitle}>Reflection</Text>
            </View>
            <Text style={styles.promptText}>{dailyContent.reflectionPrompt}</Text>
          </View>

          <View style={styles.card}>
            <View style={styles.cardHeader}>
              <Ionicons name="heart-outline" size={24} color={theme.colors.prayer.pending} />
              <Text style={styles.cardTitle}>Prayer Focus</Text>
            </View>
            <Text style={styles.promptText}>{dailyContent.prayerPrompt}</Text>
            <TouchableOpacity
              style={styles.actionButton}
              onPress={() => navigation.navigate('PrayerJournal')}
            >
              <Text style={styles.actionButtonText}>Add to Prayer Journal</Text>
              <Ionicons name="add-circle-outline" size={20} color={theme.colors.primary} />
            </TouchableOpacity>
          </View>
        </>
      )}

      <View style={styles.quickActions}>
        <Text style={styles.sectionTitle}>Quick Actions</Text>
        <View style={styles.actionsGrid}>
          <TouchableOpacity
            style={styles.actionCard}
            onPress={() => navigation.navigate('Community')}
          >
            <Ionicons name="people" size={32} color={theme.colors.primary} />
            <Text style={styles.actionCardText}>Community</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={styles.actionCard}
            onPress={() => navigation.navigate('Events')}
          >
            <Ionicons name="calendar" size={32} color={theme.colors.secondary} />
            <Text style={styles.actionCardText}>Events</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={styles.actionCard}
            onPress={() => navigation.navigate('PrayerJournal')}
          >
            <Ionicons name="heart" size={32} color={theme.colors.prayer.answered} />
            <Text style={styles.actionCardText}>Prayers</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={styles.actionCard}
            onPress={() => navigation.navigate('Profile')}
          >
            <Ionicons name="person" size={32} color={theme.colors.accent} />
            <Text style={styles.actionCardText}>Profile</Text>
          </TouchableOpacity>
        </View>
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: theme.colors.background,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: theme.spacing.lg,
    paddingTop: theme.spacing.xl,
  },
  greeting: {
    ...theme.typography.h2,
    color: theme.colors.text.primary,
  },
  subGreeting: {
    ...theme.typography.body,
    color: theme.colors.text.secondary,
    marginTop: 4,
  },
  streakContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: theme.spacing.sm,
    backgroundColor: theme.colors.surface,
    padding: theme.spacing.md,
    borderRadius: theme.borderRadius.lg,
    ...theme.shadows.sm,
  },
  streakNumber: {
    ...theme.typography.h3,
    color: theme.colors.secondary,
    lineHeight: 24,
  },
  streakLabel: {
    ...theme.typography.caption,
    color: theme.colors.text.secondary,
  },
  card: {
    backgroundColor: theme.colors.surface,
    marginHorizontal: theme.spacing.lg,
    marginBottom: theme.spacing.md,
    padding: theme.spacing.lg,
    borderRadius: theme.borderRadius.lg,
    ...theme.shadows.md,
  },
  cardHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: theme.spacing.sm,
    marginBottom: theme.spacing.md,
  },
  cardTitle: {
    ...theme.typography.h4,
    color: theme.colors.text.primary,
  },
  cardSubtitle: {
    ...theme.typography.h4,
    color: theme.colors.primary,
    marginBottom: theme.spacing.sm,
  },
  cardDescription: {
    ...theme.typography.body,
    color: theme.colors.text.secondary,
    marginBottom: theme.spacing.md,
  },
  cardFooter: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  theologianName: {
    ...theme.typography.bodySmall,
    color: theme.colors.text.secondary,
    fontStyle: 'italic',
  },
  scriptureReference: {
    ...theme.typography.h4,
    color: theme.colors.accent,
    marginBottom: theme.spacing.sm,
  },
  scriptureText: {
    ...theme.typography.body,
    color: theme.colors.text.primary,
    fontStyle: 'italic',
    marginBottom: theme.spacing.md,
    lineHeight: 24,
  },
  translation: {
    ...theme.typography.bodySmall,
    color: theme.colors.text.secondary,
    textAlign: 'right',
  },
  promptText: {
    ...theme.typography.body,
    color: theme.colors.text.primary,
    lineHeight: 24,
  },
  actionButton: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    gap: theme.spacing.sm,
    marginTop: theme.spacing.md,
    paddingVertical: theme.spacing.md,
    borderTopWidth: 1,
    borderTopColor: '#E5E5E5',
  },
  actionButtonText: {
    ...theme.typography.button,
    color: theme.colors.primary,
  },
  quickActions: {
    padding: theme.spacing.lg,
  },
  sectionTitle: {
    ...theme.typography.h3,
    color: theme.colors.text.primary,
    marginBottom: theme.spacing.md,
  },
  actionsGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: theme.spacing.md,
  },
  actionCard: {
    flex: 1,
    minWidth: '45%',
    backgroundColor: theme.colors.surface,
    padding: theme.spacing.lg,
    borderRadius: theme.borderRadius.lg,
    alignItems: 'center',
    gap: theme.spacing.sm,
    ...theme.shadows.sm,
  },
  actionCardText: {
    ...theme.typography.bodySmall,
    color: theme.colors.text.primary,
    fontWeight: '600',
  },
});
