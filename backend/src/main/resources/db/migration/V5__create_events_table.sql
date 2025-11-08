-- Create events table
CREATE TABLE events (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    event_type TEXT NOT NULL CHECK (event_type IN ('evangelistic_outreach', 'bible_study', 'prayer_meeting', 'worship_night')),
    organization_name TEXT NOT NULL,
    location_name TEXT NOT NULL,
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    event_date TIMESTAMP WITH TIME ZONE NOT NULL,
    contact_info TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create index on event_date for faster date-based queries
CREATE INDEX idx_events_event_date ON events(event_date);

-- Create index on event_type for filtering
CREATE INDEX idx_events_event_type ON events(event_type);

-- Create spatial index on location for geospatial queries
CREATE INDEX idx_events_location ON events USING GIST (
    ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)
) WHERE latitude IS NOT NULL AND longitude IS NOT NULL;
