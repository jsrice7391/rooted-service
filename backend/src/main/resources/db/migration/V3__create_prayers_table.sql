-- Create prayers table
CREATE TABLE prayers (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    scripture_reference TEXT,
    scripture_text TEXT,
    youtube_music_url TEXT,
    status TEXT NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'ANSWERED')),
    answered_at TIMESTAMP WITH TIME ZONE,
    visibility TEXT NOT NULL DEFAULT 'PRIVATE' CHECK (visibility IN ('PRIVATE', 'COMMUNITY', 'PUBLIC')),
    is_shared BOOLEAN DEFAULT false,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create index on user_id for faster lookups
CREATE INDEX idx_prayers_user_id ON prayers(user_id);

-- Create index on status for filtering
CREATE INDEX idx_prayers_status ON prayers(status);

-- Create index on visibility for filtering
CREATE INDEX idx_prayers_visibility ON prayers(visibility);

-- Create index on is_shared for filtering shared prayers
CREATE INDEX idx_prayers_is_shared ON prayers(is_shared);

-- Trigger for automatic timestamp updates on prayers
CREATE TRIGGER update_prayers_updated_at
    BEFORE UPDATE ON prayers
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();
