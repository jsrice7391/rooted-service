import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
  RefreshControl,
} from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { theme } from '../theme';
import { EventCard } from '../components/EventCard';
import { getEvents } from '../services/apiService';
import { getCurrentLocation, calculateDistance } from '../services/locationService';
import { Event } from '../types';

export const EventsScreen = ({ navigation }: any) => {
  const [events, setEvents] = useState<Event[]>([]);
  const [filter, setFilter] = useState<string>('all');
  const [loading, setLoading] = useState(true);
  const [userLocation, setUserLocation] = useState<{ latitude: number; longitude: number } | null>(null);

  useEffect(() => {
    initializeLocation();
  }, []);

  useEffect(() => {
    loadEvents();
  }, [filter, userLocation]);

  const initializeLocation = async () => {
    const location = await getCurrentLocation();
    setUserLocation(location);
  };

  const loadEvents = async () => {
    try {
      const eventType = filter === 'all' ? undefined : filter;
      const data = await getEvents(
        userLocation?.latitude,
        userLocation?.longitude,
        50, // 50 mile radius
        eventType
      );
      setEvents(data);
    } catch (error) {
      console.error('Error loading events:', error);
    } finally {
      setLoading(false);
    }
  };

  const getDistance = (event: Event) => {
    if (!userLocation) return undefined;
    return calculateDistance(
      userLocation.latitude,
      userLocation.longitude,
      event.location.latitude,
      event.location.longitude
    );
  };

  const eventTypes = [
    { label: 'All', value: 'all', icon: 'grid-outline' },
    { label: 'Outreach', value: 'evangelistic-outreach', icon: 'megaphone-outline' },
    { label: 'Bible Study', value: 'bible-study', icon: 'book-outline' },
    { label: 'Prayer', value: 'prayer-meeting', icon: 'heart-outline' },
    { label: 'Worship', value: 'worship-night', icon: 'musical-notes-outline' },
  ];

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Local Events</Text>
        <TouchableOpacity
          style={styles.mapButton}
          onPress={() => navigation.navigate('EventMap')}
        >
          <Ionicons name="map-outline" size={24} color={theme.colors.primary} />
        </TouchableOpacity>
      </View>

      <FlatList
        horizontal
        showsHorizontalScrollIndicator={false}
        data={eventTypes}
        keyExtractor={(item) => item.value}
        renderItem={({ item }) => (
          <TouchableOpacity
            style={[
              styles.filterChip,
              filter === item.value && styles.filterChipActive,
            ]}
            onPress={() => setFilter(item.value)}
          >
            <Ionicons
              name={item.icon as any}
              size={20}
              color={filter === item.value ? '#fff' : theme.colors.text.secondary}
            />
            <Text
              style={[
                styles.filterChipText,
                filter === item.value && styles.filterChipTextActive,
              ]}
            >
              {item.label}
            </Text>
          </TouchableOpacity>
        )}
        contentContainerStyle={styles.filterList}
        style={styles.filters}
      />

      <FlatList
        data={events}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => (
          <EventCard
            event={item}
            distance={getDistance(item)}
            onPress={() => navigation.navigate('EventDetail', { eventId: item.id })}
          />
        )}
        contentContainerStyle={styles.listContent}
        refreshControl={
          <RefreshControl refreshing={loading} onRefresh={loadEvents} />
        }
        ListEmptyComponent={
          <View style={styles.emptyState}>
            <Ionicons name="calendar-outline" size={64} color={theme.colors.text.tertiary} />
            <Text style={styles.emptyTitle}>No events found</Text>
            <Text style={styles.emptyText}>
              Check back later for upcoming events in your area
            </Text>
          </View>
        }
      />
    </View>
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
  title: {
    ...theme.typography.h2,
    color: theme.colors.text.primary,
  },
  mapButton: {
    width: 48,
    height: 48,
    borderRadius: theme.borderRadius.md,
    backgroundColor: theme.colors.surface,
    alignItems: 'center',
    justifyContent: 'center',
    ...theme.shadows.sm,
  },
  filters: {
    marginBottom: theme.spacing.md,
  },
  filterList: {
    paddingHorizontal: theme.spacing.lg,
    gap: theme.spacing.sm,
  },
  filterChip: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 4,
    paddingVertical: theme.spacing.sm,
    paddingHorizontal: theme.spacing.md,
    borderRadius: theme.borderRadius.full,
    backgroundColor: theme.colors.surface,
    borderWidth: 1,
    borderColor: '#E5E5E5',
    marginRight: theme.spacing.sm,
  },
  filterChipActive: {
    backgroundColor: theme.colors.primary,
    borderColor: theme.colors.primary,
  },
  filterChipText: {
    ...theme.typography.bodySmall,
    color: theme.colors.text.secondary,
    fontWeight: '600',
  },
  filterChipTextActive: {
    color: '#fff',
  },
  listContent: {
    padding: theme.spacing.lg,
    paddingTop: 0,
  },
  emptyState: {
    alignItems: 'center',
    justifyContent: 'center',
    paddingVertical: theme.spacing.xxl,
    paddingHorizontal: theme.spacing.xl,
  },
  emptyTitle: {
    ...theme.typography.h3,
    color: theme.colors.text.primary,
    marginTop: theme.spacing.lg,
    marginBottom: theme.spacing.sm,
  },
  emptyText: {
    ...theme.typography.body,
    color: theme.colors.text.secondary,
    textAlign: 'center',
  },
});
