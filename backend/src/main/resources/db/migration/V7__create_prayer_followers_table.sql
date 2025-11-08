-- Create prayer followers table (users following/praying alongside prayers)
CREATE TABLE prayer_followers (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    prayer_id UUID NOT NULL REFERENCES prayers(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    UNIQUE(prayer_id, user_id)
);

-- Create index on prayer_id for faster lookups
CREATE INDEX idx_prayer_followers_prayer_id ON prayer_followers(prayer_id);

-- Create index on user_id for faster lookups
CREATE INDEX idx_prayer_followers_user_id ON prayer_followers(user_id);
