import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  ViewStyle,
} from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { theme } from '../theme';
import { Prayer } from '../types';
import { format } from 'date-fns';

interface PrayerCardProps {
  prayer: Prayer;
  onPress: () => void;
  showStatus?: boolean;
  style?: ViewStyle;
}

export const PrayerCard: React.FC<PrayerCardProps> = ({
  prayer,
  onPress,
  showStatus = true,
  style,
}) => {
  const statusColor = prayer.status === 'answered' 
    ? theme.colors.prayer.answered 
    : theme.colors.prayer.pending;
    
  const statusIcon = prayer.status === 'answered' 
    ? 'checkmark-circle' 
    : 'time-outline';

  return (
    <TouchableOpacity
      style={[styles.container, style]}
      onPress={onPress}
      activeOpacity={0.7}
    >
      <View style={styles.header}>
        <Text style={styles.title} numberOfLines={2}>
          {prayer.title}
        </Text>
        {showStatus && (
          <View style={[styles.statusBadge, { backgroundColor: statusColor }]}>
            <Ionicons name={statusIcon as any} size={14} color="#fff" />
            <Text style={styles.statusText}>
              {prayer.status === 'answered' ? 'Answered' : 'Pending'}
            </Text>
          </View>
        )}
      </View>
      
      <Text style={styles.description} numberOfLines={3}>
        {prayer.description}
      </Text>
      
      <View style={styles.footer}>
        <View style={styles.metadata}>
          <Ionicons name="calendar-outline" size={14} color={theme.colors.text.secondary} />
          <Text style={styles.date}>
            {format(prayer.createdAt, 'MMM d, yyyy')}
          </Text>
        </View>
        
        {prayer.scriptureReferences.length > 0 && (
          <View style={styles.metadata}>
            <Ionicons name="book-outline" size={14} color={theme.colors.primary} />
            <Text style={styles.scriptureCount}>
              {prayer.scriptureReferences.length} {prayer.scriptureReferences.length === 1 ? 'verse' : 'verses'}
            </Text>
          </View>
        )}
        
        {prayer.musicLinks.length > 0 && (
          <View style={styles.metadata}>
            <Ionicons name="musical-notes-outline" size={14} color={theme.colors.secondary} />
            <Text style={styles.musicCount}>
              {prayer.musicLinks.length} {prayer.musicLinks.length === 1 ? 'song' : 'songs'}
            </Text>
          </View>
        )}
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: theme.colors.surface,
    borderRadius: theme.borderRadius.lg,
    padding: theme.spacing.md,
    marginBottom: theme.spacing.md,
    ...theme.shadows.md,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
    marginBottom: theme.spacing.sm,
  },
  title: {
    ...theme.typography.h4,
    color: theme.colors.text.primary,
    flex: 1,
    marginRight: theme.spacing.sm,
  },
  statusBadge: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: theme.spacing.sm,
    paddingVertical: 4,
    borderRadius: theme.borderRadius.full,
    gap: 4,
  },
  statusText: {
    ...theme.typography.caption,
    color: '#fff',
    fontWeight: '600',
  },
  description: {
    ...theme.typography.body,
    color: theme.colors.text.secondary,
    marginBottom: theme.spacing.md,
  },
  footer: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: theme.spacing.md,
  },
  metadata: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 4,
  },
  date: {
    ...theme.typography.caption,
    color: theme.colors.text.secondary,
  },
  scriptureCount: {
    ...theme.typography.caption,
    color: theme.colors.primary,
    fontWeight: '600',
  },
  musicCount: {
    ...theme.typography.caption,
    color: theme.colors.secondary,
    fontWeight: '600',
  },
});
