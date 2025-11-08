import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  Image,
  ViewStyle,
} from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { theme } from '../theme';
import { Event } from '../types';
import { format } from 'date-fns';

interface EventCardProps {
  event: Event;
  onPress: () => void;
  distance?: number; // in miles
  style?: ViewStyle;
}

export const EventCard: React.FC<EventCardProps> = ({
  event,
  onPress,
  distance,
  style,
}) => {
  const getEventTypeColor = () => {
    switch (event.eventType) {
      case 'evangelistic-outreach':
        return theme.colors.event.outreach;
      case 'bible-study':
        return theme.colors.event.bibleStudy;
      case 'prayer-meeting':
        return theme.colors.event.prayer;
      case 'worship-night':
        return theme.colors.event.worship;
      default:
        return theme.colors.primary;
    }
  };

  const getEventTypeLabel = () => {
    switch (event.eventType) {
      case 'evangelistic-outreach':
        return 'Outreach';
      case 'bible-study':
        return 'Bible Study';
      case 'prayer-meeting':
        return 'Prayer Meeting';
      case 'worship-night':
        return 'Worship Night';
      default:
        return 'Event';
    }
  };

  return (
    <TouchableOpacity
      style={[styles.container, style]}
      onPress={onPress}
      activeOpacity={0.7}
    >
      {event.coverImage && (
        <Image
          source={{ uri: event.coverImage }}
          style={styles.coverImage}
          resizeMode="cover"
        />
      )}
      
      <View style={styles.content}>
        <View style={[styles.typeBadge, { backgroundColor: getEventTypeColor() }]}>
          <Text style={styles.typeText}>{getEventTypeLabel()}</Text>
        </View>
        
        <Text style={styles.title} numberOfLines={2}>
          {event.title}
        </Text>
        
        <Text style={styles.organization} numberOfLines={1}>
          {event.organization.name}
        </Text>
        
        <View style={styles.details}>
          <View style={styles.detailRow}>
            <Ionicons name="calendar-outline" size={16} color={theme.colors.text.secondary} />
            <Text style={styles.detailText}>
              {format(event.startDate, 'MMM d, yyyy • h:mm a')}
            </Text>
          </View>
          
          <View style={styles.detailRow}>
            <Ionicons name="location-outline" size={16} color={theme.colors.text.secondary} />
            <Text style={styles.detailText} numberOfLines={1}>
              {event.isVirtual ? 'Virtual Event' : `${event.location.city}, ${event.location.state}`}
            </Text>
          </View>
          
          {distance !== undefined && (
            <View style={styles.detailRow}>
              <Ionicons name="navigate-outline" size={16} color={theme.colors.primary} />
              <Text style={[styles.detailText, { color: theme.colors.primary }]}>
                {distance.toFixed(1)} miles away
              </Text>
            </View>
          )}
        </View>
        
        <View style={styles.footer}>
          <View style={styles.attendees}>
            <Ionicons name="people-outline" size={16} color={theme.colors.text.secondary} />
            <Text style={styles.attendeeText}>
              {event.attendeeCount} {event.attendeeCount === 1 ? 'attendee' : 'attendees'}
            </Text>
          </View>
          
          {event.organization.isVerified && (
            <View style={styles.verified}>
              <Ionicons name="checkmark-circle" size={16} color={theme.colors.status.success} />
              <Text style={styles.verifiedText}>Verified</Text>
            </View>
          )}
        </View>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: theme.colors.surface,
    borderRadius: theme.borderRadius.lg,
    overflow: 'hidden',
    marginBottom: theme.spacing.md,
    ...theme.shadows.md,
  },
  coverImage: {
    width: '100%',
    height: 160,
  },
  content: {
    padding: theme.spacing.md,
  },
  typeBadge: {
    alignSelf: 'flex-start',
    paddingHorizontal: theme.spacing.sm,
    paddingVertical: 4,
    borderRadius: theme.borderRadius.sm,
    marginBottom: theme.spacing.sm,
  },
  typeText: {
    ...theme.typography.caption,
    color: '#fff',
    fontWeight: '700',
    textTransform: 'uppercase',
    letterSpacing: 0.5,
  },
  title: {
    ...theme.typography.h4,
    color: theme.colors.text.primary,
    marginBottom: 4,
  },
  organization: {
    ...theme.typography.bodySmall,
    color: theme.colors.text.secondary,
    marginBottom: theme.spacing.md,
  },
  details: {
    gap: theme.spacing.sm,
    marginBottom: theme.spacing.md,
  },
  detailRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: theme.spacing.sm,
  },
  detailText: {
    ...theme.typography.bodySmall,
    color: theme.colors.text.secondary,
    flex: 1,
  },
  footer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingTop: theme.spacing.sm,
    borderTopWidth: 1,
    borderTopColor: '#E5E5E5',
  },
  attendees: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 4,
  },
  attendeeText: {
    ...theme.typography.caption,
    color: theme.colors.text.secondary,
  },
  verified: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 4,
  },
  verifiedText: {
    ...theme.typography.caption,
    color: theme.colors.status.success,
    fontWeight: '600',
  },
});
