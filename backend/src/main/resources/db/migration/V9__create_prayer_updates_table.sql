-- Create prayer updates table (original poster shares updates)
CREATE TABLE prayer_updates (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    prayer_id UUID NOT NULL REFERENCES prayers(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create index on prayer_id for faster lookups
CREATE INDEX idx_prayer_updates_prayer_id ON prayer_updates(prayer_id);

-- Create index on created_at for chronological ordering
CREATE INDEX idx_prayer_updates_created_at ON prayer_updates(created_at DESC);
